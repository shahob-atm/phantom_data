package com.online.phantom_data.dto;

import uz.pdp.app.enums.FieldType;

import java.util.List;

public class RequestDto {
    private String format; // JSON, CSV, SQL
    private List<FieldType> fields; // Tanlangan FieldType lar

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<FieldType> getFields() {
        return fields;
    }

    public void setFields(List<FieldType> fields) {
        this.fields = fields;
    }
}
