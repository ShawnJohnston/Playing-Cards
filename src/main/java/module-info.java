module group.playingcardsdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;


    opens group.playingcardsdemo to javafx.fxml;
    exports group.playingcardsdemo;
}