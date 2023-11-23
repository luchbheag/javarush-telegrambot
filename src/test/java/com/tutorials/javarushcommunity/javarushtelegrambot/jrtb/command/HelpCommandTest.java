package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command;

import org.junit.jupiter.api.DisplayName;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandName.HELP;
import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.HelpCommand.HELP_MESSAGE;


@DisplayName("Unit-level testing for HelpCommand")
public class HelpCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new HelpCommand(sendBotMessageService);
    }
}
