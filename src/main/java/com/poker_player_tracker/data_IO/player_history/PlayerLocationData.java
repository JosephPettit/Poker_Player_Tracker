package com.poker_player_tracker.data_IO.player_history;


import java.io.Serializable;

public class PlayerLocationData implements Serializable {
    private final String userName;
    public boolean rawDataStored = false;
    public boolean displayDataStored = false;
    private long rawDataPositionStart;
    private long rawDataPositionEnd;
    private long displayPositionStart;
    private long displayPositionEnd;

    /**
     * Creates Object to store starting and ending positions of RandomAccess Files.
     *
     * @param userName In all lowercase.
     */
    public PlayerLocationData(String userName) {
        this.userName = userName;
    }

    /**
     * Merges this instance with provided {@code PlayerLocationData}.
     *
     * @param inPlayer {@code PlayerLocationData} to be merged with instance.
     */
    public void mergePlayer(PlayerLocationData inPlayer) {
        if (rawDataStored && inPlayer.isDisplayDataStored()) {
            this.setDisplayPositionStart(inPlayer.getDisplayPositionStart());
            this.setDisplayPositionEnd(inPlayer.getDisplayPositionEnd());
        } else if (displayDataStored && inPlayer.isRawDataStored()) {
            this.setRawDataPositionStart(inPlayer.getRawDataPositionStart());
            this.setRawDataPositionEnd(inPlayer.getRawDataPositionEnd());
        }
    }

    public String getUserName() {
        return userName;
    }

    public long getDisplayPositionStart() {
        return displayPositionStart;
    }

    /**
     * Assigns displayPositionStart position and sets displayDataStored to true.
     *
     * @param displayPositionStart
     */
    public void setDisplayPositionStart(long displayPositionStart) {
        this.displayPositionStart = displayPositionStart;
        this.displayDataStored = true;
    }

    public long getDisplayPositionEnd() {
        return displayPositionEnd;
    }

    public void setDisplayPositionEnd(long displayPositionEnd) {
        this.displayPositionEnd = displayPositionEnd;
    }

    public long getRawDataPositionStart() {
        return rawDataPositionStart;
    }

    /**
     * Assigns rawDataPositionStart position and sets rawDataStored to true.
     *
     * @param rawDataPositionStart
     */
    public void setRawDataPositionStart(long rawDataPositionStart) {
        this.rawDataPositionStart = rawDataPositionStart;
        this.rawDataStored = true;
    }

    public long getRawDataPositionEnd() {
        return rawDataPositionEnd;
    }

    public void setRawDataPositionEnd(long rawDataPositionEnd) {
        this.rawDataPositionEnd = rawDataPositionEnd;
    }

    public boolean isRawDataStored() {
        return rawDataStored;
    }

    public boolean isDisplayDataStored() {
        return displayDataStored;
    }

}

