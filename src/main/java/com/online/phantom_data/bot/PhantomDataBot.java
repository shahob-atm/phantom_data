package com.online.phantom_data.bot;

import com.online.phantom_data.config.BotConfig;
import com.online.phantom_data.handler.CallbackHandler;
import com.online.phantom_data.handler.MessageHandler;
import com.online.phantom_data.service.BotService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@SuppressWarnings("deprecation")
public class PhantomDataBot extends TelegramLongPollingBot {
    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;

    public PhantomDataBot() {
        BotService botService = new BotService(this);
        this.messageHandler = new MessageHandler(botService);
        this.callbackHandler = new CallbackHandler(botService);
    }

    @Override
    public void onUpdateReceived(Update update) {
        processUpdate(update);
    }

    @Override
    public String getBotUsername() {
        return BotConfig.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BotConfig.BOT_TOKEN;
    }

    private void processUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            messageHandler.handleTextMessage(update);
        } else if (update.hasCallbackQuery()) {
            callbackHandler.handleCallbackQuery(update.getCallbackQuery());
        }
    }
}
