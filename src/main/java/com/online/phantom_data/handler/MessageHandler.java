package com.online.phantom_data.handler;

import com.online.phantom_data.command.MessageCommand;
import com.online.phantom_data.service.BotService;
import com.online.phantom_data.util.KeyboardUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageHandler {
    private final BotService botService;

    public MessageHandler(BotService botService) {
        this.botService = botService;
    }

    public void handleTextMessage(Update update) {
        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        MessageCommand command = MessageCommand.fromString(text);

        if (command == null) {
            botService.sendMessage(update.getMessage(), new SendMessage(chatId.toString(), "❌ Nomalum buyruq!"));
            return;
        }

        if (command.equals(MessageCommand.START)) {
            botService.sendMessage(update.getMessage(), generateStartCommandMessage(chatId.toString()));
            return;
        }

        if (command.equals(MessageCommand.HELP)) {
            botService.sendMessage(update.getMessage(), generateHelpCommandMessage(chatId.toString()));
            return;
        }
    }

    private static SendMessage generateStartCommandMessage(String chatId) {
        String text = """
        <b>👻 Phantom data botiga xush kelibsiz!</b>
        \n<i>File type ni tanlang, buning uchun pastdagi tugmalardan birini bosing:</i>
        """;

        SendMessage message = new SendMessage(chatId, text);
        message.setParseMode("HTML");
        message.setReplyMarkup(KeyboardUtil.generateFormatKeyboard());
        return message;
    }

    private static SendMessage generateHelpCommandMessage(String chatId) {
        String text = """
        💁‍♂️ <b>Biz bilan bog‘lanish</b>
        \n<i>Agar sizda savollar bo‘lsa yoki qo‘shimcha ma’lumot kerak bo‘lsa, quyidagi raqamga bog‘laning:</i>
        \n📱 <i>Telefon raqam:</i> <a href="tel:+998909876754">+998 (90) 987-67-54</a>
        """;

        SendMessage message = new SendMessage(chatId, text);
        message.setParseMode("HTML");
        return message;
    }
}
