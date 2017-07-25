package com.tgp.erp.newsync.sqlbuilder.srcsqlimpl;

import com.tgp.erp.newsync.exception.NoTimeException;
import com.tgp.erp.newsync.vo.Field;
import com.tgp.erp.newsync.vo.Table;
import com.tgp.erp.newsync.sqlbuilder.SqlBuilder;

import java.util.List;

/**
 * Created by reph on 2017/6/20.
 */
public class DefaultSrcBuilder implements SqlBuilder {
    private final String DATAPREFIX = "date.";
    private final String SQLPREFIX = "select.";

    @Override
    public String buildSql(Table table) throws NoTimeException {
        String sqlModel = SQLRULE.getProperty(SQLPREFIX + table.getDbType());
        StringBuffer timeCondition = new StringBuffer();
        String time = SQLRULE.getProperty(DATAPREFIX + table.getDbType());
        List<Field> fieldList = table.getFields();
        String primary = new String();
        StringBuffer fields = new StringBuffer();
        StringBuffer condition = new StringBuffer();
        for (int i = 0, len = fieldList.size(); i < len; i++) {
            Field field = fieldList.get(i);
            if (field.isPrimary()) {
                primary = field.getFieldName();
            }
            if (field.getTimeField() != null) {
                time = time.replaceAll("#fieldtime", field.getFieldName()).replaceAll("#time", DATETIME).replaceAll("#typetime", field.getTimeField());
                timeCondition.append(" and " + time);

            }
            fields.append(field.getFieldName() + ",");
        }
        if (table.getEntryFieldName() != null) {
            condition.append(" and " + table.getEntryFieldName() + " is null ");
        }
        if (timeCondition.length() > 0) {
            condition.append(timeCondition);
        } else {
            throw new NoTimeException("table:" + table.getName() + " has no timefield");
        }
        fields.deleteCharAt(fields.length() - 1);
        String sql = sqlModel.replaceAll("#fields", fields.toString()).replaceAll("#condition", condition.toString()).replaceAll("#primary", primary).replaceAll("#table", table.getName());
        return sql;
    }


}
