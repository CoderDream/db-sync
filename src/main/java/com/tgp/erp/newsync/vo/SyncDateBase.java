package com.tgp.erp.newsync.vo;


import com.tgp.erp.newsync.exception.NoTimeException;
import com.tgp.erp.newsync.factory.TableFactory;
import com.tgp.erp.newsync.util.ConnectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by reph on 2017/6/21.
 */
public class SyncDateBase {
    private String dbId;
    private DataBase srcDB;
    private DataBase destDB;
    private List<SyncTable> tables;
    private static Logger logger = LoggerFactory.getLogger(SyncDateBase.class);
    private final static String TABLESQL = "SELECT * FROM syc_table WHERE db_id=?";
    private static Connection infoConn = null;

    public void sync() throws ClassNotFoundException, SQLException, NoTimeException {
        SyncTable table = null;
        try {
            for (SyncTable table1 : tables) {
                table = table1;
                table.syncTable();

            }
        } catch (SQLException e) {
            if (table != null) {
                logger.error(table.getSrcTable().getName() + "has error \n srcsql:" + table.getSrcTable().getSql() + "\n destsql:" + table.getDestTable().getSql() + "\n" + e);
            } else {
                logger.error(e.toString());
            }
            throw e;
        } catch (NoTimeException e) {
            logger.error(table.getSrcTable().getName() + "has no time field");
        }finally {
            table.getDestDB().getConn().close();
            table.getSrcDB().getConn().close();
        }
    }

    public SyncDateBase(String dbId, DataBase srcDB, DataBase destDB) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoTimeException {
        this.dbId = dbId;
        this.srcDB = srcDB;
        this.destDB = destDB;
        if (infoConn == null) {
            infoConn = ConnectionUtils.getConnection();
        }
        buildTable();
    }

    private void buildTable() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoTimeException {
        PreparedStatement sta = infoConn.prepareStatement(TABLESQL);
        sta.setString(1, dbId);
        ResultSet res = sta.executeQuery();
        List<SyncTable> list = new LinkedList<>();
        Table srcTable;
        Table destTable;
        SyncTable table;
        while (res.next()) {
            String tableType = res.getString("table_type");
            String srcTableName = res.getString("src_table");
            String destTableName = res.getString("dest_table");
            String entryFieldName = res.getString("is_null");
            table = TableFactory.getInstance(tableType);
            table.setTableId(res.getString("table_id"));
            table.setDestDB(destDB);
            table.setSrcDB(srcDB);
            srcTable = new Table(entryFieldName, srcTableName, srcDB.getDbType());
            destTable = new Table(entryFieldName, destTableName, destDB.getDbType());
            table.setSrcTable(srcTable);
            table.setDestTable(destTable);
            table.buildSql();
            list.add(table);
        }
        tables = list;
    }
}
