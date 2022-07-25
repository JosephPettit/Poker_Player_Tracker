package com.poker_player_tracker.data_IO.custom_exceptions;

import java.io.IOException;

public class IncorrectInputFileFormattingException extends IOException {
    public IncorrectInputFileFormattingException(){
        super("Input file is not a Poker Game History File");
    }

    public IncorrectInputFileFormattingException(String message, Throwable cause) {
        super(message, cause);
    }
}
