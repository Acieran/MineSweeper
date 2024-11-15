module org.acieran.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.acieran.minesweeper to javafx.fxml;
    exports org.acieran.minesweeper;
}