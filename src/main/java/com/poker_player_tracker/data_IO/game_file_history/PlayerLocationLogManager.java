package com.poker_player_tracker.data_IO.game_file_history;

import com.poker_player_tracker.data_IO.RequiredFileNotFoundException;
import com.poker_player_tracker.data_IO.game_file_history.FolderPath;
import com.poker_player_tracker.data_IO.player_history.PlayerLocationData;

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
     * @throws RequiredFileNotFoundException Throws if unable to read,write or create the file.
     */
    public void writeFile() throws RequiredFileNotFoundException {
        File outputFile = new File(FolderPath.PLAYER_LOCATION_LOG.getFolderPath());
        if(!outputFile.exists())
            outputFile.mkdirs();
        try (FileOutputStream fos = new FileOutputStream(outputFile); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(playerHistory);
        }
        catch(IOException e){
            throw new RequiredFileNotFoundException(FolderPath.PLAYER_LOCATION_LOG.getFolderPath(), e);
        }

    }

    private void readFile() throws IOException, ClassNotFoundException {
        File file = new File(FolderPath.PLAYER_LOCATION_LOG.getFolderPath());
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
                if (!file.exists()) {
                    throw new RequiredFileNotFoundException("PlayerLocationLog.ser could not be located or created");
                }
            }catch(IOException e){
                throw new RequiredFileNotFoundException(FolderPath.PLAYER_LOCATION_LOG.getFolderPath(), e);
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
