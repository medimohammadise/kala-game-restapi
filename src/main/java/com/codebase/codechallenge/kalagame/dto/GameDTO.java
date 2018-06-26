package com.codebase.codechallenge.kalagame.dto;

public class GameDTO {
    int gameId;
    BoardDTO boardDTO;
    int currentPlayer;
    int nextPlayer;
    PlayerDTO[] players;

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
}
