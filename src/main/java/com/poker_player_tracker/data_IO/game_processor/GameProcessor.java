package com.poker_player_tracker.data_IO.game_processor;


import com.poker_player_tracker.data_IO.game_file_history.GameFileData;
import com.poker_player_tracker.data_IO.player_data.PlayerData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class GameProcessor {
    private HashMap<String, PlayerData> playersDataMap;
    private boolean preFlop, reRaise, gameStart;
    private int endingHand;


    /**
     * Processes {@code inputFile} into {@link GameFileData} object then returns the {@code GameFileData} created.
     *
     * @param inputFile Name of input file, of type: File
     * @return {@code HashMap <String, PlayerData>}
     */
    public GameFileData processFile(File inputFile) throws IOException, NumberFormatException {
        playersDataMap = new HashMap<>(18);
        String[] currentTableArr = new String[9];
        String currentLine;
        String firstLine;

        gameStart = preFlop = reRaise = false;
        int startingHand = 0, gameID;
        endingHand = 0;


        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile.getAbsoluteFile()))) {

            if ((firstLine = reader.readLine()) == null) {
                throw new IOException("Specified game file is not formatted correctly. " +
                        "\nEnsure you are loading a ClubWPT Poker Hand File ");
            }

            gameID = Integer.parseInt(firstLine.substring(firstLine.indexOf(" ") + 1));

            while ((currentLine = reader.readLine()) != null) {

                if (currentLine.contains(GameFileKey.STARTS.toString())) {
                    if (!gameStart) {
                        startingHand = Integer.parseInt(currentLine.substring(currentLine.indexOf("-") + 1, currentLine.indexOf(" " + "starts")));
                    }
                    gameStart = preFlop = true;
                }
                // Checks for bypass condition if false, and game start true, will process file.
                else if (!bypassKeyLocated(currentLine) && gameStart) {
                    for (GameFileKey gameKey : GameFileKey.values()) {
                        if (currentLine.contains(gameKey.toString())) {
                            switch (gameKey) {
                                case SEAT -> seatAndPlayerAssignment(currentTableArr, currentLine);
                                case ANTE -> playerJoinLate(currentLine);
                                case ALL_IN, RAISE -> containsRaiseORAll_in(currentLine);
                                case ENDS -> containsEnds(currentTableArr, currentLine);
                                case BUTTON -> containsButtonPos(currentLine, currentTableArr);
                                case ROUND -> containsRound();
                                case BET ->
                                        playersDataMap.get(userNameParsed(currentLine, NameParseKey.BETS)).incrementBet();
                                case WINS ->
                                        playersDataMap.get(userNameParsed(currentLine, NameParseKey.WINS)).incrementWins();
                                case BIG_BLIND ->
                                        playersDataMap.get(userNameParsed(currentLine, NameParseKey.BIGBLIND)).setBigBlind(true);
                                case SMALL_BLIND ->
                                        playersDataMap.get(userNameParsed(currentLine, NameParseKey.SMALLBLIND)).setSmallBlind(true);
                                case UNCALLED ->
                                        playersDataMap.get(userNameParsed(currentLine, NameParseKey.RETURNED)).setUncalled(true);
                            }
                            break; //bypass other string contains after located.
                        } //end if filtering conditions
                    } // end Game Key for loop
                } // end if !bypass condition, and game started
            } // end while current line !null
        } // end try

        return new GameFileData(gameID, startingHand, endingHand, playersDataMap);
    } // end of process file

    private void seatAndPlayerAssignment(String[] currentTableArr, String currentLine) {
        int seatNumber = Character.getNumericValue(currentLine.charAt(5));
        String playerName = currentLine.substring(8, currentLine.indexOf("("));
        playerName = playerName.trim();

        if (currentTableArr[seatNumber] == null || !currentTableArr[seatNumber].equals(playerName)) {
            PlayerData player = new PlayerData(playerName);
            currentTableArr[seatNumber] = player.getUserName();
        }

        if (!playersDataMap.containsKey(userNameFormatted(playerName))) {
            PlayerData player = new PlayerData(playerName);
            playersDataMap.put(userNameFormatted(player.getUserName()), player);
        }
    }

    private void playerJoinLate(String currentLine) {
        String playerName = currentLine.substring(0, currentLine.indexOf("posts ante"));
        playerName = playerName.trim();
        PlayerData newPlayer = new PlayerData(playerName);
        newPlayer.incrementHandsPlayed();
        if (playersDataMap.containsKey(userNameFormatted(playerName))) {
            playersDataMap.get(userNameFormatted(playerName)).mergeGameData(newPlayer);
        } else {
            playersDataMap.put(userNameFormatted(playerName), newPlayer);
        }
    }

    private void containsButtonPos(String currentLine, String[] currentTableArr) {
        int buttonPos = Character.getNumericValue(currentLine.charAt(currentLine.length() - 2));
        if (currentTableArr[buttonPos] != null) {
            playersDataMap.get(userNameFormatted(currentTableArr[buttonPos])).setOnButton(true);
        }
    }

    private void containsRound() {
        if (preFlop) {
            preFlop = false;
        }
        reRaise = false;
    }

    private void containsRaiseORAll_in(String currentLine) {
        if (currentLine.contains(GameFileKey.ALL_IN.toString())) {
            containsAll_in(currentLine);
        } else {
            containsRaise(currentLine);
        }
    }

    private void containsAll_in(String currentLine) {
        String actionPlayer;
        if (currentLine.contains(NameParseKey.GOES.getParseKey())) {
            actionPlayer = userNameParsed(currentLine, NameParseKey.GOES);
        } else if (currentLine.contains(NameParseKey.CALLS.getParseKey())) {
            actionPlayer = userNameParsed(currentLine, NameParseKey.CALLS);
        } else {
            actionPlayer = userNameParsed(currentLine, NameParseKey.RAISE);
        }
        playersDataMap.get(actionPlayer).incrementAll_inGameTotal(preFlop);
        playersDataMap.get(actionPlayer).setAll_in(true);
    }

    private void containsRaise(String currentLine) {
        if (currentLine.contains(NameParseKey.RERAISE.getParseKey())) {
            if (reRaise) {
                playersDataMap.get(userNameParsed(currentLine, NameParseKey.RERAISE)).incrementThreeBetPlus(preFlop);
            } else {
                playersDataMap.get(userNameParsed(currentLine, NameParseKey.RERAISE)).incrementReRaise(preFlop);
                reRaise = true;
            }
        } else {
            playersDataMap.get(userNameParsed(currentLine, NameParseKey.RAISE)).incrementRaise(preFlop);
        }
    }

    private void containsEnds(String[] currentTableArr, String currentLine) {
        for (String player : currentTableArr) {
            if (player != null) {
                playersDataMap.get(userNameFormatted(player)).endHand();
            }
        }
        endingHand = Integer.parseInt(currentLine.substring(currentLine.indexOf("-") + 1, currentLine.indexOf(" ends")));
    }

    private String userNameParsed(String currentLine, NameParseKey key) {
        return userNameFormatted(currentLine.substring(0, currentLine.indexOf(key.getParseKey())));
    }

    private String userNameFormatted(String actionPlayer) {
        actionPlayer = actionPlayer.trim().toLowerCase();
        return actionPlayer;
    }

    private boolean bypassKeyLocated(String currentLine) {
        return ((currentLine.contains("call") && (!currentLine.contains("all-in") && !currentLine.contains("returned")))
                || currentLine.contains("dealt") || currentLine.contains("at seat") || currentLine.isBlank()
                || currentLine.contains("checks") || currentLine.contains("folds") || currentLine.contains("Dealt")
                || currentLine.contains("Dealing") || currentLine.contains("no action in this round")
                || currentLine.contains("all-in showdown") || currentLine.contains("shows")
                || currentLine.contains("The button is at") || currentLine.contains("Hand evaluation")
                || currentLine.contains("leaves the "));
    }

} // end of class


