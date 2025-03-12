package com.online.phantom_data.service;

import com.online.phantom_data.bot.PhantomDataBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BotService {
    private final PhantomDataBot bot;
    private final Map<Long, Integer> lastUserMessageMap = new HashMap<>();
    private final Map<Long, Integer> lastBotMessageMap = new HashMap<>();

    public BotService(PhantomDataBot phantomDataBot) {
        this.bot = phantomDataBot;
    }

    public void sendMessage(Message userMessage, SendMessage botMessage) {
        Long chatId = userMessage.getChatId();

        // Foydalanuvchining eski xabarini tekshiramiz va o‘chirib tashlaymiz
        if (lastUserMessageMap.containsKey(chatId)) {
            deleteMessage(chatId, lastUserMessageMap);
        }
        lastUserMessageMap.put(chatId, userMessage.getMessageId());

        // Botning eski xabarini tekshiramiz va o‘chirib tashlaymiz
        if (lastBotMessageMap.containsKey(chatId)) {
            deleteMessage(chatId, lastBotMessageMap);
        }

        try {
            Message executed = bot.execute(botMessage);
            lastBotMessageMap.put(chatId, executed.getMessageId());
        } catch (TelegramApiException e) {
            System.err.println("❌ Xatolik: Xabarni yuborishning iloji bo‘lmadi! " + e.getMessage());
        }
    }

    private void deleteMessage(Long chatId, Map<Long, Integer> messageMap) {
        if (!messageMap.containsKey(chatId)) return; // Agar xabar ID mavjud bo‘lmasa, chiqib ketamiz

        try {
            Integer messageId = messageMap.get(chatId);
            if (messageId == null || messageId <= 0) return; // Noto‘g‘ri ID bo‘lsa, chiqib ketamiz

            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(chatId.toString());
            deleteMessage.setMessageId(messageId);
            bot.execute(deleteMessage);
            messageMap.remove(chatId);
        } catch (TelegramApiException e) {
            System.err.println("❌ Xatolik: Xabarni o‘chirishning iloji bo‘lmadi! " + e.getMessage());
        }
    }

    public void sendFile(Message message, Path filePath) {
        Long chatId = message.getChatId();

        // Foydalanuvchining eski xabarini tekshiramiz va o‘chirib tashlaymiz
        if (lastUserMessageMap.containsKey(chatId)) {
            deleteMessage(chatId, lastUserMessageMap);
        }
        lastUserMessageMap.put(chatId, message.getMessageId());

        // Botning eski xabarini tekshiramiz va o‘chirib tashlaymiz
        if (lastBotMessageMap.containsKey(chatId)) {
            deleteMessage(chatId, lastBotMessageMap);
        }

        try {
            File file = filePath.toFile();
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(message.getChatId().toString());
            sendDocument.setDocument(new org.telegram.telegrambots.meta.api.objects.InputFile(file));
            Message executed = bot.execute(sendDocument);
            lastBotMessageMap.put(chatId, executed.getMessageId());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
