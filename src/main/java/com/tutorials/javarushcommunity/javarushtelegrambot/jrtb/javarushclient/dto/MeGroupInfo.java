package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.dto;

import lombok.Data;

/**
 * Group information related to authorized user.
 */
@Data
public class MeGroupInfo {
    private MeGroupInfoStatus status;
    private Integer userGroupId;
}
