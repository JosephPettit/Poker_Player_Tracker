package com.poker_player_tracker.data_IO.player_data;

import com.poker_player_tracker.data_IO.player_history.PlayerDataHistory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PlayerData implements Serializable {

    private final String userName;
    private transient boolean isBigBlind;
    private transient boolean isSmallBlind;
    private transient boolean isOnButton;
    private transient boolean isAll_in;
    private transient boolean isUncalled;
    private int handsPlayed, bet, raise, reRaise, threeBetPlus, buttonRaise, bigBlindRaise, smallBlindRaise,
            all_inTotal, all_inWins, preFlopAll_in, postFlopAll_in, preFlopThreeBetPlus, preFlopReRaise, preFlopRaise,
            preFlopBigBlindRaise, preFlopSmallBlindRaise, preFlopButtonRaise, wins, uncalledWins;

    private LinkedList<Integer> variableList = new LinkedList<>();

    public PlayerData(String userName) {
        this.userName = userName;
        this.isBigBlind = false;
        this.isSmallBlind = false;
        this.isOnButton = false;
        this.isAll_in = false;
        this.isUncalled = false;
    }

    /**
     * Returns a {@code List<Integer>} for use with {@link PlayerDataHistory PlayerDataHistory}
     *
     * @return {@code List<Integer>} of all instances variables.
     */
    public List<Integer> getVariables() {

        return Arrays.asList(handsPlayed, bet, raise, reRaise, threeBetPlus, buttonRaise, bigBlindRaise, smallBlindRaise,
                all_inTotal, all_inWins, preFlopAll_in, postFlopAll_in, preFlopThreeBetPlus, preFlopReRaise, preFlopRaise,
                preFlopBigBlindRaise, preFlopSmallBlindRaise, preFlopButtonRaise, wins, uncalledWins);
    }

    public LinkedList<Integer> getVariableList() {
        if(variableList == null)
            return new LinkedList<>();
        return variableList;
    }

    public void applyVariableList() {
        handsPlayed = variableList.remove();
        bet = variableList.remove();
        raise = variableList.remove();
        reRaise = variableList.remove();
        threeBetPlus = variableList.remove();
        buttonRaise = variableList.remove();
        bigBlindRaise = variableList.remove();
        smallBlindRaise = variableList.remove();
        all_inTotal = variableList.remove();
        all_inWins = variableList.remove();
        preFlopAll_in = variableList.remove();
        postFlopAll_in = variableList.remove();
        preFlopThreeBetPlus = variableList.remove();
        preFlopReRaise = variableList.remove();
        preFlopRaise = variableList.remove();
        preFlopBigBlindRaise = variableList.remove();
        preFlopSmallBlindRaise = variableList.remove();
        preFlopButtonRaise = variableList.remove();
        wins = variableList.remove();
        uncalledWins = variableList.remove();

    }

    /**
     * Merge this instance with another PlayerData object
     *
     * @param inGameData {@code PlayerData} to be merged into object
     */
    public void mergeGameData(PlayerData inGameData) {
        this.handsPlayed += inGameData.handsPlayed;
        this.bet += inGameData.bet;
        this.raise += inGameData.raise;
        this.reRaise += inGameData.reRaise;
        this.threeBetPlus += inGameData.threeBetPlus;
        this.buttonRaise += inGameData.buttonRaise;
        this.bigBlindRaise += inGameData.bigBlindRaise;
        this.smallBlindRaise += inGameData.smallBlindRaise;
        this.postFlopAll_in += inGameData.postFlopAll_in;
        this.all_inWins += inGameData.all_inWins;
        this.preFlopAll_in += inGameData.preFlopAll_in;
        this.preFlopThreeBetPlus += inGameData.preFlopThreeBetPlus;
        this.preFlopReRaise += inGameData.preFlopReRaise;
        this.preFlopRaise += inGameData.preFlopRaise;
        this.preFlopBigBlindRaise += inGameData.preFlopBigBlindRaise;
        this.preFlopSmallBlindRaise += inGameData.preFlopSmallBlindRaise;
        this.preFlopButtonRaise += inGameData.preFlopButtonRaise;
        this.wins += inGameData.wins;
        this.uncalledWins += inGameData.uncalledWins;
        this.all_inTotal = this.postFlopAll_in + this.preFlopAll_in;

    }

    /**
     * Boolean if the player is in Big or Small blind, and Dealer position.
     *
     * @return Boolean if player is in an action seat
     */
    public boolean isPositionPlayer() {
        return (isBigBlind || isSmallBlind || isOnButton);
    }

    /**
     * Increments player object raise for all action positions,
     *
     * @param preFlop Boolean True if game is pre-flop
     */
    public void incrementRaise(boolean preFlop) {
        if (preFlop || isPositionPlayer()) {
            if (preFlop) {
                if (isBigBlind) preFlopBigBlindRaise++;
                else if (isSmallBlind) preFlopSmallBlindRaise++;
                else if (isOnButton) preFlopButtonRaise++;
                else preFlopRaise++;
            } else {
                if (isBigBlind) bigBlindRaise++;
                else if (isSmallBlind) smallBlindRaise++;
                else buttonRaise++;
            }
        } else raise++;
    }

    /**
     * Increments player object re-raise for all action positions.
     *
     * @param preFlop Boolean True if game is pre-flop
     */
    public void incrementReRaise(boolean preFlop) {
        if (preFlop || isPositionPlayer()) {
            if (preFlop) {
                if (isBigBlind) preFlopBigBlindRaise++;
                else if (isSmallBlind) preFlopSmallBlindRaise++;
                else if (isOnButton) preFlopButtonRaise++;
                else preFlopReRaise++;
            } else {
                if (isBigBlind) bigBlindRaise++;
                else if (isSmallBlind) smallBlindRaise++;
                else buttonRaise++;
            }
        } else reRaise++;
    }

    /**
     * Increments player object threeBet for all action positions.
     *
     * @param preFlop Boolean True if game is pre-flop
     */
    public void incrementThreeBetPlus(boolean preFlop) {
        if (preFlop || isPositionPlayer()) {
            if (preFlop) {
                if (isBigBlind) preFlopBigBlindRaise++;
                else if (isSmallBlind) preFlopSmallBlindRaise++;
                else if (isOnButton) preFlopButtonRaise++;
                else preFlopThreeBetPlus++;
            } else {
                if (isBigBlind) bigBlindRaise++;
                else if (isSmallBlind) smallBlindRaise++;
                else buttonRaise++;
            }
        } else threeBetPlus++;
    }

    /** Increments player wins, including all-in wins */
    public void incrementWins() {
        if (isAll_in)
            all_inWins++;
        if (isUncalled)
            uncalledWins++;
        wins++;
    }

    /**
     * Increments all in game total, includes pre-flop all in's
     *
     * @param preFlop Boolean True if game is pre-flop
     */
    public void incrementAll_inGameTotal(boolean preFlop) {
        if (preFlop) preFlopAll_in++;
        else postFlopAll_in++;

        all_inTotal++;
    }

    /** Increments HandsPlayed by 1 */
    public void incrementHandsPlayed() {
        handsPlayed++;
    }

    /** Increments bet by 1 */
    public void incrementBet() {
        bet++;
    }

    /** Increments handsPlayer and Resets bigBlind, smallBlind, onButton, and all_in flags */
    public void endHand() {
        handsPlayed++;
        isBigBlind = isSmallBlind = isOnButton = isAll_in = isUncalled = false;
    }

    public String getUserName() {
        return userName;
    }

    public void setBigBlind(boolean bigBlind) {
        isBigBlind = bigBlind;
    }

    public void setSmallBlind(boolean smallBlind) {
        isSmallBlind = smallBlind;
    }

    public void setOnButton(boolean onButton) {
        isOnButton = onButton;
    }

    public void setAll_in(boolean all_in) {
        isAll_in = all_in;
    }

    public void setUncalled(boolean uncalled) {
        isUncalled = uncalled;
    }

    public int getHandsPlayed() {
        return handsPlayed;
    }

    public int getBet() {
        return bet;
    }

    public int getRaise() {
        return raise;
    }

    public int getReRaise() {
        return reRaise;
    }

    public int getThreeBetPlus() {
        return threeBetPlus;
    }

    public int getButtonRaise() {
        return buttonRaise;
    }

    public int getBigBlindRaise() {
        return bigBlindRaise;
    }

    public int getSmallBlindRaise() {
        return smallBlindRaise;
    }

    public int getPostFlopAll_in() {
        return postFlopAll_in;
    }

    public int getAll_inWins() {
        return all_inWins;
    }

    public int getPreFlopAll_in() {
        return preFlopAll_in;
    }

    public int getPreFlopThreeBetPlus() {
        return preFlopThreeBetPlus;
    }

    public int getPreFlopReRaise() {
        return preFlopReRaise;
    }

    public int getPreFlopRaise() {
        return preFlopRaise;
    }

    public int getPreFlopBigBlindRaise() {
        return preFlopBigBlindRaise;
    }

    public int getPreFlopSmallBlindRaise() {
        return preFlopSmallBlindRaise;
    }

    public int getPreFlopButtonRaise() {
        return preFlopButtonRaise;
    }

    public int getWins() {
        return wins;
    }

    public int getUncalledWins() {
        return uncalledWins;
    }

    public int getAll_inTotal() {
        return all_inTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerData that = (PlayerData) o;

        return userName.equals(that.userName);
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }

    @Override
    public String toString() {
        return "PlayerData{" +
                "userName='" + userName + '\'' +
                ", handsPlayed=" + handsPlayed +
                ", bet=" + bet +
                ", raise=" + raise +
                ", reRaise=" + reRaise +
                ", threeBetPlus=" + threeBetPlus +
                ", buttonRaise=" + buttonRaise +
                ", bigBlindRaise=" + bigBlindRaise +
                ", smallBlindRaise=" + smallBlindRaise +
                ", all_inGameTotal=" + postFlopAll_in +
                ", all_inWins=" + all_inWins +
                ", preFlopAll_in=" + preFlopAll_in +
                ", preFlopThreeBetPlus=" + preFlopThreeBetPlus +
                ", preFlopReRaise=" + preFlopReRaise +
                ", preFlopRaise=" + preFlopRaise +
                ", preFlopBigBlindRaise=" + preFlopBigBlindRaise +
                ", preFlopSmallBlindRaise=" + preFlopSmallBlindRaise +
                ", preFlopButtonRaise=" + preFlopButtonRaise +
                ", wins=" + wins +
                ", uncalledWins=" + uncalledWins +
                '}';
    }

}
