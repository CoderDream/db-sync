package com.tgp.erp.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class DataBaseMap {
    private final String TABLE_SQL = "select table_id,src_table,dest_table,m2m,is_null from #table where db_id=#dbid";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Properties config = Utils.config;
    String db_id;
    OldDataBase srcDB;
    OldDataBase destDB;
    List<TableMap> tables;

    public DataBaseMap(String db_id, OldDataBase srcDB, OldDataBase destDB) throws SQLException, ClassNotFoundException {
        super();
        this.db_id = db_id;
        this.srcDB = srcDB;
        this.destDB = destDB;
        setTable();
    }

    /**
     * 根据数据库信息设置映射表 在本类构造函数中调用
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void setTable() throws SQLException, ClassNotFoundException {
        List<TableMap> tables = new ArrayList<>();
        Connection conn = ConnectionUtils.getConn();
        Statement sta = conn.createStatement();
        String syctable = config.getProperty("db.tableinfo.name");
        String sql = TABLE_SQL.replaceAll("#table", syctable).replaceAll("#dbid", db_id);
        ResultSet res = sta.executeQuery(sql);
        while (res.next()) {
            String table_id = res.getString("table_id");
            String src_Table = res.getString("src_table");
            String dest_Table = res.getString("dest_table");
            String m2m = res.getString("m2m");
            String is_null = res.getString("is_null");
            Table srcTable = new Table(src_Table);
            Table destTable = new Table(dest_Table);
            TableMap tableMap;
            // 如果catch到错误则不会将映射信息添加到映射集中
            try {
                tableMap = new TableMap(table_id, this, srcTable, destTable, m2m, is_null);
                tables.add(tableMap);
            } catch (HasNoTimeException e) {
                logger.info(e.getMessage());
            }
        }
        this.tables = tables;
    }

    /**
     * 数据库同步方法
     *
     * @throws SQLException
     */
    public void sync() throws SQLException {
        long start = System.currentTimeMillis();
        logger.info("db_id:" + db_id + " start sync from:" + start);
        String sql = null;
        Statement srcSta = null;
        ResultSet countRes = null;
        ResultSet srcRes = null;
        PreparedStatement destSta = null;
        String contsql = null;
        try {
            Connection srcconn = srcDB.conn;
            Connection destconn = destDB.conn;
            for (TableMap table : tables) {
                logger.info("table_id:" + table.table_id + " start sync");
                try {

                    srcSta = srcconn.createStatement();
                    contsql = table.srcTable.countSQL;
                    if (table.is_null != null) {
                        contsql = table.srcTable.countSQL.replaceAll("#nullcondition", "where " + table.is_null + " is null");
                    } else {
                        contsql = table.srcTable.countSQL.replaceAll("#nullcondition", "");
                    }
                    countRes = srcSta.executeQuery(contsql);
                    countRes.next();
                    int count = countRes.getInt(1);
                    srcSta.close();
                    // 插入点
                    //logger.info(count+"");
                    for (int i = 0; i < count; i += 2000) {
                        //logger.info(i+"");
                        if (table.is_null != null) {
                            sql = table.srcTable.sql.replaceAll("#start", String.valueOf(i)).replaceAll("#nullcondition", "where " + table.is_null + " is null");
                        } else {
                            sql = table.srcTable.sql.replaceAll("#start", String.valueOf(i)).replaceAll("#nullcondition", "");
                        }
                        logger.info(sql);
                        srcSta = srcconn.createStatement();
                        srcRes = srcSta.executeQuery(sql);
                        ResultSetMetaData metaData = srcRes.getMetaData();
                        destSta = destconn.prepareStatement(table.destTable.sql);
                        while (srcRes.next()) {
                            if (table.m2mtable != null) {
                                Map<Integer, List<String>> m2mdatas = new HashMap<>();
                                int m2mlen = 0;
                                for (int j = 1, len = metaData.getColumnCount(); j <= len; j++) {
                                    if (table.destTable.srcm2mlist.contains(metaData.getColumnLabel(j))
                                            && srcRes.getObject(j) != null
                                            && srcRes.getObject(j).toString().trim().length() > 0) {
                                        String[] s = srcRes.getObject(j).toString().split(",");
                                        List<String> list = Arrays.asList(s);
                                        m2mdatas.put(j, list);
                                        m2mlen = s.length > m2mlen ? s.length : m2mlen;
                                    }
                                }
                                try {
                                    for (int j = 1, len = metaData.getColumnCount(); j <= len; j++) {
                                        if (metaData.getColumnLabel(j) == "フルID" || metaData.getColumnLabel(j) == "更新者ID") {
                                            destSta.setInt(j, Integer.parseInt(srcRes.getString(j)));

                                        } else {
                                            if (!(table.destTable.srcm2mlist.contains(metaData.getColumnLabel(j))
                                                    && srcRes.getObject(j) != null
                                                    && srcRes.getObject(j).toString().trim().length() > 0)) {
                                                destSta.setObject(j, srcRes.getObject(j));
                                            }
                                        }

                                    }
                                    for (int j = 0; j < m2mlen; j++) {
                                        Set<Integer> keySet = m2mdatas.keySet();
                                        for (Integer integer : keySet) {
                                            String value = null;
                                            List<String> list = m2mdatas.get(integer);
                                            if (list.size() > 0) {
                                                value = list.size() - 1 < j ? " " : list.get(j);
                                            }
                                            destSta.setObject(integer, value);
                                        }
                                        destSta.addBatch();
                                    }
                                } catch (Exception e) {

                                    e.printStackTrace();
                                    System.exit(0);
                                }

                            } else {

                                for (int j = 1, len = metaData.getColumnCount(); j <= len; j++) {
                                    destSta.setObject(j, srcRes.getObject(j));
                                }
                                destSta.addBatch();

                            }
                        }
                        destSta.executeBatch();
                    }
                } catch (SQLException e) {
                    logger.info("src sql：" + sql);
                    logger.info("dest sql:" + table.destTable.sql);
                    logger.info("count sql:" + contsql + "\n" + e);
                }

            }

        } finally {
            if (srcSta != null)
                srcSta.close();
            if (countRes != null)
                countRes.close();
            if (srcRes != null)
                srcRes.close();
            if (destSta != null)
                destSta.close();
            srcDB.conn.close();
            destDB.conn.close();

        }
        logger.info("db_id:" + db_id + " end sync cast:" + (System.currentTimeMillis() - start) / 1000 + 's');
    }

}

class OldDataBase {
    private Properties sqlrule = Utils.sqlrule;
    Connection conn;
    String url;
    String password;
    String user;
    String dbtype;
    String dbname;

    public OldDataBase(String password, String user, String dbtype, String dbname, String url) throws Exception {
        super();
        this.password = password;
        this.user = user;
        this.dbtype = dbtype;
        this.dbname = dbname;
        this.url = url;
        setUrl();
        setConn();
    }

    private void setUrl() {
        String urltemp = sqlrule.getProperty("url." + dbtype);
        url = urltemp.replaceAll("#url", url).replaceAll("#user", user).replaceAll("#pwd", password)
                .replaceAll("#dbname", dbname);
    }

    private void setConn() throws Exception {
        Class.forName(sqlrule.getProperty("class." + dbtype));
        this.conn = DriverManager.getConnection(url);
    }

}