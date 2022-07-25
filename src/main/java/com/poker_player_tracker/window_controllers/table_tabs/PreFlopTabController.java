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

public class PreFlopTabController implements Initializable {

    @FXML
    public TableView<PlayerDisplayData> preFlopTable;

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
        bigBlind.setCellValueFactory(new PropertyValueFactory<>("preFlopBigBlindRaise"));
        smallBlind.setCellValueFactory(new PropertyValueFactory<>("preFlopSmallBlindRaise"));
        button.setCellValueFactory(new PropertyValueFactory<>("preFlopButtonRaise"));
        raise.setCellValueFactory(new PropertyValueFactory<>("preFlopRaise"));
        reRaise.setCellValueFactory(new PropertyValueFactory<>("preFlopReRaise"));
        threeBet.setCellValueFactory(new PropertyValueFactory<>("preFlopThreeBet"));
        all_in.setCellValueFactory(new PropertyValueFactory<>("preFlopAll_in"));

        preFlopTable.setItems(MainController.getPlayerList());
    }

}