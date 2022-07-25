package com.poker_player_tracker.data_IO;

import java.io.IOException;

public class IncorrectInputFileFormatting extends IOException {
    public IncorrectInputFileFormatting (){
        super("Input file is not a Poker Game History File");
    }

    public IncorrectInputFileFormatting(String message, Throwable cause) {
        super(message, cause);
    }
}
