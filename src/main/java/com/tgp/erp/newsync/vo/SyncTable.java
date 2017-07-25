package com.tgp.erp.newsync.vo;


import com.tgp.erp.newsync.exception.NoTimeException;
import com.tgp.erp.newsync.fieldbuilder.FieldBuilder;
import com.tgp.erp.newsync.sqlbuilder.SqlBuilder;
import com.tgp.erp.newsync.syncbuilder.SyncMethod;
import com.tgp.erp.newsync.util.ConnectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by reph on 2017/6/15.
 */
public class SyncTable {
    private final String SQL = "Select * from syc_field where table_id=?";

    private String TableId;
    private Table srcTable;
    private Table destTable;
    private DataBase srcDB;
    private DataBase destDB;
    private SyncMethod sync;
    private SqlBuilder srcBuilder;
    private SqlBuilder destBuilder;
    private FieldBuilder fieldBuilder;
    private static Logger logger = LoggerFactory.getLogger(SyncTable.class);

    /**
     * 生成sql语句
     * @throws SQLException
     * @throws NoTimeException
     * @throws ClassNotFoundException
     */
    public void buildSql() throws SQLException, NoTimeException, ClassNotFoundException {
        buildFields();
        String srcsql = srcBuilder.buildSql(srcTable);
        String destsql = destBuilder.buildSql(destTable);
        srcTable.setSql(srcsql);
        destTable.setSql(destsql);
    }

    /**
     * 生成表的映射列
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void buildFields() throws SQLException, ClassNotFoundException {
        PreparedStatement sta = ConnectionUtils.getConnection().prepareStatement(SQL);
        sta.setString(1, getTableId());
        ResultSet res = sta.executeQuery();
        fieldBuilder.buildField(this, res);
    }

    /**
     * 表级同步方法
     * @throws SQLException
     * @throws NoTimeException
     * @throws ClassNotFoundException
     */
    public void syncTable() throws SQLException, NoTimeException, ClassNotFoundException {
        Connection srcDBConn = srcDB.getConn();
        Connection destDBConn = destDB.getConn();
        Statement srcDBSta = null;
        ResultSet srcRes = null;
        PreparedStatement destSta = null;
        int counts = 0;
        logger.info(srcTable.getName()+" to "+destTable.getName() + " | start synchroniz ");
        try {
            srcDBSta = srcDBConn.createStatement();
            srcRes = srcDBSta.executeQuery(srcTable.getSql());
            destSta = destDBConn.prepareStatement(destTable.getSql());
            sync.dealDate(srcRes,destSta,this);
        }catch (Exception e){
            throw e;
        }
        finally {
            if (srcRes != null) {
                srcRes.close();
            }
            if (srcDBSta != null) {
                srcDBSta.close();
            }
            if (destSta != null) {
                destSta.close();
            }
        }
        logger.info(srcTable.getName()+" to "+destTable.getName()  + " | has been synchronized ");
    }

    public DataBase getSrcDB() {
        return srcDB;
    }

    public DataBase getDestDB() {
        return destDB;
    }

    public Table getSrcTable() {
        return srcTable;
    }

    public String getTableId() {
        return TableId;
    }

    public Table getDestTable() {
        return destTable;
    }

    /**
     * 需要设置 源表
     *
     * @param srcTable
     */
    public void setSrcTable(Table srcTable) {
        this.srcTable = srcTable;
    }

    /**
     * 需要设置 目标表
     *
     * @param destTable
     */
    public void setDestTable(Table destTable) {
        this.destTable = destTable;
    }


    /**
     * 需要设置 源数据库
     *
     * @param srcDB
     */
    public void setSrcDB(DataBase srcDB) {
        this.srcDB = srcDB;
    }

    /**
     * 需要设置 目标数据库
     *
     * @param destDB
     */
    public void setDestDB(DataBase destDB) {
        this.destDB = destDB;
    }

    /**
     * 不需要设置
     *
     * @param sync
     */
    public void setSync(SyncMethod sync) {
        this.sync = sync;
    }

    /**
     * 不需要设置
     *
     * @param srcBuilder
     */
    public void setSrcBuilder(SqlBuilder srcBuilder) {
        this.srcBuilder = srcBuilder;
    }

    /**
     * 不需要设置
     *
     * @param destBuilder
     */
    public void setDestBuilder(SqlBuilder destBuilder) {
        this.destBuilder = destBuilder;
    }

    /**
     * 需要设置 表id
     *
     * @param tableId
     */
    public void setTableId(String tableId) {
        TableId = tableId;
    }

    /**
     * 不需要设置
     *
     * @param fieldBuilder
     */
    public void setFieldBuilder(FieldBuilder fieldBuilder) {
        this.fieldBuilder = fieldBuilder;
    }
}
