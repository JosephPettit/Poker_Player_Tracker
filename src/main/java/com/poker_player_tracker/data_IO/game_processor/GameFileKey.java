package com.poker_player_tracker.data_IO.game_processor;

public enum GameFileKey {

    // Needs to be evaluated first
    ANTE("posts ante"),
    SEAT("Seat "),

    // In order by occurrence frequency
    ROUND("Round "),
    BIG_BLIND("big blind"),
    SMALL_BLIND("small blind"),
    WINS("wins "),
    BUTTON("The button is moved"),
    ENDS("ends"),
    BET("bets for"),
    RAISE("raises"),
    ALL_IN("all-in"),
    UNCALLED("uncalled"),

    // Has own conditional statement - Does not need to process in loop.
    STARTS("starts");

    private final String gameKey;

    /**
     * String representation of GameFileKey
     *
     * @param str Returns String GameFileKey
     */
    GameFileKey(String str) {
        this.gameKey = str;
    }

    @Override
    public String toString() {
        return gameKey;
    }
}
