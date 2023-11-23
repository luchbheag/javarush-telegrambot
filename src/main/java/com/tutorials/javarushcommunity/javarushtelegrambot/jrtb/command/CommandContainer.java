package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command;

import com.google.common.collect.ImmutableMap;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.SendBotMessageService;

import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandName.*;

/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIndetifier) {
        return commandMap.getOrDefault(commandIndetifier, unknownCommand);
    }
}
