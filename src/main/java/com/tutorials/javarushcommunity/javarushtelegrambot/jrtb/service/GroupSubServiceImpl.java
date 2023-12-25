package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.repository.GroupSubRepository;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.repository.entity.GroupSub;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.repository.entity.TelegramUser;
import javax.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupSubServiceImpl implements GroupSubService {
    private final GroupSubRepository groupSubRepository;
    private final TelegramUserService telegramUserService;

    @Autowired
    public GroupSubServiceImpl(GroupSubRepository groupSubRepository, TelegramUserService telegramUserService) {
        this.groupSubRepository = groupSubRepository;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo) {
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
        // TODO add exception handling
        GroupSub groupSub;
        Optional<GroupSub> groupSubFromDB = groupSubRepository.findById(groupDiscussionInfo.getId());
        if (groupSubFromDB.isPresent()) {
            System.out.println("if block");
            groupSub = groupSubFromDB.get();
            System.out.println("after get");
            Optional<TelegramUser> first = groupSub.getUsers().stream()
                    .filter(it -> it.getChatId().equalsIgnoreCase(chatId))
                    .findFirst();
            if (first.isEmpty()) {
                groupSub.addUser(telegramUser);
            }
        } else {
            groupSub = new GroupSub();
            groupSub.addUser(telegramUser);
            groupSub.setId(groupDiscussionInfo.getId());
            groupSub.setTitle(groupDiscussionInfo.getTitle());
        }

        return groupSubRepository.save(groupSub);
    }

    @Override
    public GroupSub save(GroupSub groupSub) {
        return groupSubRepository.save(groupSub);
    }

    @Override
    public Optional<GroupSub> findById(Integer id) {
        return groupSubRepository.findById(id);
    }
}
