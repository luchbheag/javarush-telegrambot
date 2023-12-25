package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.repository.entity.TelegramUser;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.SendBotMessageService;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.TelegramUserService;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandName.ADD_GROUP_SUB;

import javax.ws.rs.NotFoundException;

import java.util.stream.Collectors;

import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandUtils.getChatId;

public class ListGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private  final TelegramUserService telegramUserService;

    public ListGroupSubCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        // TODO add eexception handling
        TelegramUser telegramUser = telegramUserService.findByChatId(getChatId(update))
                .orElseThrow(NotFoundException::new);

        String message;
        if (CollectionUtils.isEmpty(telegramUser.getGroupSubs())) {
            message = String.format("You have no supscriptions yet. To add subscription send %s", ADD_GROUP_SUB.getCommandName());
        } else {

            String collectedGroupds = telegramUser.getGroupSubs().stream()
                    .map(it -> "Group: " + it.getTitle() + ", ID = " + it.getId() + " \n")
                    .collect(Collectors.joining());
            message = String.format("I found all your group subscriptions: %s\n\n", collectedGroupds);
        }
        sendBotMessageService.sendMessage(telegramUser.getChatId(), message);
    }
}
