module cc102.game {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens cc102.game to javafx.fxml;
    exports cc102.game;
}
