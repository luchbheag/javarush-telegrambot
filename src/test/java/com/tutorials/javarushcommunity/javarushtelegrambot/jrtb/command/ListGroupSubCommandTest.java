package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.repository.entity.GroupSub;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.repository.entity.TelegramUser;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.SendBotMessageService;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.TelegramUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.AbstractCommandTest.prepareUpdate;
import static com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.CommandName.LIST_GROUP_SUB;

@DisplayName("Unit-level testing for ListGroupSubCommand")
public class ListGroupSubCommandTest {

    @Test
    public void shouldProperlyShowsListGroupSub() {
        // given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setActive(true);
        telegramUser.setChatId("1");

        List<GroupSub> groupSubList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            groupSubList.add(populateGroupSub(i, String.format("gs%d", i)));
        }

        telegramUser.setGroupSubs(groupSubList);

        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

        Mockito.when(telegramUserService.findByChatId(telegramUser.getChatId()))
                .thenReturn(Optional.of(telegramUser));

        ListGroupSubCommand command = new ListGroupSubCommand(sendBotMessageService, telegramUserService);

        Update update = prepareUpdate(Long.valueOf(telegramUser.getChatId()), LIST_GROUP_SUB.getCommandName());

        String collectedGroups = String.format("I found all your group subscriptions: %s\n\n",
                telegramUser.getGroupSubs().stream()
                .map(it -> "Group: " + it.getTitle() + ", ID = " + it.getId() + " \n")
                .collect(Collectors.joining()));

        // when
        command.execute(update);

        // then
        Mockito.verify(sendBotMessageService).sendMessage(telegramUser.getChatId(), collectedGroups);
    }

    private GroupSub populateGroupSub(Integer id, String title) {
        GroupSub gs = new GroupSub();
        gs.setId(id);
        gs.setTitle(title);

        return gs;
    }

}
