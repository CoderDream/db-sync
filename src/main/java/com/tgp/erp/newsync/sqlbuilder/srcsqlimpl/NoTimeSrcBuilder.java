package com.tgp.erp.newsync.sqlbuilder.srcsqlimpl;

import com.tgp.erp.newsync.exception.NoTimeException;
import com.tgp.erp.newsync.sqlbuilder.SqlBuilder;
import com.tgp.erp.newsync.vo.Field;
import com.tgp.erp.newsync.vo.Table;

import java.util.List;

/**
 * Created by reph on 2017/6/22.
 */
public class NoTimeSrcBuilder implements SqlBuilder {
    @Override
    public String buildSql(Table table) throws NoTimeException {
        String sqlModel = SQLRULE.getProperty("select." + table.getDbType());
        String timeCondition = null;
        List<Field> fieldList = table.getFields();
        String primary = new String();
        StringBuffer fields = new StringBuffer();
        StringBuffer condition = new StringBuffer("");
        for (int i = 0, len = fieldList.size(); i < len; i++) {
            Field field = fieldList.get(i);
            if (field.isPrimary()) {
                primary = field.getFieldName();
            }
            if (field.getTimeField()!=null) {
                timeCondition = SQLRULE.getProperty("date." + table.getDbType());
                timeCondition = timeCondition.replaceAll("#fieldtime", field.getFieldName()).replaceAll("#time",DATETIME).replaceAll("#typetime",field.getTimeField());

            }
            fields.append(field.getFieldName()+",");
        }
        if (table.getEntryFieldName() != null) {
            condition.append(" and " + table.getEntryFieldName() + " is null ");
        }
        fields.deleteCharAt(fields.length() - 1);
        String sql = sqlModel.replaceAll("#fields", fields.toString()).replaceAll("#condition", condition.toString()).replaceAll("#primary", primary).replaceAll("#table",table.getName());
        return sql;
    }
}
