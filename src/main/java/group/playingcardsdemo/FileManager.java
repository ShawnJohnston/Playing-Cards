package group.playingcardsdemo;

import java.io.*;

public class FileManager {
    BufferedReader reader = null;
    String line = "";

    public FileManager() {
    }

    public void inputPayoutSheet_UTH(String room) {
        try {
            String[][] row = new String[8][3];
            reader = new BufferedReader(new FileReader("src/main/resources/group/playingcardsdemo/UTH_" + room + ".csv"));

            int rowCount = 0;
            while((line = reader.readLine()) != null) {
                row[rowCount] = line.split(",");
                rowCount++;
            }
            Global.payoutSheetUTH = row;
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
