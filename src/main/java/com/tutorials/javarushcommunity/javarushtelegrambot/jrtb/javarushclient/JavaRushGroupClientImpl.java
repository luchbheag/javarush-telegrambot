package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient;

import com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.dto.*;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Implementation of the {@link JavaRushGroupClient} interface.
 */
@Component
public class JavaRushGroupClientImpl implements JavaRushGroupClient {

    private final String javarushApiGroupPath;
    private final String getJavarushApiPostPath;

    public JavaRushGroupClientImpl(@Value("${javarush.api.path}") String javarushApi) {
        this.javarushApiGroupPath = javarushApi + "/groups";
        this.getJavarushApiPostPath = javarushApi + "/posts";
    }

    @Override
    public List<GroupInfo> getGroupList(GroupRequestArgs requestArgs) {
        return Unirest.get(javarushApiGroupPath)
                .queryString(requestArgs.populateQueries())
                .asObject(new GenericType<List<GroupInfo>>() {
                })
                .getBody();
    }

    @Override
    public List<GroupDiscussionInfo> getGroupDiscussionList(GroupRequestArgs requestArgs) {
        return Unirest.get(javarushApiGroupPath)
                .queryString(requestArgs.populateQueries())
                .asObject(new GenericType<List<GroupDiscussionInfo>>() {
                })
                .getBody();
    }

    @Override
    public Integer getGroupCount(GroupsCountRequestArgs countRequestArgs) {
        return Integer.valueOf(
                Unirest.get(String.format("%s/count", javarushApiGroupPath))
                        .queryString(countRequestArgs.populateQueries())
                        .asString()
                        .getBody()
        );
    }

    @Override
    public GroupDiscussionInfo getGroupById(Integer id) {
        System.out.println(String.format("%s/group%s", javarushApiGroupPath, id.toString()));
        GroupDiscussionInfo result = Unirest.get(String.format("%s/group%s", javarushApiGroupPath, id.toString()))
                .asObject(GroupDiscussionInfo.class)
                .getBody();
        return result;
    }

    @Override
    public int findLastArticleId(int groupSubId) {
        List<PostInfo> posts = Unirest.get(getJavarushApiPostPath)
                .queryString("order", "NEW")
                .queryString("groupKid", String.valueOf(groupSubId))
                .queryString("limit", "1")
                .asObject(new GenericType<List<PostInfo>>() {
                })
                .getBody();
        return isEmpty(posts) ? 0 : Optional.ofNullable(posts.get(0)).map(PostInfo::getId).orElse(0);
    }
}
