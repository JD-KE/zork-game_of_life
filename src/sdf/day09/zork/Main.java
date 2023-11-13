package sdf.day09.zork;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        
        if (args.length <= 0) {
            System.out.println("Please load game file.");
            System.exit(1);
        }

        // Console cons = System.console();


        String gameFile = args[0].trim();


        try {
            Game.loadGame(gameFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Game.playGame();
    }
    
}
