package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.repository.entity.GroupSub;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.repository.entity.TelegramUser;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.GroupSubService;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.SendBotMessageService;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.TelegramUserService;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandName.DELETE_GROUP_SUB;
import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandName.ADD_GROUP_SUB;
import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandUtils.getChatId;
import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandUtils.getMessage;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Delete Group subscription {@link Command}.
 */

public class DeleteGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final GroupSubService groupSubService;

    public DeleteGroupSubCommand(SendBotMessageService sendBotMessageService,
                                 TelegramUserService telegramUserService,
                                 GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(DELETE_GROUP_SUB.getCommandName())) {
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getMessage(update).split(SPACE)[1];
        String chatId = getChatId(update);
        if (isNumeric(groupId)) {
            Optional<GroupSub> optionalGroupSub = groupSubService.findById(Integer.valueOf(groupId));
            if (optionalGroupSub.isPresent()) {
                GroupSub groupSub = optionalGroupSub.get();
                TelegramUser telegramUser = telegramUserService.findByChatId(chatId)
                        .orElseThrow(NotFoundException::new);
                groupSub.getUsers().remove(telegramUser);
                groupSubService.save(groupSub);

                sendBotMessageService.sendMessage(chatId, "I deleted supscription on group " + groupSub.getTitle());
            } else {
                sendBotMessageService.sendMessage(chatId, "I didn't find this group.");
            }



        } else {
            sendBotMessageService.sendMessage(chatId, "Wrond groupd ID format.\n " +
                    "ID must be positive integer number.");
        }


    }

    private void sendGroupIdList(String chatId) {
        String message;
        List<GroupSub> groupSubs = telegramUserService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new)
                .getGroupSubs();
        if (CollectionUtils.isEmpty(groupSubs)) {
            message = "There is no group subscruptions yet. To add one send " + ADD_GROUP_SUB.getCommandName();
        } else {
            message = "To delete group subscription send command together with group ID. \n"
                    + "For example: " + DELETE_GROUP_SUB.getCommandName() + " 16 \n\n"
                    + "I prepared a list of all groups you subscribed \n\n"
                    + "group name - group ID \n\n"
                    + "%s";
        }
        String userGroupSubData = groupSubs.stream()
                .map(group -> format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        sendBotMessageService.sendMessage(chatId, format(message, userGroupSubData));
    }



}
