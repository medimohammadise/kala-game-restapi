package com.codebase.codechallenge.kalagame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

class Board {
    private Map<String, Integer> pits=new HashMap<>();
    private Integer gameId;
    //TODO  numbers should be fetch from property file

    /**
     * Initialise the Board
     */
    public Board(Integer gameId) {
        IntStream.range(1, 14).forEach(i -> pits.put(String.valueOf(i), 6));
        pits.put("7", 0);
        pits.put("14", 0);
    }

    /**
     * The board index of a players cup
     *
     * @param player The player (0 or 1)
     * @param cup    The cup index (0 to 5)
     * @return The real index of the cup (0 to 13)
     */
    private String getPitIndexForPlayer(int player, String pit) {

        int realCup = (player * 7) + Integer.valueOf(pit);
        return String.valueOf(realCup);
    }

    /**
     * @param pit
     * @return 0 for player 0 and 1 for player 1
     */
    protected int whoIsThisPit(String pit) {
        return Integer.valueOf(pit) > 7 ? 1 : 0; // player = 1;
    }

    /**
     * Add a bead to a cup at the given index
     *
     * @param index
     */
    public void addStoneToPit(String index) {
        pits.put(index, pits.get(index) + 1);
    }

    /**
     * Add a bead to a cup at the given index
     *
     * @param index
     */
    public void addStonesToPit(String index,int numberofStonesToAdd) {
        pits.put(index, pits.get(index) + numberofStonesToAdd);
    }


    /**
     * Clear the beads in a given cup
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
     * That is if either player has any normal cups with beads in the game is
     * not over
     *
     * @return true If the game is over
     */
    public boolean isGameOver() {
        return (pits.entrySet().stream().filter(pit -> Integer.valueOf(pit.getKey()) < 7 && pit.getValue() == 0).count() == 7
                ||
                pits.entrySet().stream().filter(pit -> Integer.valueOf(pit.getKey()) > 7 && pit.getValue() == 0).count() == 7);
    }

    /**
     * The available cups to "move" for a player
     *
     * @param player The player number (1 or 2)
     * @return An array containing the indexes of the cups that can be moved by a
     * player
     */
    public List<Integer> getAvailablePitsForMove(int player) {
        List<Integer> availablePitsForMove = new ArrayList();
        for (int i = 1; i < 7; i++) {
            if (pits.get(getPitIndexForPlayer(player, String.valueOf(i))) > 0) {
                // add a new move
                availablePitsForMove.add(i);
            }
        }
        return availablePitsForMove;
    }

}
