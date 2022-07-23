package com.poker_player_tracker.data_IO.game_file_history;

public enum FolderPath {
    GAME_FILE_LOG("src/main/resources/com/player_history/bin/GameFileHistory/GameFileLog.ser"),
    RAW_PLAYER_DATA("src/main/resources/com/player_history/bin/PlayerData/PlayerDataHistory.dat"),
    DISPLAY_PLAYER_DATA("src/main/resources/com/player_history/bin/PlayerData/PlayerDataDisplayHistory.dat"),
    GAME_DATA_DIRECTORY("src/main/resources/com/player_history/bin/GameFileHistory/GameFileMapsHistory/"),
    PLAYER_LOCATION_LOG("src/main/resources/com/player_history/bin/PlayerData/PlayerLocationLog.ser");

    private final String folderPath;

    FolderPath(String str) {
        this.folderPath = str;
    }

    public String getFolderPath() {
        return folderPath;
    }

}
