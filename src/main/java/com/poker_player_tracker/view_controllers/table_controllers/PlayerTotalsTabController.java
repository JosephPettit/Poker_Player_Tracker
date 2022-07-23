package com.poker_player_tracker.view_controllers.table_controllers;


import com.poker_player_tracker.data_IO.player_data.PlayerDisplayData;
import com.poker_player_tracker.view_controllers.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerTotalsTabController implements Initializable {

    @FXML
    protected TableView<PlayerDisplayData> totalsTable;
    @FXML
    private TableColumn<PlayerDisplayData, String> nameColumn;

    @FXML
    private TableColumn<PlayerDisplayData, Integer> handsPlayed;

    @FXML
    private TableColumn<PlayerDisplayData, Double> bigBlind;

    @FXML
    private TableColumn<PlayerDisplayData, Double> smallBlind;

    @FXML
    private TableColumn<PlayerDisplayData, Double> button;

    @FXML
    private TableColumn<PlayerDisplayData, Double> bet;

    @FXML
    private TableColumn<PlayerDisplayData, Double> raise;

    @FXML
    private TableColumn<PlayerDisplayData, Double> reRaise;

    @FXML
    private TableColumn<PlayerDisplayData, Double> threeBet;

    @FXML
    private TableColumn<PlayerDisplayData, Double> all_in;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        handsPlayed.setCellValueFactory(new PropertyValueFactory<>("handsPlayed"));
        bigBlind.setCellValueFactory(new PropertyValueFactory<>("bigBlindRaise"));
        smallBlind.setCellValueFactory(new PropertyValueFactory<>("smallBlindRaise"));
        button.setCellValueFactory(new PropertyValueFactory<>("buttonRaise"));
        bet.setCellValueFactory(new PropertyValueFactory<>("bet"));
        raise.setCellValueFactory(new PropertyValueFactory<>("raise"));
        reRaise.setCellValueFactory(new PropertyValueFactory<>("reRaise"));
        threeBet.setCellValueFactory(new PropertyValueFactory<>("threeBet"));
        all_in.setCellValueFactory(new PropertyValueFactory<>("all_inGame"));

        totalsTable.setItems(MainController.getPlayerList());

    }

}