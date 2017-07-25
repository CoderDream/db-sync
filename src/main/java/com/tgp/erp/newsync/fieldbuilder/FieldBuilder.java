package com.tgp.erp.newsync.fieldbuilder;

import com.tgp.erp.newsync.vo.SyncTable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by reph on 2017/6/21.
 */
public interface FieldBuilder{
    void buildField(SyncTable table, ResultSet res) throws SQLException;
}
