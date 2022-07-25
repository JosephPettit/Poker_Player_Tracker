package com.poker_player_tracker.data_IO.game_file_history;

import com.poker_player_tracker.data_IO.custom_exceptions.RequiredFileAccessDeniedException;
import com.poker_player_tracker.data_IO.custom_exceptions.RequiredFileNotFoundException;

import java.io.*;
import java.util.HashSet;

public class GameFileLogManager {

    private HashSet<GameFileData> gameFileHistory;

    /**
     * Reads {@link FolderPath#GAME_FILE_LOG} upon creation. If file cannot be found / created. New file will be created.
     *
     */
    public GameFileLogManager() {
        this.gameFileHistory = null;
        readFile();
    }

    /**
     * Checks {@link FolderPath#GAME_FILE_LOG} if {@link GameFileData} has the same Game ID and starting hand int.
     *
     * @param gameData Processed gameData to be checked against history.
     * @return True if match is found.
     */
    public boolean duplicateLocated(GameFileData gameData) {
        if (gameFileHistory == null) return false;
        return gameFileHistory.contains(gameData);
    }

    /**
     * Adds {@code GameFileData} to {@link FolderPath#GAME_FILE_LOG}.
     *
     * @param gameData Processed {@code gameData} to be added to history file.
     */
    public void addToFileHistory(GameFileData gameData) {
        if (gameFileHistory == null) gameFileHistory = new HashSet<>();
        gameFileHistory.add(gameData);
    }

    /**
     * Writes {@code HashMap<GameFileData>} {@code gameFileHistory} to {@link FolderPath#GAME_FILE_LOG}.
     *
     * @throws RequiredFileNotFoundException throws exception if {@link FolderPath#GAME_FILE_LOG} is unable to be written too.
     * @throws RequiredFileAccessDeniedException throws exception if access to the file is denied by the System's security manager.
     */
    public void writeFile() throws RequiredFileNotFoundException, RequiredFileAccessDeniedException {
        File outputFile = new File(FolderPath.GAME_FILE_LOG.getFolderPath());
        try (FileOutputStream fos = new FileOutputStream(outputFile); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameFileHistory);
        }
        catch(IOException e){
            throw new RequiredFileNotFoundException(FolderPath.GAME_FILE_LOG.getFolderPath(), e);
        } catch (SecurityException e) {
            throw new RequiredFileAccessDeniedException(FolderPath.PLAYER_LOCATION_LOG.getFolderPath(), e);
        }
    }

    /**
     * Creates a {@code GameFileData} file at {@link FolderPath#GAME_DATA_DIRECTORY} with the name of {@link GameFileData#getGameID()} .ser
     *
     * @param gameData Processed {@code GameFileData} to be written to file.
     * @throws RequiredFileNotFoundException If the file is unable to be located or created by the program.
     * @throws RequiredFileAccessDeniedException throws exception if access to the file is denied by the System's security manager.
     */
    public void createGameHistoryFile(GameFileData gameData) throws RequiredFileNotFoundException, RequiredFileAccessDeniedException {
        File outputFile = new File(FolderPath.GAME_DATA_DIRECTORY.getFolderPath() + gameData.getGameID() + ".ser");
        if (!(new File(FolderPath.GAME_DATA_DIRECTORY.getFolderPath()).exists()))
            new File(FolderPath.GAME_DATA_DIRECTORY.getFolderPath()).mkdirs();
        try (FileOutputStream fos = new FileOutputStream(outputFile); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameData.getGameMap());
        } catch (IOException e) {
            throw new RequiredFileNotFoundException(FolderPath.GAME_DATA_DIRECTORY.getFolderPath(), e);
        } catch (SecurityException e) {
            throw new RequiredFileAccessDeniedException(FolderPath.PLAYER_LOCATION_LOG.getFolderPath(), e);
        }
    }

    // TODO: Needs a method to read in Game History Files. *backup plan in case of failure

    private void readFile() {
        File file = new File(FolderPath.GAME_FILE_LOG.getFolderPath());
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            if ((gameFileHistory = (HashSet<GameFileData>) ois.readObject()) == null) {
                gameFileHistory = new HashSet<>();
            }
        } catch (ClassNotFoundException | IOException e) {
            gameFileHistory = new HashSet<>();
        }
    }
}