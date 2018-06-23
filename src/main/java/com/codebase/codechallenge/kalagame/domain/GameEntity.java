package com.codebase.codechallenge.kalagame.domain;

import javax.persistence.*;

@Entity
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OrderColumn
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "gameEntity")
    PitStatusEnity[] pitsStatus;

    public GameEntity() {
    }

    public GameEntity(Integer id, PitStatusEnity[] pitsStatus) {
        this.id = id;
        this.pitsStatus = pitsStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PitStatusEnity[] getPitsStatus() {
        return pitsStatus;
    }

    public void setPitsStatus(PitStatusEnity[] pitsStatus) {
        this.pitsStatus = pitsStatus;
    }
}
