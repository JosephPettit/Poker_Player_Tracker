package com.poker_player_tracker.view_controllers.menu_controllers;

import com.poker_player_tracker.data_IO.DataManager;
import com.poker_player_tracker.data_IO.player_data.PlayerDisplayData;
import com.poker_player_tracker.data_IO.player_history.PlayerLocationData;
import com.poker_player_tracker.view_controllers.MainController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.util.HashMap;

public class MenuViewController {

    public MenuItem displayAll;

    public void displayAllPlayers(ActionEvent event) throws IOException, ClassNotFoundException {
        // TODO Locate and handle where exception propagates to.
        DataManager dataManager = new DataManager();
        HashMap<String, PlayerLocationData> playerData = dataManager.getPlayerHistory();
        ObservableList<PlayerDisplayData> playerList = MainController.getPlayerList();
        for (PlayerLocationData player : playerData.values()) {
            playerList.add(dataManager.getDisplayPlayer(player));
        }
    }
}
