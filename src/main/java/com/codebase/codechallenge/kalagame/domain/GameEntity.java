package com.codebase.codechallenge.kalagame.domain;

import com.codebase.codechallenge.kalagame.enums.GameStatus;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Map;

@Entity(name="game")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="game_pits",
            joinColumns=@JoinColumn(name="game_id"))
    @MapKeyColumn(name = "pit_key")
    @Column(name = "pit_value")
    @BatchSize(size = 14)
    Map<String,Integer> pits;

    int currentPlayer;

    @Enumerated(EnumType.STRING)
    GameStatus status=GameStatus.CREATED;

    public GameEntity() {

    }

    public GameEntity(Integer id, Map<String,Integer>  pits) {
        this.id = id;
        this.pits = pits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Integer> getPits() {
        return pits;
    }

    public void setPits(Map<String, Integer> pits) {
        this.pits = pits;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
