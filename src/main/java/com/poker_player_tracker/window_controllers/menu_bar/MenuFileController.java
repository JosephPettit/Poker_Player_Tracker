package com.poker_player_tracker.window_controllers.menu_bar;

import com.poker_player_tracker.data_IO.DataManager;
import com.poker_player_tracker.data_IO.IncorrectInputFileFormatting;
import com.poker_player_tracker.data_IO.RequiredFileAccessDeniedException;
import com.poker_player_tracker.data_IO.RequiredFileNotFoundException;
import com.poker_player_tracker.window_controllers.AlertWindowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;


public class MenuFileController {

    @FXML
    private MenuItem folderProcess;
    @FXML
    private MenuItem mulFilesProcess;
    private DataManager dataManager;


    @FXML
    private void processFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Game Files");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Poker History File (.txt)", "*.txt"));
        List<File> fileList = fileChooser.showOpenMultipleDialog(new Stage());
        if (fileList != null)
            gameFileToProcess(fileList);
    }

    @FXML
    private void processFolder(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Upload Game File Directory");
        FileFilter fileFilter = pathname -> pathname.getName().startsWith("Poker Hand History");
        File folder = directoryChooser.showDialog(new Stage());
        if (folder != null) {
            List<File> fileList = List.of(Objects.requireNonNull(folder.listFiles(fileFilter)));
            gameFileToProcess(fileList);
        }
    }

    private void gameFileToProcess(List<File> fileList) {
        Queue<File> fileQueue = new LinkedList<>(fileList);
        Queue<String> completedFiles = new LinkedList<>();
        Queue<String> duplicateFiles = new LinkedList<>();
        Queue<String> failedFiles = new LinkedList<>();
        dataManager = new DataManager();
        AlertWindowController alert = new AlertWindowController();
        int completed = 0;
        int starting = fileList.size();
        while (!fileQueue.isEmpty()) {
            File currentFile = fileQueue.poll();
            try {
                if (dataManager.processGameFile(currentFile)) {
                    completed++;
                    completedFiles.offer(currentFile.getName());
                } else {
                    duplicateFiles.offer(currentFile.getName());
                }

            } catch (IncorrectInputFileFormatting | NullPointerException e) {
                failedFiles.offer(currentFile.getName());
                alert.displayError(e, "Input File Reading Exception (not a Poker History File)");
            } catch (RequiredFileNotFoundException e) {
                failedFiles.offer(currentFile.getName());
                alert.displayError(e, "File IO Exception: Unable to read, write, or create necessary game files.");
            }catch (RequiredFileAccessDeniedException e) {
                    failedFiles.offer(currentFile.getName());
                    alert.displayError(e, "File IO Exception: Access to a required file was denied by the System's security manager.");
            } catch (Exception e) {
                failedFiles.offer(currentFile.getName());
                alert.displayError(e, "General Exception");
            }
        }
        alert.displayCompleted(starting, completed, completedFiles, duplicateFiles, failedFiles);
    }

//    private void displayCompleted(int starting, int completed, Queue<String> completedFiles, Queue<String> duplicateFiles, Queue<String> failedFiles) {
//        StringBuilder sb = new StringBuilder();
//        String completedText;
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("File Upload Completed");
//
//        if (starting == completed) {
//            alert.setHeaderText("I have some great news!");
//            alert.setContentText(completed + " out of " + starting + " files below have been processed successfully");
//            addFilesToLog(sb, "Successfully processed files: \n\n", completedFiles);
//
//        } else if (starting > completed && completed != 0) {
//            alert.setHeaderText("Well, I have some good news and some bad news...");
//            alert.setContentText("Only " + completed + " out of " + starting + ", the files selected successfully");
//
//            if (!completedFiles.isEmpty()) {
//                addFilesToLog(sb, "Successfully processed files: \n\n", completedFiles);
//            }
//            if (!duplicateFiles.isEmpty()) {
//                addFilesToLog(sb, "\nDuplicated Skipped files: \n\n", duplicateFiles);
//            }
//            if (!failedFiles.isEmpty()) {
//                addFilesToLog(sb, "\nFailed files: \n\n", failedFiles);
//            }
//
//        } else {
//            alert.setHeaderText("Bad News....");
//            alert.setContentText("None of the selected files processed successfully.");
//
//            if (!duplicateFiles.isEmpty()) {
//                addFilesToLog(sb, "\nDuplicated Skipped files: \n\n", duplicateFiles);
//            }
//            if (!failedFiles.isEmpty()) {
//                addFilesToLog(sb, "\nFailed files: \n\n", failedFiles);
//            }
//        }
//
//        completedText = sb.toString();
//        displayLog("File processing log is below:", completedText, alert);
//
//    }
//
//    private void addFilesToLog(StringBuilder sb, String str, Queue<String> fileQueue) {
//        sb.append(str);
//        for (String fileName : fileQueue)
//            sb.append(fileName).append("\n");
//    }
//
//    private void displayError(Exception e, String errorType) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Exception Dialog");
//        alert.setHeaderText("An un-recoverable " + errorType + " error has accord");
//        alert.setContentText("If you're feeling up-to the challenge, the stacktrace is below. \n\n");
//
//        // Create expandable Exception.
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        e.printStackTrace(pw);
//        String exceptionText = sw.toString();
//        displayLog("The exception stacktrace is:", exceptionText, alert);
//    }
//
//    private void displayLog(String s, String messageText, Alert alert) {
//        Label label = new Label(s);
//        TextArea textArea = new TextArea(messageText);
//        textArea.setEditable(false);
//        textArea.setWrapText(true);
//        textArea.setMaxWidth(Double.MAX_VALUE);
//        textArea.setMaxHeight(Double.MAX_VALUE);
//        GridPane.setVgrow(textArea, Priority.ALWAYS);
//        GridPane.setHgrow(textArea, Priority.ALWAYS);
//        GridPane expContent = new GridPane();
//        expContent.setMaxWidth(Double.MAX_VALUE);
//        expContent.add(label, 0, 0);
//        expContent.add(textArea, 0, 1);
//
//        // Set expandable Exception into the dialog pane.
//        alert.getDialogPane().setExpandableContent(expContent);
//        alert.showAndWait();
//    }

    @FXML
    private void closeApplication(ActionEvent event) {
        System.exit(0);
    }
}