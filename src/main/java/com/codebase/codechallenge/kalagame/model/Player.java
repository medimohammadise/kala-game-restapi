package com.codebase.codechallenge.kalagame.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
public class Player {
    /**
     * Player number
     */
    protected int number;
    Logger log= LoggerFactory.getLogger(getClass());

    public Player(){

    }
    /**
     * Create a numbered player
     *
     * @param number
     */
    public Player(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    /**
     * Create an outcome representing a particular move.
     *
     * @param player The player making the move (0 or 1)
     * @param cup    The cup to move (0 to 5)
     * @param update Notify update listeners of changes to the board
     * @return If the player ha9s another go
     */
    public int doMove(String pit, Board board, boolean update) {
        if (getPlayerKalaIndex(number).equals(pit))
            throw new IllegalArgumentException("You can not peak any stone from your house!");
        Integer nextPlayerId = (number==0?1:0);  //by default next player is opponent player!
        int numberOfStonesInThePit = board.getPits().get(pit);
        if (numberOfStonesInThePit == 0)
            throw new IllegalArgumentException("in this pit=" + pit + " there is no stones to move!");
        board.clearPit(pit);
        int pitCounter=Integer.valueOf(pit);
        for (; numberOfStonesInThePit > 0; numberOfStonesInThePit--) {
            pitCounter++; //go ahead with subsequet pits couter clockwise
            if (String.valueOf(pitCounter).equals(getOtherPlayerKalaIndexToAvoid(number)))
                pitCounter++; // don't put stone in other players Kala,avoid it!
            if (pitCounter > board.getPits().size())
                pitCounter = pitCounter - board.getPits().size();
            log.info("adding stone into pit number "+pitCounter);
            board.addStoneToPit(String.valueOf(pitCounter));
        }
        if (isPlayeMoveLeadToPlayerKala(number, pit))
            nextPlayerId = number;
        else if (board.getPits().get(pit) == 1) {
            board.addStoneToPit(getPlayerKalaIndex(number));
            board.clearPit(pit);
            if (board.getPits().get(getOppositPit(pit)) > 0) {
                board.addStonesToPit(getPlayerKalaIndex(number), board.getPits().get(getOppositPit(pit)));
                board.clearPit(getOppositPit(pit));
            }
        }
        return nextPlayerId;
    }

    private boolean isPlayeMoveLeadToPlayerKala(int player, String pit) {
        return (pit.equals(getPlayerKalaIndex(player)));

    }

    /**
     * Is the specified cup index the player's end cup
     *
     * @param player The player (0 or 1)
     * @param cup    The cup board index ( 0 to 13)
     * @return
     */
    private String getPlayerKalaIndex(int player) {
        return String.valueOf ((player * 7) + 7); // e.g. 13 or 6
    }

    /**
     * The index of the other player's end cup
     *
     * @param player The current player (0 or 1)
     * @return The other players end cup index (on the board)
     */
    private String getOtherPlayerKalaIndexToAvoid(int player) {
        if (player == 0) // other == 1
        {
            return "14";
        } else
        // other == 0
        {
            return "7";
        }
    }

    /**
     * -------------->------------------ | | 0 | 1 | 2 | 3 | 4 | 5 | | Player 1
     * |13 |-----------------------| 6 | | |12 |11 |10 | 9 | 8 | 7 | | Player 2
     * -------------- <------------------
     * <p>
     * cup opposite 3 9 0 12 5 7 11 1
     *
     * @param cup
     * @return
     */
    private String getOppositPit(String pit) {
        int pitIndex=Integer.valueOf(pit);
        if (number==0)
            return String.valueOf(pitIndex+7);
        else
           return String.valueOf(pitIndex-7);

    }



    public  boolean checkChoosenPitValidForMove(Board board,int choosenPit){
        List<Integer> AvailablePitsForMove =board.getAvailablePitsForMove(number);
        return AvailablePitsForMove.stream().filter(moveAbalePit->choosenPit==choosenPit).findFirst().isPresent();
    }

}
