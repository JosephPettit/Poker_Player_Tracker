package com.poker_player_tracker.data_IO.player_data;


import com.poker_player_tracker.data_IO.player_history.PlayerDisplayHistory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PlayerDisplayData implements Serializable {

    private final String userName;

    private LinkedList<Double> variableList;
    private int handsPlayed;

    private double bet, raise, reRaise, threeBet, buttonRaise, bigBlindRaise, smallBlindRaise, postFlopAll_in,
            all_inGame, all_inWin, all_inTotal, preFlopAll_in, preFlopThreeBet, preFlopReRaise, preFlopRaise,
            preFlopBigBlindRaise, preFlopSmallBlindRaise, preFlopButtonRaise, wins, uncalledWins;

    /**
     * Constructor assigns all values from {@link PlayerData} into object with logic to convert variables to type {@code Double}.
     * <p>
     * All values will be converted to a double representation of their respective ratios to {@code handsPlayed} as percentages
     * with the exception of {@code handsPlayed}, {@code wins}, {@code uncalledWins}, {@code all_inTotal}, and {@code all_inWins}.
     * Excluded values will remain integers for display.
     *
     * @param playerData to be converted to {@code PlayerDisplayData}
     */
    public PlayerDisplayData(PlayerData playerData) {
        this.userName = playerData.getUserName();
        this.handsPlayed = playerData.getHandsPlayed();
        this.wins = playerData.getWins();
        this.uncalledWins = playerData.getUncalledWins();
        this.all_inTotal = playerData.getAll_inTotal();
        this.all_inWin = playerData.getAll_inWins();
        this.bet = calculateFrequencies(playerData.getBet());
        this.raise = calculateFrequencies(playerData.getRaise());
        this.reRaise = calculateFrequencies(playerData.getReRaise());
        this.threeBet = calculateFrequencies(playerData.getThreeBetPlus());
        this.buttonRaise = calculateFrequencies(playerData.getButtonRaise());
        this.bigBlindRaise = calculateFrequencies(playerData.getBigBlindRaise());
        this.smallBlindRaise = calculateFrequencies(playerData.getSmallBlindRaise());
        this.postFlopAll_in = calculateFrequencies(playerData.getPostFlopAll_in());
        this.all_inGame = calculateFrequencies(playerData.getAll_inTotal());
        this.preFlopAll_in = calculateFrequencies(playerData.getPreFlopAll_in());
        this.preFlopThreeBet = calculateFrequencies(playerData.getPreFlopThreeBetPlus());
        this.preFlopReRaise = calculateFrequencies(playerData.getPreFlopReRaise());
        this.preFlopRaise = calculateFrequencies(playerData.getPreFlopRaise());
        this.preFlopBigBlindRaise = calculateFrequencies(playerData.getPreFlopBigBlindRaise());
        this.preFlopSmallBlindRaise = calculateFrequencies(playerData.getPreFlopSmallBlindRaise());
        this.preFlopButtonRaise = calculateFrequencies(playerData.getPreFlopButtonRaise());
    }

    /**
     * Constructor to create instance of {@code PlayerDisplayData}. For use with {@link PlayerDisplayHistory}
     *
     * @param userName Will be used as Key in {@code HashMap}
     */
    public PlayerDisplayData(String userName) {
        this.userName = userName;
        // TODO: should be removed after 'better' solution for loading all variables is found.
        LinkedList<Double> variableList = new LinkedList<>();
    }

    public LinkedList<Double> getVariableList() {
        // TODO: should be removed after 'better' solution for loading all variables is found.
        if (variableList == null)
            return new LinkedList<>();
        return variableList;
    }

    /**
     * Returns a {@code List<Double>} for use with {@link PlayerDisplayHistory PlayerDisplayHistory}
     *
     * @return {@code List<Double>} of all instance variables.
     */
    public List<Double> getVariables() {

        return Arrays.asList((double) handsPlayed, bet, raise, reRaise, threeBet, buttonRaise, bigBlindRaise,
                smallBlindRaise, postFlopAll_in, all_inGame, all_inWin, all_inTotal, preFlopAll_in, preFlopThreeBet,
                preFlopReRaise, preFlopRaise, preFlopBigBlindRaise, preFlopSmallBlindRaise, preFlopButtonRaise, wins,
                uncalledWins);
    }

    //TODO should be removed after 'better' solution is identified.
    public void applyVariableList(LinkedList<Double> variableList) {
        handsPlayed = variableList.remove().intValue();
        bet = variableList.remove();
        raise = variableList.remove();
        reRaise = variableList.remove();
        threeBet = variableList.remove();
        buttonRaise = variableList.remove();
        bigBlindRaise = variableList.remove();
        smallBlindRaise = variableList.remove();
        postFlopAll_in = variableList.remove();
        all_inGame = variableList.remove();
        all_inWin = variableList.remove();
        all_inTotal = variableList.remove();
        preFlopAll_in = variableList.remove();
        preFlopThreeBet = variableList.remove();
        preFlopReRaise = variableList.remove();
        preFlopRaise = variableList.remove();
        preFlopBigBlindRaise = variableList.remove();
        preFlopSmallBlindRaise = variableList.remove();
        preFlopButtonRaise = variableList.remove();
        wins = variableList.remove();
        uncalledWins = variableList.remove();

    }

    public String getUserName() {
        return userName;
    }

    public int getHandsPlayed() {
        return handsPlayed;
    }

    public double getBet() {
        return bet;
    }

    public double getRaise() {
        return raise;
    }

    public double getReRaise() {
        return reRaise;
    }

    public double getThreeBet() {
        return threeBet;
    }

    public double getButtonRaise() {
        return buttonRaise;
    }

    public double getBigBlindRaise() {
        return bigBlindRaise;
    }

    public double getSmallBlindRaise() {
        return smallBlindRaise;
    }

    public double getPostFlopAll_in() {
        return postFlopAll_in;
    }

    public double getAll_inGame() {
        return all_inGame;
    }

    public double getAll_inWin() {
        return all_inWin;
    }

    public double getAll_inTotal() {
        return all_inTotal;
    }

    public double getPreFlopAll_in() {
        return preFlopAll_in;
    }

    public double getPreFlopThreeBet() {
        return preFlopThreeBet;
    }

    public double getPreFlopReRaise() {
        return preFlopReRaise;
    }

    public double getPreFlopRaise() {
        return preFlopRaise;
    }

    public double getPreFlopBigBlindRaise() {
        return preFlopBigBlindRaise;
    }

    public double getPreFlopSmallBlindRaise() {
        return preFlopSmallBlindRaise;
    }

    public double getPreFlopButtonRaise() {
        return preFlopButtonRaise;
    }

    public double getWins() {
        return wins;
    }

    public double getUncalledWins() {
        return uncalledWins;
    }

    private double calculateFrequencies(double inputNumber) {
        if (inputNumber == 0 || handsPlayed == 0) {
            return 0;
        }
        // casts inputNumber / handsPlayed * 10,000 to int for removing trailing decimals.
        // results / 100.0 to format output to 2 decimal places.
        return ((int) (inputNumber / handsPlayed * 10000) / 100.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerDisplayData that = (PlayerDisplayData) o;

        if (handsPlayed != that.handsPlayed) return false;
        if (Double.compare(that.bet, bet) != 0) return false;
        if (Double.compare(that.raise, raise) != 0) return false;
        if (Double.compare(that.reRaise, reRaise) != 0) return false;
        if (Double.compare(that.threeBet, threeBet) != 0) return false;
        if (Double.compare(that.buttonRaise, buttonRaise) != 0) return false;
        if (Double.compare(that.bigBlindRaise, bigBlindRaise) != 0) return false;
        if (Double.compare(that.smallBlindRaise, smallBlindRaise) != 0) return false;
        if (Double.compare(that.all_inGame, all_inGame) != 0) return false;
        if (Double.compare(that.all_inWin, all_inWin) != 0) return false;
        if (Double.compare(that.preFlopAll_in, preFlopAll_in) != 0) return false;
        if (Double.compare(that.preFlopThreeBet, preFlopThreeBet) != 0) return false;
        if (Double.compare(that.preFlopReRaise, preFlopReRaise) != 0) return false;
        if (Double.compare(that.preFlopRaise, preFlopRaise) != 0) return false;
        if (Double.compare(that.preFlopBigBlindRaise, preFlopBigBlindRaise) != 0) return false;
        if (Double.compare(that.preFlopSmallBlindRaise, preFlopSmallBlindRaise) != 0) return false;
        if (Double.compare(that.preFlopButtonRaise, preFlopButtonRaise) != 0) return false;
        if (Double.compare(that.wins, wins) != 0) return false;
        if (Double.compare(that.uncalledWins, uncalledWins) != 0) return false;
        return userName.equals(that.userName);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = userName.hashCode();
        result = 31 * result + handsPlayed;
        temp = Double.doubleToLongBits(bet);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(raise);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(reRaise);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(threeBet);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(buttonRaise);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(bigBlindRaise);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(smallBlindRaise);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(all_inGame);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(all_inWin);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(preFlopAll_in);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(preFlopThreeBet);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(preFlopReRaise);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(preFlopRaise);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(preFlopBigBlindRaise);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(preFlopSmallBlindRaise);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(preFlopButtonRaise);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(wins);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(uncalledWins);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


}