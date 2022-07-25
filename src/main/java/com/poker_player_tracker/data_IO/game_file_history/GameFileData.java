package com.poker_player_tracker.data_IO.game_file_history;

import com.poker_player_tracker.data_IO.player_data.PlayerData;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class to hold processed file game data.
 */
public class GameFileData implements Serializable {
    private final int gameID;
    private final int startingHand;
    private final int endingHand;
    private transient final HashMap<String, PlayerData> gameMap = new HashMap<>();

    public GameFileData(int gameID, int startingHand, int endingHand, HashMap<String, PlayerData> gameMap) {
        this.gameID = gameID;
        this.startingHand = startingHand;
        this.endingHand = endingHand;
        this.gameMap.putAll(gameMap);
    }

    public int getGameID() {
        return gameID;
    }

    public HashMap<String, PlayerData> getGameMap() {
        return gameMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameFileData that = (GameFileData) o;

        if (gameID != that.gameID) return false;
        return startingHand == that.startingHand;
    }

    @Override
    public int hashCode() {
        int result = gameID;
        result = 31 * result + startingHand;
        return result;
    }


    @Override
    public String toString() {
        return "GameFileData{" +
                "gameID=" + gameID +
                ", startingHand=" + startingHand +
                ", endingHand=" + endingHand +
                '}';
    }
}
