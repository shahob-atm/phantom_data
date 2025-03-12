package com.online.phantom_data.dto;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.app.enums.FieldType;

import java.util.List;

@Getter
@Setter
public class RequestDto {
    private String format; // JSON, CSV, SQL
    private List<FieldType> fields; // Tanlangan FieldType lar
}
