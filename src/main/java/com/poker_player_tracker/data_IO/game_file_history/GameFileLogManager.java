package com.poker_player_tracker.data_IO.game_file_history;

import java.io.*;
import java.util.HashSet;

public class GameFileLogManager {

    private HashSet<GameFileData> gameFileHistory;

    /**
     * Reads {@link FolderPath#GAME_FILE_LOG} upon creation. If file cannot be found / created. New file will be created.
     *
     * @throws ClassNotFoundException Throws if {@link FolderPath#GAME_FILE_LOG} doesn't contain a {@code HashSet <GameFileData>}.
     */
    public GameFileLogManager() throws ClassNotFoundException {
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
     * @throws IOException throws exception if {@link FolderPath#GAME_FILE_LOG} is unable to be written too.
     */
    public void writeFile() throws IOException {
        File outputFile = new File(FolderPath.GAME_FILE_LOG.getFolderPath());
//        if (!outputFile.exists())
//            outputFile.mkdir();
        try (FileOutputStream fos = new FileOutputStream(outputFile); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameFileHistory);
        }
    }

    /**
     * Creates a {@code GameFileData} file at {@link FolderPath#GAME_DATA_DIRECTORY} with the name of {@link GameFileData#getGameID()} .ser
     *
     * @param gameData Processed {@code GameFileData} to be written to file.
     * @throws IOException Throws if system prevents the file from being written.
     */
    public void createGameHistoryFile(GameFileData gameData) throws IOException {
        File outputFile = new File(FolderPath.GAME_DATA_DIRECTORY.getFolderPath() + gameData.getGameID() + ".ser");
        if(!(new File(FolderPath.GAME_DATA_DIRECTORY.getFolderPath()).exists()))
            new File(FolderPath.GAME_DATA_DIRECTORY.getFolderPath()).mkdirs();
        try (FileOutputStream fos = new FileOutputStream(outputFile); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameData.getGameMap());
        }

    }

    // TODO: Needs a method to read in Game History Files. *backup plan in case of failure

    private void readFile() throws ClassNotFoundException {
        File file = new File(FolderPath.GAME_FILE_LOG.getFolderPath());

        //TODO: Find 'better' way to handle when the file cannot be opened or read.
//        if (!file.exists()) {
//            file.getParentFile().mkdirs();
//            file.createNewFile();
//
//            if (!file.exists())
//                throw new IOException(FolderPath.GAME_FILE_LOG.getFolderPath() + " could not be located or created");
//        }
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            if ((gameFileHistory = (HashSet<GameFileData>) ois.readObject()) == null) {
                gameFileHistory = new HashSet<>();
            }
        } catch (IOException e) {
            gameFileHistory = new HashSet<>();
        }
    }
}