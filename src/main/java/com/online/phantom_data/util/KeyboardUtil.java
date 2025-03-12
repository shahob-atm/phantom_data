package com.online.phantom_data.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.app.enums.FieldType;

import java.util.ArrayList;
import java.util.List;

public class KeyboardUtil {
    public static InlineKeyboardMarkup generateFieldTypeKeyboard() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        InlineKeyboardButton button;
        List<InlineKeyboardButton> row = new ArrayList<>();

        for (FieldType fieldType : FieldType.values()) {
            button = new InlineKeyboardButton();
            button.setText(fieldType.name());
            button.setCallbackData("FIELD_" + fieldType.name());

            row.add(button);
            if (row.size() == 2) {  // Har bir qatorda 2 ta tugma bo‘lishi kerak
                keyboard.add(row);
                row = new ArrayList<>();
            }
        }

        // Agar oxirgi qator to‘liq bo‘lmasa, uni ham qo‘shamiz
        if (!row.isEmpty()) {
            keyboard.add(row);
        }

        // GENERATE tugmachasini qo‘shamiz
        InlineKeyboardButton generateButton = new InlineKeyboardButton();
        generateButton.setText("🚀 Generate");
        generateButton.setCallbackData("GENERATE".toLowerCase());

        List<InlineKeyboardButton> generateRow = new ArrayList<>();
        generateRow.add(generateButton);
        keyboard.add(generateRow);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        return markup;
    }

    public static InlineKeyboardMarkup generateFormatKeyboard() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        // 3 ta format tugmachasi
        String[] formats = {"JSON", "CSV", "SQL"};
        for (String format : formats) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(format);
            button.setCallbackData("FORMAT_" + format);
            row.add(button);
        }
        keyboard.add(row); // Barcha tugmalar bitta qatorga qo‘shiladi

        // Keyboard markup yaratish
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        return markup;
    }
}
