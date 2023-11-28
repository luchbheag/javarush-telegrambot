package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandName.STAT;
import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.StatCommand.STAT_MESSAGE;

public class StatCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return String.format(STAT_MESSAGE, 0);
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }

    @Override
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        super.shouldProperlyExecuteCommand();
    }
}
