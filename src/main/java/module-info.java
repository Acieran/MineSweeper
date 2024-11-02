module org.acieran.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.acieran.minesweeper to javafx.fxml;
    exports org.acieran.minesweeper;
}