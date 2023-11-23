package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command;

import org.junit.jupiter.api.DisplayName;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandName.START;
import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.StartCommand.START_MESSAGE;


@DisplayName("Unit-level testing for StartCommand")
public class StartCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return START.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return START_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StartCommand(sendBotMessageService);
    }
}
