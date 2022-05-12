module group.playingcardsdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;
    requires java.desktop;
    requires lombok;


    opens group.playingcardsdemo to javafx.fxml;
    exports group.playingcardsdemo;
    exports group.playingcardsdemo.cards;
    opens group.playingcardsdemo.cards to javafx.fxml;
}