package com.codebase.codechallenge.kalagame.dto;



import com.codebase.codechallenge.kalagame.model.Game;

import java.net.*;
import java.util.Map;

public class MoveOutcomeDTO {
    int gameId;
    URI gameURL;
    int currentPalyer;
    int nextPlayerTurn;
    String selectedPitIndex;
    Map<String,Integer> pitsStatus;
    public MoveOutcomeDTO(){

    }
    public MoveOutcomeDTO(GameDTO gameDTO, String selectedPitIndex) throws URISyntaxException {
        this.gameId = gameDTO.getGameId();
        this.pitsStatus = gameDTO.getBoardDTO().getPits();
        this.currentPalyer=gameDTO.getCurrentPlayer();
        this.nextPlayerTurn=gameDTO.getNextPlayer();
        this.selectedPitIndex=selectedPitIndex;
        try {
            this.gameURL=new URI("http://" +InetAddress.getLocalHost().getHostAddress()+"/"+"games/"+String.valueOf(gameId)+"/pits/"+selectedPitIndex);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public URI getGameURL() {
        return gameURL;
    }

    public void setGameURL(URI gameURL) {
        this.gameURL = gameURL;
    }

    public Map<String,Integer> getPitsStatus() {
        return pitsStatus;
    }

    public void setPitsStatus(Map<String,Integer> pitsStatus) {
        this.pitsStatus = pitsStatus;
    }

    public int getCurrentPalyer() {
        return currentPalyer;
    }

    public void setCurrentPalyer(int currentPalyer) {
        this.currentPalyer = currentPalyer;
    }

    public int getNextPlayerTurn() {
        return nextPlayerTurn;
    }

    public void setNextPlayerTurn(int nextPlayerTurn) {
        this.nextPlayerTurn = nextPlayerTurn;
    }
}
