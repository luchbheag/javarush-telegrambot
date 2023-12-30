package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.dto.PostInfo;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JavaRushPostClientImpl implements JavaRushPostClient {

    private final String javaRushApiPostPath;

    public JavaRushPostClientImpl(@Value("${javarush.api.path}") String javaRushApiPostPath) {
        this.javaRushApiPostPath = javaRushApiPostPath + "/posts";
    }

    @Override
    public List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId) {
        //System.out.println(groupId);
        List<PostInfo> lastPostsByGroup = Unirest.get(javaRushApiPostPath)
                .queryString("order", "NEW")
                .queryString("groupKid", groupId)
                .queryString("limit", 15)
                .asObject(new GenericType<List<PostInfo>>() {
                }).getBody();
        //System.out.println(lastPostsByGroup);
        List<PostInfo> newPosts = new ArrayList<>();
        for (PostInfo post : lastPostsByGroup) {
            if (lastPostId.equals(post.getId())) {
                return newPosts;
            }
            newPosts.add(post);
        }
        return newPosts;
    }
}
