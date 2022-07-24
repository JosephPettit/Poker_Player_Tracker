package com.poker_player_tracker.window_controllers.table_tabs;


import com.poker_player_tracker.data_IO.player_data.PlayerDisplayData;
import com.poker_player_tracker.window_controllers.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class WinningTabController implements Initializable {
    @FXML
    public TableView<PlayerDisplayData> winningTable;

    @FXML
    private TableColumn<PlayerDisplayData, String> nameColumn;

    @FXML
    private TableColumn<PlayerDisplayData, Integer> handsPlayed;

    @FXML
    private TableColumn<PlayerDisplayData, Integer> all_In_Total;

    @FXML
    private TableColumn<PlayerDisplayData, Integer> all_in_Wins;

    @FXML
    private TableColumn<PlayerDisplayData, Integer> uncalledWins;

    @FXML
    private TableColumn<PlayerDisplayData, Integer> wins;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        handsPlayed.setCellValueFactory(new PropertyValueFactory<>("handsPlayed"));
        all_In_Total.setCellValueFactory(new PropertyValueFactory<>("all_inTotal"));
        all_in_Wins.setCellValueFactory(new PropertyValueFactory<>("all_inWin"));
        uncalledWins.setCellValueFactory(new PropertyValueFactory<>("uncalledWins"));
        wins.setCellValueFactory(new PropertyValueFactory<>("wins"));

        winningTable.setItems(MainController.getPlayerList());

    }

}
