package com.codebase.codechallenge.kalagame.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PitStatusEnity {
    @Id
    String key;
    int value;
    @ManyToOne
    @JoinColumn(name = "game_id")
    GameEntity gameEntity;

    public PitStatusEnity(String key, int value, GameEntity gameEntity) {
        this.key = key;
        this.value = value;
        this.gameEntity = gameEntity;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public GameEntity getGameEntity() {
        return gameEntity;
    }

    public void setGameEntity(GameEntity gameEntity) {
        this.gameEntity = gameEntity;
    }
}
