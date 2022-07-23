package com.poker_player_tracker.data_IO.processor;

/**
 * Enum class to hold String values to parse the players userName.
 */
public enum NameParseKey {
    BETS("bets"),
    BIGBLIND("posts the big"),
    SMALLBLIND("posts the small"),
    GOES("goes all-in"),
    CALLS("calls all-in"),
    RAISE("raises"),
    RERAISE("re-"),
    RETURNED("is returned"),
    WINS("wins ");

    private final String parseKey;

    NameParseKey(String str) {
        this.parseKey = str;
    }

    public String getParseKey() {
        return parseKey;
    }
}

