package com.codebase.codechallenge.kalagame.model;

import com.codebase.codechallenge.kalagame.domain.GameEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Game {
    Logger log= LoggerFactory.getLogger(getClass());
    Integer gameId;
    Board board;
    Player[] players=new Player[2];
    int currentPlayer=-1;
    int nextPlayer=-1;

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(int nextPlayer) {
        this.nextPlayer = nextPlayer;
    }


    public Game(){

    }
    public Game(int gameId,Map<String,Integer> pits) {
        this.gameId = gameId;
        //TODO pass game state to the board
        this.board=new Board(gameId,pits);
        players[0]=new Player(0);
        players[1]=new Player(1);
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

    public void doMove(String pitId) {
        log.info("currentPlayer " +currentPlayer+" nextPlayer "+nextPlayer);
        int requestedPlayerId=board.whoIsThisPit(pitId);
        if (nextPlayer!=-1 && requestedPlayerId!= nextPlayer)
            throw new IllegalArgumentException("It is not your turn, it is player= "+ nextPlayer +" turn!");
        else
             currentPlayer =requestedPlayerId;
        log.info("currentPlayer playing ---> "+currentPlayer);
                nextPlayer = players[currentPlayer].doMove(pitId,board,false);
    }
    public Map<String,Integer> getStoneStatuse(){
        return board.getPits();
    }
    public int getCurrentPlayer(){
        return currentPlayer;
    }

}
