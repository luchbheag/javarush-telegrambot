package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.dto.PostInfo;

import java.util.List;

/**
 * Client for Javarush Open API corresponds to Posts.
 */
public interface JavaRushPostClient {
    List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId);
}
