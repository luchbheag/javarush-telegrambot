package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command;

import org.junit.jupiter.api.DisplayName;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.UnknownCommand.UNKNOWN_MESSAGE;


@DisplayName("Unit-level testing for UnknownCommand")
public class UnknownCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return "/sadsajdssd";
    }

    @Override
    String getCommandMessage() {
        return UNKNOWN_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new UnknownCommand(sendBotMessageService);
    }
}
