package com.poker_player_tracker.data_IO;

public class RequiredFileAccessDeniedException extends SecurityException {
    public RequiredFileAccessDeniedException(String message, Throwable cause) {
        super("File: " + message + ", could not be read or written too due to the System's security manager.", cause);
    }
}
