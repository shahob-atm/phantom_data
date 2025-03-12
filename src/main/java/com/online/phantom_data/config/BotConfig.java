package com.online.phantom_data.config;

import java.util.ResourceBundle;

public class BotConfig {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("bot");

    public static final String BOT_TOKEN = bundle.getString("BOT_TOKEN");
    public static final String BOT_USERNAME = bundle.getString("BOT_USERNAME");
}
