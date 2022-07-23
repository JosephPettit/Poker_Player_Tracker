package com.poker_player_tracker.data_IO.player_history;


import com.poker_player_tracker.data_IO.game_file_history.FolderPath;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class PlayerHistoryTemplate {
    int totalEntries;
    long fileLength;
    long previousPointer;
    FolderPath path;

    /**
     * Loads Random Access File from {@link FolderPath} specified.
     * <p>
     * If file does not exist, it will be created.
     *
     * @param path {@code FolderPath} enum type.
     * @throws IOException SecurityException if access to read or write is denied by a security manager.
     */
    protected void loadFileData(FolderPath path) throws IOException {
        this.path = path;
        if (!(new File(path.getFolderPath()).exists()))
            new File(path.getFolderPath()).getParentFile().mkdirs();
        try (RandomAccessFile file = new RandomAccessFile(path.getFolderPath(), "rw")) {
            if (file.length() == 0) {
                firstLoad(file);
            }
            totalEntries = file.readInt();
            fileLength = file.length();
        }
    }

    private void firstLoad(RandomAccessFile file) throws IOException {
        file.writeInt(0);
        file.writeInt(0);
        file.seek(0);
    }

}
