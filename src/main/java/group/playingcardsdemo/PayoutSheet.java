package group.playingcardsdemo;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class PayoutSheet {
    public static String[][] payoutSheetUTH = new String[8][3];

    private String game;
    private String room;
    private ArrayList<Integer> groups;
}
