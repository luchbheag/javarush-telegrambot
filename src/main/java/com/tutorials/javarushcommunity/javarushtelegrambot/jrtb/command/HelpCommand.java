package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandName.*;

/**
 * Help {@link Command}.
 */
public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE =
            String.format("<b>Available commands</b>\n\n"
            + "<b>Start/stop work with the bot</b>\n"
            + "%s - start work with me\n"
            + "%s - stop work with me\n\n"
            + "<b>Work with subscriptions</b>\n"
            + "%s - subscribe on the group of the articles\n"
            + "%s - get list of the groups you subscribed\n"
            + "%s - delete group from your subscriptions\n\n"
            + "%s - get help\n\n",
                    START.getCommandName(), STOP.getCommandName(), ADD_GROUP_SUB.getCommandName(),
                    LIST_GROUP_SUB.getCommandName(), DELETE_GROUP_SUB.getCommandName(),
                    HELP.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
