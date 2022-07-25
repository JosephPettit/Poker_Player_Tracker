package com.poker_player_tracker.window_controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Queue;

public class AlertWindowController {

    public void displayError(Exception e, String errorType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("An un-recoverable " + errorType + " error has accord");
        alert.setContentText("If you're feeling up-to the challenge, the stacktrace is below. \n\n");

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();
        displayLog("The exception stacktrace is:", exceptionText, alert);
    }

    public void displayCompleted(int starting, int completed, Queue<String> completedFiles, Queue<String> duplicateFiles, Queue<String> failedFiles) {
        StringBuilder sb = new StringBuilder();
        String completedText;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("File Upload Completed");

        if (starting == completed) {
            alert.setHeaderText("I have some great news!");
            alert.setContentText(completed + " out of " + starting + " files below have been processed successfully");
            addFilesToLog(sb, "Successfully processed files: \n\n", completedFiles);

        } else if (starting > completed && completed != 0) {
            alert.setHeaderText("Well, I have some good news and some bad news...");
            alert.setContentText("Only " + completed + " out of " + starting + ", the files selected successfully");

            if (!completedFiles.isEmpty()) {
                addFilesToLog(sb, "Successfully processed files: \n\n", completedFiles);
            }
            if (!duplicateFiles.isEmpty()) {
                addFilesToLog(sb, "\nDuplicated Skipped files: \n\n", duplicateFiles);
            }
            if (!failedFiles.isEmpty()) {
                addFilesToLog(sb, "\nFailed files: \n\n", failedFiles);
            }

        } else {
            alert.setHeaderText("Bad News....");
            alert.setContentText("None of the selected files processed successfully.");

            if (!duplicateFiles.isEmpty()) {
                addFilesToLog(sb, "\nDuplicated Skipped files: \n\n", duplicateFiles);
            }
            if (!failedFiles.isEmpty()) {
                addFilesToLog(sb, "\nFailed files: \n\n", failedFiles);
            }
        }

        completedText = sb.toString();
        displayLog("File processing log is below:", completedText, alert);

    }

    private void addFilesToLog(StringBuilder sb, String str, Queue<String> fileQueue) {
        sb.append(str);
        for (String fileName : fileQueue)
            sb.append(fileName).append("\n");
    }
    private void displayLog(String s, String messageText, Alert alert) {
        Label label = new Label(s);
        TextArea textArea = new TextArea(messageText);
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
