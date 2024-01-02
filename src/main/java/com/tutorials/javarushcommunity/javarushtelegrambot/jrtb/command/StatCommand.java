package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.command.annotation.AdminCommand;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.dto.StatisticDTO;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.SendBotMessageService;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.StatisticsService;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

@AdminCommand
public class StatCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final StatisticsService statisticsService;

    public final static String STAT_MESSAGE = "<b>I prepared statistic</b>\n"
            + "- Amount of active users: %s\n"
            + "- Amount of inactive users: %s\n"
            + "- Average amount of group per one user: %s\n\n"
            + "<b>Information about active groups:</b>\n"
            + "%s";

    @Autowired
    public StatCommand(SendBotMessageService sendBotMessageService, StatisticsService statisticsService) {
        this.sendBotMessageService = sendBotMessageService;
        this.statisticsService = statisticsService;
    }

    @Override
    public void execute(Update update) {
        StatisticDTO statisticDTO = statisticsService.countBotStatistic();

        String collectedGroups = statisticDTO.getGroupStatDTOs().stream()
                .map(it -> String.format("%s (id = %s) - %s subscribers", it.getTitle(), it.getId(), it.getActiveUserCount()))
                .collect(Collectors.joining("\n"));

        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),
                String.format(STAT_MESSAGE,
                        statisticDTO.getActiveUserCount(),
                        statisticDTO.getInactiveUserCount(),
                        statisticDTO.getAverageGroupCountByUser(),
                        collectedGroups
                ));
    }
}
