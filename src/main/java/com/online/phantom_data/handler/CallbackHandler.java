package com.online.phantom_data.handler;

import com.online.phantom_data.dto.RequestDTO;
import com.online.phantom_data.service.BotService;
import com.online.phantom_data.util.FileUtil;
import com.online.phantom_data.util.KeyboardUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import uz.pdp.app.enums.FieldType;
import uz.pdp.app.model.Pairs;
import uz.pdp.app.model.Request;

import java.nio.file.Path;
import java.util.*;

public class CallbackHandler {
    private final BotService botService;
    private final Map<Long, RequestDTO> userRequests = new HashMap<>();

    public CallbackHandler(BotService botService) {
        this.botService = botService;
    }

    public void handleCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String data = callbackQuery.getData();

        // Har bir foydalanuvchi uchun alohida `RequestDTO` obyektini saqlash
        userRequests.putIfAbsent(chatId, new RequestDTO());
        RequestDTO requestDTO = userRequests.get(chatId);

        if (requestDTO.getFields() == null) {
            requestDTO.setFields(new ArrayList<>());
        }

        if (data.startsWith("FORMAT_")) {
            requestDTO.setFormat(data.replace("FORMAT_", ""));
            SendMessage message = new SendMessage(chatId.toString(), "<b>Field type larni tanlang: </b>");
            message.setReplyMarkup(KeyboardUtil.generateFieldTypeKeyboard());
            message.setParseMode("HTML");
            botService.sendMessage(callbackQuery.getMessage(), message);
        } else if (data.startsWith("FIELD_")) {
            requestDTO.getFields().add(FieldType.valueOf(data.replace("FIELD_", "")));
            System.out.println(requestDTO.getFields().toString());
            SendMessage message = new SendMessage(chatId.toString(), "<b>Tanlangan fieldlar: </b>\n" + getFieldTypeValues(requestDTO.getFields()));
            message.setReplyMarkup(KeyboardUtil.generateFieldTypeKeyboard());
            message.setParseMode("HTML");
            botService.sendMessage(callbackQuery.getMessage(), message);
        } else if (data.equalsIgnoreCase("GENERATE")) {
            try {
                Path path = FileUtil.writeToFile(getRequest(requestDTO));
                botService.sendFile(callbackQuery.getMessage(), path);
            }catch (Exception e){
                botService.sendMessage(callbackQuery.getMessage(), new SendMessage(chatId.toString(), "⚠️ Fayl yozishda xatolik, qaytaa urining /start!"));
            }finally {
                userRequests.remove(chatId); // Foydalanuvchi ishini tugatgandan so‘ng uni o‘chiramiz
            }
        }
    }

    private String getFieldTypeValues(List<FieldType> fieldTypes) {
        StringJoiner stringJoiner = new StringJoiner(", ", "<i>", "</i>");
        for (FieldType fieldType : fieldTypes) {
            stringJoiner.add(fieldType.name());
        }
        return stringJoiner.toString();
    }

    private Request getRequest(RequestDTO requestDTO) {
        System.out.println("requestDTO.toString() = " + requestDTO.toString());
        List<Pairs> pairs = new ArrayList<>();
        for (FieldType field : requestDTO.getFields()) {
            pairs.add(new Pairs(field.name(), field));
        }
        return new Request("phantom." + requestDTO.getFormat().toLowerCase(), 100, requestDTO.getFormat().toLowerCase(), pairs);
    }
}
