package com.poker_player_tracker.window_controllers.menu_bar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class UploadWindowController {


    @FXML
    private Label lbFileName;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button btnOkay;

    public void setLbFileName(){
        lbFileName.setVisible(true);
        lbFileName.setText("working?");
    }


}
