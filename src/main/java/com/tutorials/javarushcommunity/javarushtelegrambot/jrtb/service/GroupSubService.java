package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.repository.entity.GroupSub;

import java.util.List;
import java.util.Optional;

/**
 * Service for manipulating with {@link GroupSub}.
 */
public interface GroupSubService {
    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);

    GroupSub save(GroupSub groupSub);

    Optional<GroupSub> findById(Integer id);
    List<GroupSub> findAll();
}
