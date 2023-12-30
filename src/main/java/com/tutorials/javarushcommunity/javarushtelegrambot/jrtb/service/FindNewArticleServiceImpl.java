package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.service;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.JavaRushPostClient;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.dto.LikeStatus;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.dto.PostInfo;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.repository.entity.GroupSub;
import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindNewArticleServiceImpl implements FindNewArticleService {

    public static final String JAVARUSH_WEB_POS_FORMAT = "https://javarush.com/groups/posts/%s";

    private final GroupSubService groupSubService;
    private final SendBotMessageService sendMessageService;
    private final JavaRushPostClient javaRushPostClient;

    @Autowired
    public FindNewArticleServiceImpl(GroupSubService groupSubService,
                                     SendBotMessageService sendMessageService,
                                     JavaRushPostClient javaRushPostClient) {
        this.groupSubService = groupSubService;
        this.sendMessageService = sendMessageService;
        this.javaRushPostClient = javaRushPostClient;
    }

    @Override
    public void findNewArticles() {
        groupSubService.findAll().forEach(gSub -> {
            List<PostInfo> newPosts = javaRushPostClient.findNewPosts(gSub.getId(), gSub.getLastArticleId());

            setNewLastArticleId(gSub, newPosts);

            notifySubscribersAboutNewArticles(gSub, newPosts);
        });
    }

    private void notifySubscribersAboutNewArticles(GroupSub gSub, List<PostInfo> newPosts) {
        Collections.reverse(newPosts);
        List<String> messagesWithNewArticles = newPosts.stream()
                .map(post -> String.format("New article <b>%s</b> is published in group <b>%s</b>.\n\n"
                + "<b>Description:</b> %s \n\n"
                + "<b>Link:</b> %s \n",
                        post.getTitle(), gSub.getTitle(), post.getDescription(), getPostUrl(post.getKey())))
                .collect(Collectors.toList());

        gSub.getUsers().stream()
                .filter(TelegramUser::isActive)
                .forEach(it -> sendMessageService.sendMessage(it.getChatId(), messagesWithNewArticles));
    }

    private void setNewLastArticleId(GroupSub gSub, List<PostInfo> newPosts) {
        newPosts.stream().mapToInt(PostInfo::getId).max()
                .ifPresent(id -> {
                    gSub.setLastArticleId(id);
                    groupSubService.save(gSub);
                });
    }

    private String getPostUrl(String key) {
        return String.format(JAVARUSH_WEB_POS_FORMAT, key);
    }
}
