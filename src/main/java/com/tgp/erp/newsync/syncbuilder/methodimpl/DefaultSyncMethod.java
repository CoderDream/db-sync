package com.tgp.erp.newsync.syncbuilder.methodimpl;

import com.tgp.erp.newsync.syncbuilder.SyncMethod;
import com.tgp.erp.newsync.vo.SyncTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by reph on 2017/6/20.
 */
public class DefaultSyncMethod implements SyncMethod {
    @Override
    public void dealDate(ResultSet res, PreparedStatement sta, SyncTable syncTable) throws SQLException {
        ResultSetMetaData metaData = res.getMetaData();
        int len = metaData.getColumnCount();
        int count = 0;
        while (res.next()) {
            for (int i = 1; i <= len; i++) {
                Object object = res.getObject(i);
                if(object!=null&&object.toString().equals("")){
                    object=null;
                }
                sta.setObject(i, object);
            }
            sta.addBatch();
            count++;
            if(count>2000){
                sta.executeBatch();
                count=0;
            }
        }
        sta.executeBatch();
    }
}
