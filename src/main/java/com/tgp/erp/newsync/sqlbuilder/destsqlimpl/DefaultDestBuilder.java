package com.tgp.erp.newsync.sqlbuilder.destsqlimpl;

import com.tgp.erp.newsync.sqlbuilder.SqlBuilder;
import com.tgp.erp.newsync.vo.Field;
import com.tgp.erp.newsync.vo.Table;

import java.util.List;

/**
 * Created by reph on 2017/6/20.
 */
public class DefaultDestBuilder implements SqlBuilder {
    @Override
    public String buildSql(Table table) {
        List<Field> fieldList = table.getFields();
        StringBuffer fields = new StringBuffer();
        StringBuffer values = new StringBuffer();
        for (int i = 0, len = fieldList.size(); i < len; i++) {
            fields.append(fieldList.get(i).getFieldName()+",");
            values.append("?,");
        }
        fields.deleteCharAt(fields.length() - 1);
        values.deleteCharAt(values.length() - 1);
        String updateSql = SQLRULE.getProperty("update.mysql");
        String sql = updateSql.replaceAll("#fields", fields.toString()).replaceAll("#table", table.getName()).replaceAll("#values", values.toString());
        return sql;
    }
}
