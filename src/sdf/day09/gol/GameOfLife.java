package sdf.day09.gol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class GameOfLife {

    private int[][] board;
    private int rows;
    private int columns;

    public void loadGame (String fileName) throws IOException {
        // File file = new File(fileName);
        int startRow = 0, startColumn = 0;

        try (FileReader fr = new FileReader(fileName)) {
            BufferedReader br = new BufferedReader(fr);

            String line;
            boolean readData = false;
            int rowsCount = 0;

            while((line = br.readLine()) != null) {
                
                if (line.startsWith("#")) {
                    continue;
                } else if (line.startsWith("GRID")) {
                    String[] gridSize = line.substring(5).trim().split(" ");
                    rows = Integer.parseInt(gridSize[1]);
                    columns = Integer.parseInt(gridSize[0]);
                    board = new int[rows][columns];

                } else if (line.startsWith("START")) {
                    String[] start = line.substring(6).trim().split(" ");
                    startRow = Integer.parseInt(start[1]);
                    startColumn = Integer.parseInt(start[0]);
                    // System.out.printf("startRow: %s, startColumn: %s%n", startRow,startColumn);

                } else if (line.startsWith("DATA")) {
                    readData = true;
                    // System.out.println("Reading data");
                    continue;
                } else if (readData && !line.trim().isBlank()) {
                    // System.out.println("Reading line");
                    char[] c = line.stripTrailing().toCharArray();
                    // System.out.println(Arrays.toString(c));
                    for (int i = 0; i < c.length; i++) {
                        if ("*".equals(String.valueOf(c[i])) && (startRow + rowsCount < rows) && (startColumn + i < columns)) {
                            board[startRow + rowsCount][startColumn + i] = 1;
                        } else {
                            continue;
                        }
                    }
                    rowsCount++;

                }
            
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            System.exit(1);
        }
    }

    public void playGame() {

        System.out.println("Generation 0:");
        printBoard();
        for (int i = 1; i < 6; i++) {
            System.out.printf("Generation %d:%n", i);
            evaluateBoard();
            printBoard();
        }

    } 

    public void evaluateBoard() {
        int[][] copyBoard = new int[rows][columns];
        for(int i = 0; i < rows; i++){
            copyBoard[i] = board[i].clone();
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boolean isAlive = board[i][j] == 1;
                int count = liveCellCount(i, j);
                // System.out.printf("alive: %b, count:%d / ", isAlive, count);

                if (isAlive && count <= 1) {
                    copyBoard[i][j] = 0;
                } else if (isAlive && count >= 2 && count <= 3) {
                    continue;
                } else if (isAlive && count >= 4) {
                    copyBoard[i][j] = 0;
                } else if (!isAlive && count == 3) {
                    copyBoard[i][j] = 1;
                }
            }
            // System.out.println("");
        }
        board = copyBoard;
    }

    public int liveCellCount(int rowIndex, int columnIndex) {
        int count = 0;
        
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((rowIndex + i) < 0 || (rowIndex + i) >= rows || (columnIndex + j) < 0 || (columnIndex + j) >= columns) {
                    continue;
                } else if (i == 0 && j == 0) {
                    continue;
                } else {
                    count += board[rowIndex + i][columnIndex + j];
                }
            }
        }
        return count;

    }

    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j ++) {
                if (board[i][j] != 0) {
                    System.out.print("*");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }
    }
    
}
