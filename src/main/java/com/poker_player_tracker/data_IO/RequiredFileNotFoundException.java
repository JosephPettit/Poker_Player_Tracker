package com.poker_player_tracker.data_IO;

import java.io.IOException;

public class RequiredFileNotFoundException extends IOException {
    public RequiredFileNotFoundException(String message, Throwable cause) {
        super("File: " + message + ", could not be located or created.", cause);
    }

    public RequiredFileNotFoundException(String message) {
        super(message);
    }
}
