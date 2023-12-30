package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.JavaRushGroupClient;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.dto.GroupRequestArgs;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.repository.entity.GroupSub;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.GroupSubService;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandName.ADD_GROUP_SUB;
import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandUtils.getChatId;
import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandUtils.getMessage;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;


/**
 * Add Group subscription {@link Command}.
 */
public class AddGroupSubCommand implements Command{

    private final SendBotMessageService sendBotMessageService;
    private final JavaRushGroupClient javaRushGroupClient;
    private final GroupSubService groupSubService;

    public AddGroupSubCommand(SendBotMessageService sendBotMessageService,
                              JavaRushGroupClient javaRushGroupClient,
                              GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.javaRushGroupClient = javaRushGroupClient;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())) {
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getMessage(update).split(SPACE)[1];
        String chatId = getChatId(update);
        if (isNumeric(groupId)) {
            GroupDiscussionInfo groupById = javaRushGroupClient.getGroupById(Integer.parseInt(groupId));
            if (isNull(groupById.getId())) {
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub savedGroupSub = groupSubService.save(chatId, groupById);
            sendBotMessageService.sendMessage(chatId, "I subsribed you to group " + savedGroupSub.getTitle());
        } else {
            sendGroupNotFound(chatId, groupId);
        }
    }

    private void sendGroupNotFound(String chatId, String groupId) {
        String groupNotFoundMessage = "There is no group with ID = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }
    private void sendGroupIdList(String chatId) {
        String groupIds = javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build()).stream()
                .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        String message = "To subscribe group send command with group ID. \n" +
                "For example: /addGroupSub 16. \n\n" +
                "I prepared list of all groups. Choose any of them!\n\n" +
                "name of the group - ID of the group \n\n" +
                "%s";

        sendBotMessageService.sendMessage(chatId, String.format(message, groupIds));
    }

}
