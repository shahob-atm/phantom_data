package com.online.phantom_data;

import com.online.phantom_data.bot.PhantomDataBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        startBot();
    }

    private static void startBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new PhantomDataBot());
            System.out.println("✅ PhantomData bot ishga tushdi!");
        } catch (TelegramApiException e) {
            System.err.println("❌ Botni ishga tushirishda xatolik!");
            e.printStackTrace();
        }
    }
}