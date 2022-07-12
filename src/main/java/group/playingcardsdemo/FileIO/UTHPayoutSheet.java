package group.playingcardsdemo.FileIO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UTHPayoutSheet extends PayoutSheet {
    public static String[][] rawSheet = new String[3][15];

    public static int[] tripsBonus = new int[7];
    public static int[] blindBonus = new int[7];
    public static int[] pocketBonus = new int[7];

    public static void parseRawSheet() {
        int fastIncrementer = 2;
        for (int i = 0; i < tripsBonus.length; i++) {
            tripsBonus[i] = Integer.parseInt(rawSheet[0][fastIncrementer]);
            fastIncrementer += 2;
        }
        fastIncrementer = 2;
        for (int i = 0; i < blindBonus.length; i++) {
            blindBonus[i] = Integer.parseInt(rawSheet[1][fastIncrementer]);
            fastIncrementer += 2;
        }
        fastIncrementer = 2;
        for (int i = 0; i < pocketBonus.length; i++) {
            pocketBonus[i] = Integer.parseInt(rawSheet[2][fastIncrementer]);
            fastIncrementer += 2;
        }
    }

    public static void printRawSheet() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < rawSheet[0].length; j++) {
                System.out.print(rawSheet[i][j]);

                if (j == 0) {
                    System.out.print(": ");
                }
                else if (j < rawSheet[0].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
    public static void printTripsBonus() {
        System.out.println("Trips: ");
        for (int bonus : tripsBonus) {
            System.out.print(bonus + " ");
        }
        System.out.println();
    }
    public static void printBlindBonus() {
        System.out.println("Blind: ");
        for (int bonus : blindBonus) {
            System.out.print(bonus + " ");
        }
        System.out.println();
    }
    public static void printPocketBonus() {
        System.out.println("Pocket: ");
        for (int bonus : pocketBonus) {
            System.out.print(bonus + " ");
        }
        System.out.println();
    }
}
