package com.tgp.erp.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class TableMap {
    private final String FIELD_SQL = "select `field_id`,`src_field_name`,`dest_field_name`,`is_time`,m2m,`is_primary` from #table where table_id=#id_table";

    private final Logger logger = LoggerFactory.getLogger(TableMap.class);
    private Properties config = Utils.config;
    private static String time;
    String table_id;
    DataBaseMap dataBases;
    Table srcTable;
    Table destTable;
    String m2mtable;
    String is_null;
    List<FieldMap> fields;


    public TableMap(String table_id, DataBaseMap dataBases, Table srcTable, Table destTable, String m2mtable, String is_null)
            throws SQLException, HasNoTimeException, ClassNotFoundException {
        super();
        this.table_id = table_id;
        this.dataBases = dataBases;
        this.srcTable = srcTable;
        this.destTable = destTable;
        this.m2mtable = m2mtable;
        this.is_null = is_null;
        setFields();
        srcTable.setSQL(fields, dataBases.srcDB.dbtype, true);
        destTable.setSQL(fields, dataBases.destDB.dbtype, false);
        logger.debug("db_id:" + dataBases.db_id + " table_id:" + table_id + " sercsql:" + srcTable.sql + " destsql:" + destTable.sql);

    }

    /**
     * 从数据库中获取列信息 会在table初始化时运行
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void setFields() throws SQLException, ClassNotFoundException {
        List<FieldMap> fields = new LinkedList<>();
        String field_table = config.getProperty("db.fieldinfo.name");
        String sql = FIELD_SQL.replaceAll("#table", field_table).replaceAll("#id_table", this.table_id);
        Connection conn;
        conn = ConnectionUtils.getConn();
        Statement sta = conn.createStatement();
        ResultSet res = sta.executeQuery(sql);
        Field srcField = null;
        Field destField = null;
        while (res.next()) {
            String src_field = res.getString("src_field_name");
            String dest_field = res.getString("dest_field_name");
            boolean istime = 1 == res.getInt("is_time");
            boolean primary = 1 == res.getInt("is_primary");
            String m2mfield = res.getString("m2m");
            srcField = new Field(src_field);
            destField = new Field(dest_field);
            fields.add(new FieldMap(srcField, destField, primary, istime, m2mfield));
        }
        this.fields = fields;
    }

    static {
        String time = Utils.config.getProperty("sync.time");
        if ("".equals(time)) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -1);
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
        }
        TableMap.time = time;
    }

    public static String getTime() {
        return time;
    }

}

class Table {
    String name;
    String sql;
    String countSQL = "";
    List<String> srcm2mlist = new ArrayList<String>();
    List<String> destm2mlist = new ArrayList<String>();
    private final String COUNT_SQL = "select count(#primary) from (select * from #table #nullcondition) mst where 1=1 #condition";

    public Table(String name) {
        super();
        this.name = name;
    }

    /**
     * 通过本表信息设置本表sql 会在tablemap初始化时运行
     *
     * @param list   表的列信息
     * @param dbType 表类型
     * @param isSrc  是否是数据源表 最后又 this.sql =sql 来设置sql语句
     * @throws HasNoTimeException
     */
    public void setSQL(List<FieldMap> list, String dbType, boolean isSrc) throws HasNoTimeException {
        Properties sqlrule = Utils.sqlrule;
        String sqltemp;
        // 判断是否为数据源.sql语句会不同
        if (isSrc) {
            sqltemp = sqlrule.getProperty("select." + dbType);
        } else {
            sqltemp = sqlrule.getProperty("update." + dbType);
        }
        sqltemp = sqltemp.replaceAll("#table", name);
        countSQL = COUNT_SQL.replaceAll("#table", name);

        StringBuffer field = new StringBuffer();
        StringBuffer values = new StringBuffer();
        StringBuffer time = new StringBuffer("and(");
        String connect = "";

        for (FieldMap fields : list) {
            // 判断是否是主键
            if (fields.primary) {
                sqltemp = sqltemp.replaceAll("#primary", isSrc ? fields.srcField.name : fields.destField.name);
                if (isSrc) {
                    countSQL = countSQL.replaceAll("#primary", isSrc ? fields.srcField.name : fields.destField.name);
                }
            }
            // 判断数据源是否有时间操作
            if (fields.time) {
                time.append(connect);
                time.append(sqlrule.getProperty("date." + dbType).replaceAll("#fieldtime",
                        isSrc ? fields.srcField.name : fields.destField.name));
                connect = " or ";
            }

            if (fields.m2mfield != null) {
                srcm2mlist.add(fields.srcField.name);
                destm2mlist.add(fields.destField.name);

            }
            field.append((isSrc ? fields.srcField.name : fields.destField.name) + ",");
            values.append("?,");
        }
        field.deleteCharAt(field.length() - 1);
        values.deleteCharAt(values.length() - 1);
        // 如果时间字符长度大于4则证明有时间判断
        if (time.length() > 4) {
            time.append(')');
            String timesql = time.toString().replaceAll("#time", TableMap.getTime());
            sqltemp = sqltemp.replaceAll("#condition", timesql);
            countSQL = countSQL.replaceAll("#condition", timesql);
        } else {
            sqltemp = sqltemp.replaceAll("#condition", "");
            countSQL = countSQL.replaceAll("#condition", "");
//			throw new HasNoTimeException("表:" + name + "没有时间条件");
        }


        sql = sqltemp.replaceAll("#fields", field.toString()).replaceAll("#time", TableMap.getTime())
                .replaceAll("#values", values.toString());


    }
}

class HasNoTimeException extends Exception {
    private static final long serialVersionUID = 1L;

    public HasNoTimeException(String msg) {
        super(msg);
    }
}