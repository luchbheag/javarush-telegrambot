package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Group discussion info class.
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GroupDiscussionInfo extends GroupInfo {
    private UserDiscussionInfo userDiscussionInfo;
    private Integer commentsCount;
}
