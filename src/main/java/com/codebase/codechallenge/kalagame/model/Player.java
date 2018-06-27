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

    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * this is the main method controling whole logic for play
     *
     * @param selectedPit selected pit to start playing
     * @param borad
     * @return next player turn
     */
    public int doMove(String selectedPit, Board board) {
        int playerKalaIndex=getPlayerKalaIndex(number); //keep this for the end of stone movement

        /*rule: player can not choose any pit from his/her kala(house) */
        if (String.valueOf(playerKalaIndex).equals(selectedPit))
            throw new IllegalArgumentException("You can not peak any stone from your house!");

        int nextPlayerId = (number==0?1:0);  //by default next player is opponent player!

        int numberOfStonesInTheSelectedPit = board.getPits().get(selectedPit);

        if (numberOfStonesInTheSelectedPit == 0)
            throw new IllegalArgumentException("in this pit=" + numberOfStonesInTheSelectedPit + " there is no stones to move! Please choose another one.");

        board.clearPit(selectedPit);

        int selectedPitIndex=Integer.valueOf(selectedPit);
        //start moving
        for (; numberOfStonesInTheSelectedPit > 0; numberOfStonesInTheSelectedPit--) {
            selectedPitIndex++; //go ahead with subsequent pits counter clockwise

            // rule : don't put stone in other players Kala,avoid it!
            if (selectedPitIndex==getKalaIndexForOpponent(number)) {
                log.info("player "+number+" kala "+selectedPitIndex +" avoided");
                selectedPitIndex++; // don't put stone in other players Kala,avoid it!
            }

            //if you reach stone number 13 start agian with number 1
            if (selectedPitIndex > board.getPits().size())
                selectedPitIndex = selectedPitIndex - board.getPits().size();

            //it is time to add one stone in the found pit
            log.info("adding stone into pit number "+selectedPitIndex);
            board.addStoneToPit(String.valueOf(selectedPitIndex));
        }
        if (isPlayerPutLastStoneInHisKala(number, selectedPitIndex))
            //rule : in this case current player will get free turn next time
            nextPlayerId = number;

        else if (board.getPits().get(String.valueOf(selectedPitIndex)) == 1) {

            //rule : if player put last stone in an empty pit , will get a big price as following
            //first put this stone in your kala
            board.addStoneToPit(String.valueOf(playerKalaIndex));
            board.clearPit(String.valueOf(selectedPitIndex));

            //second if in the opponents pit is there any stone or not ? all for you
            String oppoisitPitIndex=getOppositPitIndex(String.valueOf(selectedPitIndex));
            int numberOfStonesInOppositePit=board.getPits().get(oppoisitPitIndex);
            if (numberOfStonesInOppositePit>0) {
                //get whatever stone in oppoiste index for you
                board.addStonesToPit(String.valueOf(playerKalaIndex), numberOfStonesInOppositePit);
                board.clearPit(oppoisitPitIndex);
            }
        }
        return nextPlayerId;
    }

    private boolean isPlayerPutLastStoneInHisKala(int player, int pit) {
        return pit==getPlayerKalaIndex(player);

    }

    /**
     * specifies house or kala for each player
     *
     * @param player The player (0 or 1)
     * @return
     */
    private int getPlayerKalaIndex(int player) {
        return  (player * 7) + 7; // either 7 or 14
    }

    /**
     * The index of the other player's end cup
     *
     * @param player The current player (0 or 1)
     * @return The other players end cup index (on the board)
     */
    private int getKalaIndexForOpponent(int player) {
        if (player == 0)
            return 14;
         else
            return 7;
    }

    /*
       get opposite pit for the selected one
     */
    private String getOppositPitIndex(String pit) {
        int pitIndex=Integer.valueOf(pit);
        if (pitIndex>7)
            return String.valueOf(pitIndex-7);
        else
           return String.valueOf(pitIndex+7);

    }

    public  boolean checkChoosenPitValidForMove(Board board,String choosenPit){
        List<String> AvailablePitsForMove =board.getAvailablePitsForMove(number);
        return AvailablePitsForMove.stream().filter(moveAbalePit->moveAbalePit.equals(choosenPit)).findFirst().isPresent();
    }

}
