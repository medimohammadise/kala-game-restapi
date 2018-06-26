package com.codebase.codechallenge.kalagame.model;

import com.codebase.codechallenge.kalagame.domain.GameEntity;

import java.util.Map;

public class Game {
    Integer gameId;
    Board board;
    Player[] players=new Player[2];
    GameEntity gameEntity;
    int currentPlayer;
    int nextPlayer;
    public Game(){

    }
    public Game(GameEntity gameEntity) {
        this.gameId = gameEntity.getId();
        //TODO pass game state to the board
        this.board=new Board(gameId);
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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board borad) {
        this.board = borad;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public int doMove(String pitId) {

        int requestedPlayerId=board.whoIsThisPit(pitId);
        if (requestedPlayerId== nextPlayer)
            currentPlayer =requestedPlayerId;
        else
            throw new IllegalArgumentException("It is not your turn, it is player= "+ nextPlayer +" turn!");
        nextPlayer = players[currentPlayer].doMove(pitId,board,false);
        return nextPlayer;

    }
    public Map<String,Integer> getStoneStatuse(){
        return board.getPits();
    }
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public GameEntity getGameEntity() {
        return gameEntity;
    }
}
