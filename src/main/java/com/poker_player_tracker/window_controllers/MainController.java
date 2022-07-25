package com.poker_player_tracker.window_controllers;

import com.poker_player_tracker.data_IO.DataManager;
import com.poker_player_tracker.data_IO.player_data.PlayerDisplayData;
import com.poker_player_tracker.data_IO.player_history.PlayerLocationData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private static final ObservableList<PlayerDisplayData> playerList = FXCollections.observableArrayList();

    @FXML
    public static javafx.scene.control.MenuBar MenuBar;

    @FXML
    public static javafx.scene.layout.BorderPane BorderPane;
    @FXML
    private Button clearButton;
    @FXML
    private Label notLocatedLabel;
    @FXML
    private TextField playerTextField;

    @FXML
    private Button searchButton;
    @FXML
    private MenuItem filesToProcess;
    public static ObservableList<PlayerDisplayData> getPlayerList() {
        return playerList;
    }

    @FXML
    protected void onSearchButtonClick(ActionEvent event) {
        HashMap<String, PlayerLocationData> playerData;
        try {
            playerData  = new DataManager().getPlayerHistory();
        } catch (IOException | ClassNotFoundException e) {
            playerData = new HashMap<>();
        }
        notLocatedLabel.setVisible(false);
        String playerName = playerTextField.getText().trim().toLowerCase();
        if (playerData.containsKey(playerName)) {
            try {
                playerList.add(new DataManager().getDisplayPlayer(playerData.get(playerName)));
            } catch (IOException e) {
                notLocatedLabel.setText("Unable to locate player: " + playerName);
                notLocatedLabel.setVisible(true);
            }
        } else {
            notLocatedLabel.setText("Unable to locate player: " + playerName);
            notLocatedLabel.setVisible(true);
        }
        playerTextField.clear();
    }

    @FXML
    protected void onClearButtonClick() {
        if(!playerList.isEmpty()) {
            notLocatedLabel.setVisible(false);
            playerList.clear();
            notLocatedLabel.setText("Cleared all players");
            notLocatedLabel.setVisible(true);
        }
        else {
            notLocatedLabel.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}