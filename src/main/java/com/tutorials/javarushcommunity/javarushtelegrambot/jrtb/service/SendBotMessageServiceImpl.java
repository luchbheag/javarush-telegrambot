package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.bot.JavaRushTelegramBot;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * Implementation of {@link SendBotMessageService} interface.
 */
@Service
@Component
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final JavaRushTelegramBot javarushBot;

    @Autowired
    public SendBotMessageServiceImpl(JavaRushTelegramBot javarushBot) {
        this.javarushBot = javarushBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            javarushBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String chatId, List<String> messages) {
        if (messages.isEmpty()) return;

        messages.forEach(m -> sendMessage(chatId, m));
    }
}
