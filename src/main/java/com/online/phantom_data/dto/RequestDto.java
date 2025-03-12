package com.online.phantom_data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.app.enums.FieldType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private String format; // JSON, CSV, SQL
    private List<FieldType> fields; // Tanlangan FieldType lar
}
