package com.codebase.codechallenge.kalagame.dto;



import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

public class MoveOutcomeDTO {
    int gameId;
    URL gameURL;
    int currentPalyer;
    int nextPlayerTurn;
    Map<String,Integer> pitsStatus;

    public MoveOutcomeDTO(int gameId, URL gameURL, Map<String,Integer> pitsStatus,int currentPalyer,int nextPlayerTurn) {
        this.gameId = gameId;
        this.gameURL = gameURL;
        this.pitsStatus = pitsStatus;
        this.currentPalyer=currentPalyer;
        this.nextPlayerTurn=nextPlayerTurn;
        try {
            this.gameURL=new URL("https://" +InetAddress.getLocalHost().getHostAddress()+"/"+"games/"+String.valueOf(gameId));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public URL getGameURL() {
        return gameURL;
    }

    public void setGameURL(URL gameURL) {
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
