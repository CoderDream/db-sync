package com.tgp.erp.newsync.vo;

import java.util.List;

/**
 * Created by reph on 2017/6/15.
 */
public class Table {
    private List<Field> fields;
    private String entryFieldName;
    private String sql;
    private String name;
    private String dbType;

    public Table(String entryFieldName, String name, String dbType) {
        this.entryFieldName = entryFieldName;
        this.name = name;
        this.dbType = dbType;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Field> getFields() {
        return fields;
    }

    public String getEntryFieldName() {
        return entryFieldName;
    }

    public String getSql() {
        return sql;
    }

    public String getName() {
        return name;
    }

    public String getDbType() {
        return dbType;
    }
}
