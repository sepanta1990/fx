package com.paf.exercise.exercise.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashSet;

/**
 * @author Mohammad Fathizadeh 2020-01-04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tournament {
    private Integer id;
    private Integer rewardAmount;
    private HashSet<Player> players = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Integer rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public HashSet<Player> getPlayers() {
        return players;
    }

    public void setPlayers(HashSet<Player> players) {
        this.players = players;
    }
}
