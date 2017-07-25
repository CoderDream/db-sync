package com.tgp.erp.newsync.fieldbuilder.impl;

import com.tgp.erp.newsync.fieldbuilder.FieldBuilder;
import com.tgp.erp.newsync.vo.Field;
import com.tgp.erp.newsync.vo.SyncTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by reph on 2017/6/21.
 */
public class DefaultBuilderImpl implements FieldBuilder {
    @Override
    public void buildField(SyncTable table, ResultSet res) throws SQLException {
        List<Field> srcFields = new LinkedList<>();
        List<Field> destFields = new LinkedList<>();
        Field srcField;
        Field destField;
        String srcFieldName;
        String destFieldName;
        boolean isPrimary;
        String isTimeField;
        String M2M;
        String relatedField;
        while (res.next()) {
            srcFieldName = res.getString("src_field_name");
            destFieldName = res.getString("dest_field_name");
            isPrimary = res.getInt("is_primary") == 1;
            isTimeField = res.getString("is_time");
            M2M = res.getString("m2m");
            relatedField = res.getString("related_field");

            srcField = new Field(srcFieldName, isPrimary, isTimeField, M2M,relatedField);
            destField = new Field(destFieldName, isPrimary, isTimeField, M2M,relatedField);
            srcFields.add(srcField);
            destFields.add(destField);
        }
        table.getSrcTable().setFields(srcFields);
        table.getDestTable().setFields(destFields);
    }
}
