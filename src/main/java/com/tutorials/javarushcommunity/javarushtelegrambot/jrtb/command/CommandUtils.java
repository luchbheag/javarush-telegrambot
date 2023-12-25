package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Utils class for Commands.
 */
public class CommandUtils {

    /**
     * Get chatIf from {@link Update} object.
     * @param update
     * @return
     */
    public static String getChatId(Update update) {
        return update.getMessage().getChatId().toString();
    }

    public static String getMessage(Update update) {
        return update.getMessage().getText();
    }
}
