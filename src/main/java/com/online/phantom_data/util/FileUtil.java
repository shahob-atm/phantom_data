package com.online.phantom_data.util;

import uz.pdp.app.dao.FakeDataGenerator;
import uz.pdp.app.enums.FieldType;
import uz.pdp.app.model.Entry;
import uz.pdp.app.model.Pairs;
import uz.pdp.app.model.Request;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class FileUtil {
    public static Path writeToFile(Request request) {
        try {
            Path filePath = Path.of(request.getFileName());
            Files.deleteIfExists(filePath); // Fayl bor bo‘lsa, avval o‘chiramiz
            try (FileWriter writer = new FileWriter(filePath.toFile())) {
                if (request.getType().equalsIgnoreCase("json")) {
                    writer.write(getGenerateJson(request));
                }
                if (request.getType().equalsIgnoreCase("csv")) {
                    writer.write(getGenerateCsv(request));
                }
                if (request.getType().equalsIgnoreCase("sql")) {
                    writer.write(getGenerateSql(request));
                }
            }
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("❌ Fayl yozishda xatolik!", e);
        }
    }

    private static String getGenerateJson(Request request) {
        List<Pairs> pairs = request.getPairs();
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "\n]");
        List<Entry> neededMethods = getNeededMethods(pairs);

        for (int i = 0; i < request.getCount(); i++) {
            StringJoiner joiner = new StringJoiner(",\n", "\n{\n", "\n}");
            for (Entry entry : neededMethods) {
                FieldType fieldType = entry.getFieldType();
                String fieldName = entry.getFieldName();
                joiner.add(fieldType.getJsonPairs(fieldName, entry.getSupplier().get()));
            }
            stringJoiner.add(joiner.toString());
        }
        return stringJoiner.toString();
    }

    private static String getGenerateCsv(Request request) {
        List<Pairs> pairs = request.getPairs();
        List<Entry> neededMethods = getNeededMethods(pairs);

        StringJoiner csvString = new StringJoiner("\n");

        StringJoiner headerJoiner = new StringJoiner(",");
        for (Entry entry : neededMethods) {
            headerJoiner.add(entry.getFieldName());
        }
        csvString.add(headerJoiner.toString());

        for (int i = 0; i < request.getCount(); i++) {
            StringJoiner rowJoiner = new StringJoiner(",");
            for (Entry entry : neededMethods) {
                rowJoiner.add(entry.getSupplier().get().toString());
            }
            csvString.add(rowJoiner.toString());
        }
        return csvString.toString();
    }

    private static String getGenerateSql(Request request) {
        List<Pairs> pairs = request.getPairs();
        List<Entry> neededMethods = getNeededMethods(pairs);
        String tableName = request.getFileName().replace(".sql", "");

        StringJoiner sqlString = new StringJoiner(";\n", "", ";");

        StringJoiner columns = new StringJoiner(", ", "(", ")");
        for (Entry entry : neededMethods) {
            columns.add(entry.getFieldName());
        }

        for (int i = 0; i < request.getCount(); i++) {
            StringJoiner values = new StringJoiner(", ", "(", ")");
            for (Entry entry : neededMethods) {
                Object value = entry.getSupplier().get();
                values.add(formatSqlValue(value));
            }
            sqlString.add("INSERT INTO " + tableName + " " + columns + " VALUES " + values);
        }
        return sqlString.toString();
    }

    private static List<Entry> getNeededMethods(List<Pairs> pairs) {
        List<Entry> methods = new ArrayList<>();
        pairs.forEach(pair -> {
            methods.add(new Entry(pair.getFieldType(), pair.getFieldName(), FakeDataGenerator.functions.get(pair.getFieldType())));
        });

        return methods;
    }

    private static String formatSqlValue(Object value) {
        if (value instanceof String) {
            return "'" + value.toString().replace("'", "''") + "'";
        }
        return value.toString();
    }
}
