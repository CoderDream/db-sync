package com.tgp.erp.newsync.vo;

/**
 * Created by reph on 2017/6/15.
 */
public class Field {
    private String fieldName;
    private boolean isPrimary;
    private String isTimeField;
    private String M2MField;
    private String relatedField;

    public Field(String fieldName, boolean isPrimary, String isTimeField, String isM2MField, String relatedField) {
        this.fieldName = fieldName;
        this.isPrimary = isPrimary;
        this.isTimeField = isTimeField;
        this.M2MField = isM2MField;
        this.relatedField = relatedField;

    }

    public String getRelatedField() {
        return relatedField;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public String getTimeField() {
        return isTimeField;
    }

    public String getM2MField() {
        return M2MField;
    }
}
