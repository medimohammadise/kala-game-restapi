package com.codebase.codechallenge.kalagame.model;

import com.codebase.codechallenge.kalagame.domain.GameEntity;

import java.util.Map;

public class Game {
    Integer gameId;
    Board borad;
    Player[] players=new Player[2];
    GameEntity gameEntity;
    int currentPlayerId;
    int nextPlayerId;
    public Game(GameEntity gameEntity) {
        this.gameId = gameEntity.getId();
        //TODO pass game state to the board
        this.borad=new Board(gameId);
        players[0]=new Player(0);
        players[1]=new Player(1);
        this.gameEntity = gameEntity;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Board getBorad() {
        return borad;
    }

    public void setBorad(Board borad) {
        this.borad = borad;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public int doMove(String pitId) {

        int requestedPlayerId=borad.whoIsThisPit(pitId);
        if (requestedPlayerId==nextPlayerId)
            currentPlayerId=requestedPlayerId;
        else
            throw new RuntimeException("It is not your turn, it is player= "+nextPlayerId+" turn!");
        nextPlayerId= players[currentPlayerId].doMove(pitId,borad,false);
        return nextPlayerId;

    }
    public Map<String,Integer> getStoneStatuse(){
        return borad.getPits();
    }
    public int getCurrentPlayerId(){
        return  currentPlayerId;
    }
}
