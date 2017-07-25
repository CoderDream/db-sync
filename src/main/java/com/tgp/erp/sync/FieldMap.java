package com.tgp.erp.sync;

public class FieldMap {
    Field srcField;
    Field destField;
    boolean primary;
    boolean time;
    String m2mfield;

    public FieldMap(Field srcField, Field destField, boolean primary, boolean time, String m2mfield) {
        super();
        this.srcField = srcField;
        this.destField = destField;
        this.primary = primary;
        this.time = time;
        this.m2mfield = m2mfield;
    }

}

class Field {
    String name;

    public Field(String name) {
        super();
        this.name = name;
    }
}