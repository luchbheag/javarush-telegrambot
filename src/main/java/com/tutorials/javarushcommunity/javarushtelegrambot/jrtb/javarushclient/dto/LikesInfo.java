package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb.javarushclient.dto;

import lombok.Data;

/**
 * DTO, which represents like's informtion.
 */
@Data
public class LikesInfo {
    private Integer count;
    private LikeStatus status;
}
