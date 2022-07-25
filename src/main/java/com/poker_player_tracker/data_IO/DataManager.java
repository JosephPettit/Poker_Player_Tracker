package com.poker_player_tracker.data_IO;

import com.poker_player_tracker.data_IO.game_file_history.FolderPath;
import com.poker_player_tracker.data_IO.game_file_history.GameFileData;
import com.poker_player_tracker.data_IO.game_file_history.GameFileLogManager;
import com.poker_player_tracker.data_IO.game_file_history.PlayerLocationLogManager;
import com.poker_player_tracker.data_IO.game_processor.GameProcessor;
import com.poker_player_tracker.data_IO.player_data.PlayerData;
import com.poker_player_tracker.data_IO.player_data.PlayerDisplayData;
import com.poker_player_tracker.data_IO.player_history.PlayerDataHistory;
import com.poker_player_tracker.data_IO.player_history.PlayerDisplayHistory;
import com.poker_player_tracker.data_IO.player_history.PlayerLocationData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class DataManager {
    private PlayerLocationLogManager locationLog;
    private GameFileLogManager gameLog;
    private PlayerDataHistory playerHistory;
    private PlayerDisplayHistory playerDisplayHistory;
    private GameFileData gameData;

    /**
     * Loads {@link FolderPath#PLAYER_LOCATION_LOG} from {@link PlayerLocationLogManager}.
     *
     * @return Returns a shallow copy {@code HashMap<String, PlayerLocationData>} from {@code PlayerLocationManager}.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public HashMap<String, PlayerLocationData> getPlayerHistory() throws IOException, ClassNotFoundException {
        locationLog = new PlayerLocationLogManager();
        return locationLog.getPlayerHistory();
    }

    /**
     * Loads {@link PlayerDisplayData} to be displayed from {@link FolderPath#DISPLAY_PLAYER_DATA} .dat file.
     *
     * @param playerLocation non-null
     * @return Returns {@code PlayerDisplayDisplay} form .dat file.
     * @throws IOException
     */
    public PlayerDisplayData getDisplayPlayer(PlayerLocationData playerLocation) throws IOException {
        playerDisplayHistory = new PlayerDisplayHistory();
        return playerDisplayHistory.readPlayerData(playerLocation);
    }

    /**
     * Logic for total handling of game files.
     * <p>
     * <ol>
     * <li>Sends {@link GameFileData} to {@link GameProcessor} for processing</li>
     * <li>Processed {@code GameFileData} is checked for duplicates against {@link FolderPath#GAME_FILE_LOG}</li>
     * <li>{@code GameFileData HashMap} is saved to {@link FolderPath#GAME_DATA_DIRECTORY} in .ser format, with {@link GameFileData#getGameID()} as the files name.</li>
     * <li>{@code GameFileData} is added to {@code FolderPath.GAME_FILE_LOG} </li>
     * <li>Merge player data from {@code GameFileData HashMap} with {@link FolderPath#RAW_PLAYER_DATA} and {@link FolderPath#DISPLAY_PLAYER_DATA}</li>
     * <li>If new player, {@link PlayerLocationData} is created and recorded in {@link FolderPath#PLAYER_LOCATION_LOG}.</li>
     * </ol>
     *
     * @param file Game file input from user.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void processGameFile(File file) throws IOException, ClassNotFoundException {
        gameData = new GameProcessor().processFile(file);
        gameLog = new GameFileLogManager();
        if (!gameLog.duplicateLocated(gameData)) {
            gameLog.addToFileHistory(gameData);
            gameLog.createGameHistoryFile(gameData);
            gameLog.writeFile();
            mergePlayersWithLocLog(gameData);
        }
        // TODO: add custom exception for null file;

    }

    private void mergePlayersWithLocLog(GameFileData gameData) throws IOException, ClassNotFoundException {
        playerHistory = new PlayerDataHistory();
        playerDisplayHistory = new PlayerDisplayHistory();
        locationLog = new PlayerLocationLogManager();
        HashMap<String, PlayerLocationData> locationMap = locationLog.getPlayerHistory();

        for (PlayerData player : gameData.getGameMap().values()) {
            PlayerLocationData playerLocation;
            // If player is already in history. Load player from history, then merge with current player.
            // After merge is completed, add player back to history.
            if (locationMap.containsKey(player.getUserName().toLowerCase())) {
                playerLocation = locationMap.get(player.getUserName().toLowerCase());
                PlayerData mergePlayer = playerHistory.readPlayerData(playerLocation);
                mergePlayer.mergeGameData(player);
                playerHistory.writePlayerData(mergePlayer, playerLocation);
                playerDisplayHistory.writePlayerData(new PlayerDisplayData(mergePlayer), playerLocation);
            } else {
                playerLocation = playerHistory.writePlayerData(player);
                playerLocation.mergePlayer(playerDisplayHistory.writePlayerData(new PlayerDisplayData(player)));
                locationMap.put(player.getUserName().toLowerCase(), playerLocation);
            }
        }
        locationLog.writeFile();
    }

}

