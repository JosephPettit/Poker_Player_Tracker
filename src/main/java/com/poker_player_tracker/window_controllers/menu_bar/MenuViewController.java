package com.poker_player_tracker.window_controllers.menu_bar;

import com.poker_player_tracker.data_IO.DataManager;
import com.poker_player_tracker.data_IO.RequiredFileNotFoundException;
import com.poker_player_tracker.data_IO.player_data.PlayerDisplayData;
import com.poker_player_tracker.data_IO.player_history.PlayerLocationData;
import com.poker_player_tracker.window_controllers.AlertWindowController;
import com.poker_player_tracker.window_controllers.MainController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.util.HashMap;

public class MenuViewController {

    public MenuItem displayAll;

    public void displayAllPlayers(ActionEvent event) {
        DataManager dataManager = new DataManager();
        HashMap<String, PlayerLocationData> playerData;
        try {
            playerData = dataManager.getPlayerHistory();
            ObservableList<PlayerDisplayData> playerList = MainController.getPlayerList();
            for (PlayerLocationData player : playerData.values()) {
                playerList.add(dataManager.getDisplayPlayer(player));
            }
        } catch (IOException e) {
            new AlertWindowController().displayError(e, "File IO Exception: Unable to read, write, or create a necessary game file.");
        }
        catch(ClassNotFoundException e){
            new AlertWindowController().displayError(e, "Internal Error has occurred. Unable to locate internal class file");
        }
    }
}
