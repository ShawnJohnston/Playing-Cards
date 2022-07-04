module group.playingcardsdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;
    requires java.desktop;
    requires lombok;


    opens group.playingcardsdemo to javafx.fxml;
    exports group.playingcardsdemo;
    exports group.playingcardsdemo.PlayingCards;
    opens group.playingcardsdemo.PlayingCards to javafx.fxml;
    exports group.playingcardsdemo.Chips;
    opens group.playingcardsdemo.Chips to javafx.fxml;
    exports group.playingcardsdemo.FileIO;
    opens group.playingcardsdemo.FileIO to javafx.fxml;
}