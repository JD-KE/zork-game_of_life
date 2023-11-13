package sdf.day09.gol;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        
        if (args.length <= 0) {
            System.out.println("Please load game file.");
            System.exit(1);
        }

        // Console cons = System.console();


        String gameFile = args[0].trim();
        if (!gameFile.endsWith(".gol")) {
            System.out.println("Please load .gol file.");
            System.exit(1);

        }

        GameOfLife game = new GameOfLife();

        try {
            game.loadGame(gameFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        game.playGame();
    }
    
    
}
