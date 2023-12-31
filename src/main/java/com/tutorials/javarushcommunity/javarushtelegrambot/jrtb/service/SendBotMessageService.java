package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service;

import java.util.List;

/**
 * Service for sending messages via telegram-bot.
 */
public interface SendBotMessageService {

    /**
     * Send message via telegram bot.
     *
     * @param chatId provided chatId in which message would be sent.
     * @param message provided message to be sent.
     */
    void sendMessage(String chatId, String message);

    void sendMessage(String chatId, List<String> messages);
}
