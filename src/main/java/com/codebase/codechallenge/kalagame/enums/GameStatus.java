package com.codebase.codechallenge.kalagame.enums;

public enum GameStatus {
    CREATED("C"),IN_PROGRESS("P"),OVER("O");

    public String getStatus() {
        return status;
    }

    private String status;
    GameStatus(String status){
        this.status=status;

    }

}
