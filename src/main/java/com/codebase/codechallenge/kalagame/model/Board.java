package com.codebase.codechallenge.kalagame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {
    private Map<String, Integer> pits;
    private Integer gameId;

    public void setPits(Map<String, Integer> pits) {
        this.pits = pits;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Board() {


    }

    /**
     * Initialise the Board
     */
    public Board(Integer gameId, Map<String, Integer> pits) {
        this.gameId = gameId;
        this.pits = pits;
    }

    /**
     * The board index of a players
     *
     * @param player The player (0 or 1)
     * @param pits   The cup index (1 to 6)
     * @return The real index of the cup (0 to 13)
     */
    private String getPitIndexForPlayer(int player, String pit) {

        int realCup = (player * 7) + Integer.valueOf(pit);
        return String.valueOf(realCup);
    }

    /**
     * @param pitIndex
     * @return 0 for player 0 and 1 for player 1
     */
    protected int whoIsThisPit(String pitIndex) {
        return Integer.valueOf(pitIndex) > 7 ? 1 : 0; // player = 1;
    }

    /**
     * Add a stone to a pit at the given index
     *
     * @param index
     */
    public void addStoneToPit(String index) {
        pits.merge(index,1,(oldValue,value)->oldValue+value);
        //pits.put(index, pits.get(index) + 1);
    }

    /**
     * Add numberOfStonesToAdd stones to a pit at the given index
     *
     * @param index
     */
    public void addStonesToPit(String index, int numberOfStonesToAdd) {
        pits.merge(index,numberOfStonesToAdd,(oldValue,value)->oldValue+value);
        //pits.put(index, pits.get(index) + numberOfStonesToAdd);
    }


    /**
     * Clear the stones in a given pit
     *
     * @param index
     */
    public void clearPit(String index) {
        pits.put(index, 0);
    }

    public Map<String, Integer> getPits() {
        return pits;
    }


    /**
     * The game is over if on this board either player can make no more moves.
     * That is if either player has any normal stones with pits in the game is
     * not over
     *
     * @return true If the game is over
     */
    public boolean isGameOver() {
        return (pits.entrySet().stream().filter(pit -> Integer.valueOf(pit.getKey()) < 7 && pit.getValue() == 0).count() == 6 ||
                pits.entrySet().stream().filter(pit -> Integer.valueOf(pit.getKey()) < 14 && Integer.valueOf(pit.getKey()) > 7 && pit.getValue() == 0).count() == 6);
    }

    /**
     * The available cups to "move" for a player
     *
     * @param player The player number (0 or 1)
     * @return An array containing the indexes of the pits that can be moved by a
     * player
     */
    public List<String> getAvailablePitsForMove(int player) {
        List<String> availablePitsForMove = new ArrayList();
        String availableIndex = "";
        for (int i = 1; i < 7; i++) {
            availableIndex = getPitIndexForPlayer(player, String.valueOf(i));
            if (pits.get(availableIndex) > 0) {
                // add a new move
                availablePitsForMove.add(availableIndex);
            }
        }
        return availablePitsForMove;
    }

}
