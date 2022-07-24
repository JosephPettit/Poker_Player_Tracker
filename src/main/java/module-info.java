module com.poker_player_tracker.poker_player_tracker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.poker_player_tracker to javafx.fxml;
    exports com.poker_player_tracker;
    exports com.poker_player_tracker.window_controllers;
    opens com.poker_player_tracker.window_controllers to javafx.fxml;
    exports com.poker_player_tracker.data_IO.player_data;
    opens com.poker_player_tracker.data_IO.player_data to javafx.fxml;
    exports com.poker_player_tracker.window_controllers.table_tabs;
    opens com.poker_player_tracker.window_controllers.table_tabs to javafx.fxml;
    exports com.poker_player_tracker.window_controllers.menu_bar;
    opens com.poker_player_tracker.window_controllers.menu_bar to javafx.fxml;
    exports com.poker_player_tracker.data_IO;
    opens com.poker_player_tracker.data_IO to javafx.fxml;
    exports com.poker_player_tracker.data_IO.game_file_history;
    opens com.poker_player_tracker.data_IO.game_file_history to javafx.fxml;
    exports com.poker_player_tracker.data_IO.processor;
    opens com.poker_player_tracker.data_IO.processor to javafx.fxml;
    exports com.poker_player_tracker.data_IO.player_history;
    opens com.poker_player_tracker.data_IO.player_history to javafx.fxml;
}