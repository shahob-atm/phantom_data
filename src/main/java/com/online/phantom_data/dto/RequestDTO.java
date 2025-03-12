package com.online.phantom_data.dto;

import lombok.Data;
import uz.pdp.app.enums.FieldType;

import java.util.List;

@Data
public class RequestDTO {
    private String format; // JSON, CSV, SQL
    private List<FieldType> fields; // Tanlangan FieldType lar
}
