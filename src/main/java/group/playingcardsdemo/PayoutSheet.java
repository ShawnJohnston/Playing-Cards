package group.playingcardsdemo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter

public class PayoutSheet {
    public static String[][] payoutSheetUTH = new String[8][3];

    private String game;
    private String room;
    private ArrayList<Integer> groups = new ArrayList<>();

    PayoutSheet() {
        game = "";
        room = "Default";
    }
    PayoutSheet(String game, String room, ArrayList<Integer> groups) {
        this.game = game;
        this.room = room;
    }
}
