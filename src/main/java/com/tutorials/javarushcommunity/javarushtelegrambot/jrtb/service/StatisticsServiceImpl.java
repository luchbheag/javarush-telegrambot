package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.dto.GroupStatDTO;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.dto.StatisticDTO;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.repository.entity.TelegramUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final GroupSubService groupSubService;
    private final TelegramUserService telegramUserService;

    public StatisticsServiceImpl(GroupSubService groupSubService, TelegramUserService telegramUserService) {
        this.groupSubService = groupSubService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public StatisticDTO countBotStatistic() {
        List<GroupStatDTO> groupStatDTOs = groupSubService.findAll().stream()
                .filter(it -> !isEmpty(it.getUsers()))
                .map(groupSub -> new GroupStatDTO(groupSub.getId(), groupSub.getTitle(), groupSub.getUsers().size()))
                .collect(Collectors.toList());
        List<TelegramUser> allInactiveUsers = telegramUserService.findAllInactiveUsers();
        List<TelegramUser> allActiveUsers = telegramUserService.findAllActiveUsers();

        double groupsPerUser = getGroupPerUser(allActiveUsers);
        return new StatisticDTO(allActiveUsers.size(), allInactiveUsers.size(), groupStatDTOs, groupsPerUser);
    }

    private double getGroupPerUser(List<TelegramUser> allActiveUsers) {
        return (double)allActiveUsers.stream().mapToInt(it -> it.getGroupSubs().size()).sum() / allActiveUsers.size();
    }
}
