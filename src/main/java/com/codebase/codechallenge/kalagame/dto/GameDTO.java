package com.codebase.codechallenge.kalagame.dto;

import com.codebase.codechallenge.kalagame.enums.GameStatus;

public class GameDTO {
    int gameId;
    BoardDTO boardDTO;
    int currentPlayer;
    int nextPlayer;
    PlayerDTO[] players;
    GameStatus status;

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setNextPlayer(int nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public GameDTO() {
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }


    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public PlayerDTO[] getPlayers() {
        return players;
    }

    public void setPlayers(PlayerDTO[] players) {
        this.players = players;
    }

    public BoardDTO getBoardDTO() {
        return boardDTO;
    }

    public void setBoardDTO(BoardDTO boardDTO) {
        this.boardDTO = boardDTO;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
