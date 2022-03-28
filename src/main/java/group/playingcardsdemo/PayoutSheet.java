package group.playingcardsdemo;

import java.util.ArrayList;

public class PayoutSheet {
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
