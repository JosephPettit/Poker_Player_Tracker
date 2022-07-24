package com.poker_player_tracker.window_controllers.menu_bar;

import com.poker_player_tracker.data_IO.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;



public class MenuFileController {

    @FXML
    private MenuItem folderProcess;
    @FXML
    private MenuItem mulFilesProcess;
    @FXML
    private MenuItem singleFileProcess;


    public void processOneFile(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Game File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PokerHistory File (.txt)", "*.txt"));

        File file = fileChooser.showOpenDialog(new Stage());
        actionUploadWindow(event);
        if (file != null) {
            fileChooser.setInitialDirectory(file.getParentFile()); // saves the directory for next time.
            gameFileToProcess(file);
        }
    }

    public void processMulFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Game Files");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Poker History File (.txt)", "*.txt"));
        List<File> fileList = fileChooser.showOpenMultipleDialog(new Stage());
        if(fileList != null)
            gameFileToProcess(fileList);
    }

    public void processFolder(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Upload Game File Directory");
        FileFilter fileFilter = pathname -> pathname.getName().startsWith("Poker Hand History");
        File folder = directoryChooser.showDialog(new Stage());
        if(folder != null) {
            List<File> fileList = List.of(Objects.requireNonNull(folder.listFiles(fileFilter)));
            gameFileToProcess(fileList);
        }
    }

    private void gameFileToProcess(File file) {
        try {
            new DataManager().processGameFile(file);
        }
        catch (NumberFormatException | StringIndexOutOfBoundsException | NullPointerException e){
            displayError(e, "Input File Reading Exception (not a Poker History File)");
        }
        catch(IOException e) {
            displayError(e, "File IO Exception");
        }
        catch(Exception e){
            displayError(e, "General Exception");
        }
    }

    private void gameFileToProcess(List<File> fileList) {
        Queue<File> fileQueue = new LinkedList<>(fileList);
        while(!fileQueue.isEmpty()){
            gameFileToProcess(fileQueue.poll());
        }
    }

    @FXML
    private void actionUploadWindow (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UploadWindow.fxml"));
        Parent root = loader.load();
        UploadWindowController uwc = loader.getController();
        uwc.setLbFileName();

//        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UploadWindow.fxml")));
        Stage stage = new Stage();
        stage.setTitle("Uploading Files!");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void closeApplication(ActionEvent event) {
        System.exit(0);
    }

    private void displayError(Exception e, String errorType){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("An un-recoverable "+ errorType +" error has accord");
        alert.setContentText("If you are feeling brave enough to debug. \nThe stacktrace is below. \n\n");

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();
        Label label = new Label("The exception stacktrace is:");
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }




}