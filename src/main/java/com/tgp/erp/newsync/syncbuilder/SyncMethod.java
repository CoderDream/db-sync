package com.tgp.erp.newsync.syncbuilder;

import com.tgp.erp.newsync.vo.SyncTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by reph on 2017/6/16.
 */
public interface SyncMethod {

    void dealDate(ResultSet res, PreparedStatement sta, SyncTable syncTable) throws SQLException;
}
