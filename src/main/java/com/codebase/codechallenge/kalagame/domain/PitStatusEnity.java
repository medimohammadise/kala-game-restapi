package com.codebase.codechallenge.kalagame.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PitStatusEnity {
    @Id
    int key;
    int value;
    @ManyToOne
    @JoinColumn(name = "game_id")
    GameEntity gameEntity;

    public PitStatusEnity(int key, int value, GameEntity gameEntity) {
        this.key = key;
        this.value = value;
        this.gameEntity = gameEntity;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
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
