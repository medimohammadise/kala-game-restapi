package com.codebase.codechallenge.kalagame.model;

import com.codebase.codechallenge.kalagame.enums.GameStatus;
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
    GameStatus status;

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

    public void doMoveStones(String selectedPitId) {
        if (!board.isGameOver()) {
            status=GameStatus.IN_PROGRESS;
            int requestedPlayerId = board.whoIsThisPit(selectedPitId);
            log.info("requested pits belongs to player  " + requestedPlayerId);

            if (nextPlayer != -1 && requestedPlayerId != nextPlayer)
                throw new IllegalArgumentException("It is not your turn, it is player= " + nextPlayer + " turn!");
            else
                currentPlayer = requestedPlayerId;
            log.info("asking player object "+currentPlayer+" to play!");
            nextPlayer = players[currentPlayer].doMove(selectedPitId, board);
            log.info("next player should be " + nextPlayer);
            if (board.isGameOver()) status=GameStatus.OVER;
        }
        else
            throw new IllegalArgumentException("game is over");

    }
    public Map<String,Integer> getStoneStatuse(){
        return board.getPits();
    }
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
