package com.poker_player_tracker.data_IO.player_history;

import com.poker_player_tracker.data_IO.game_file_history.FolderPath;

import java.io.*;
import java.util.HashMap;

public class PlayerLocationLogManager {

    private HashMap<String, PlayerLocationData> playerHistory;

    /**
     * Loads player location history from {@link FolderPath#PLAYER_LOCATION_LOG}
     */
    public PlayerLocationLogManager() throws IOException, ClassNotFoundException {
        this.playerHistory = null;
        readFile();
    }

    /**
     * Access to shallow copy of {@code HashMap} from {@link FolderPath#PLAYER_LOCATION_LOG}
     *
     * @return Returns a shallow copy of {@code HashMap <String, PlayerLocationData>}
     */
    public HashMap<String, PlayerLocationData> getPlayerHistory() {
        return playerHistory;
    }

    /**
     * Writes {@code HashMap <String, PlayerLocationData>} to {@link FolderPath#PLAYER_LOCATION_LOG}
     * <p>
     * If file does not exist, it will be created.
     *
     * @throws IOException SecurityException if access to write is denied by a security manager.
     */
    public void writeFile() throws IOException {
        File outputFile = new File(FolderPath.PLAYER_LOCATION_LOG.getFolderPath());
        if(!outputFile.exists())
            outputFile.mkdirs();
        try (FileOutputStream fos = new FileOutputStream(outputFile); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(playerHistory);
        }
    }

    private void readFile() throws IOException, ClassNotFoundException {
        File file = new File(FolderPath.PLAYER_LOCATION_LOG.getFolderPath());
        // TODO: Clean up and remove if statement - see if there is a better way to handle the file not being located.
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            if (!file.exists()) {
                throw new IOException("PlayerLocationLog.ser could not be located or created");
            }
        }
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            if ((playerHistory = (HashMap<String, PlayerLocationData>) ois.readObject()) == null) {
                playerHistory = new HashMap<>();
            }
        } catch (IOException e) {
            playerHistory = new HashMap<>();
        }
    }


}
