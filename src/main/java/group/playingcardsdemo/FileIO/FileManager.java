package group.playingcardsdemo.FileIO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {
    public static BufferedReader reader = null;
    public static String line = "";

    public static void inputPayoutSheet_UTH(String room) {
        try {
            String[][] row = new String[3][15];
            reader = new BufferedReader(new FileReader("src/main/resources/group/playingcardsdemo/PayoutSheets/UTH_" + room + ".csv"));

            int rowCount = 0;
            while((line = reader.readLine()) != null) {
                row[rowCount] = line.split(",");
                rowCount++;
            }
            UTHPayoutSheet.rawSheet = row;
            UTHPayoutSheet.parseRawSheet();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
