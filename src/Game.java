import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the difficulty level: ");
        System.out.println("Press 0 for BEGINNER (9 * 9 cells and 10 Mines)");
        System.out.println("Press 1 for INTERMEDIATE (16 * 16 cells and 40 Mines)");
        System.out.println("Press 2 for BEGINNER (24 * 24 cells and 99 Mines)");
        String level = scanner.nextLine();
        char[][] board = initBoard(level);
        printBoard(board);
        System.out.println("Select a position: ");
        String command = scanner.nextLine();

        while (true) {
            int row = Integer.parseInt(command.split(" ")[0]);
            int col = Integer.parseInt(command.split(" ")[1]);
            if (board[row][col] == '*') {
                System.out.println("You lost! You stepped on a mine.");
                break;
            }
            calc(row, col, board);
            printBoard(board);

            System.out.println("Select a position: ");
            command = scanner.nextLine();
        }
    }

    //Initialize the board, filled with *(mines) and -(empty).
    private static char[][] initBoard(String level) {
        char[][] board;
        int minesCount = 0;
        switch (level) {
            case "0":
                board = new char[9][9];
                minesCount = 10;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        board[i][j] = '-';
                    }
                }
                fillMines(board, minesCount);
                break;
            case "1":
                board = new char[16][16];
                minesCount = 40;
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        board[i][j] = '-';
                    }
                }
                fillMines(board, minesCount);
                break;
            case "2":
                board = new char[24][24];
                minesCount = 99;
                for (int i = 0; i < 24; i++) {
                    for (int j = 0; j < 24; j++) {
                        board[i][j] = '-';
                    }
                }
                fillMines(board, minesCount);
                break;
            default:
                throw new IllegalStateException("Please select a valid difficulty.");
        }
        return board;
    }

    //Fill the board with mines on random places, corresponding to its size
    private static void fillMines(char[][] board, int minesCount) {
        Random rand = new Random();
        int mineCountData = 0;
        while (mineCountData < minesCount) {
            int randInt = (int) (rand.nextDouble() * board.length);
            int randInt2 = (int) (rand.nextDouble() * board[0].length);
            board[randInt][randInt2] = '*';
            mineCountData++;
        }
    }

    //Show the board's status
    private static void printBoard(char[][] board) {
        System.out.println("CURRENT STATUS OF BOARD: ");

        System.out.println();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {

                System.out.print(board[i][j] + " ");
            }
            System.out.println("\n");
        }
    }



    //Calculate the nearby mines
    private static void calc(int row, int col, char[][] board) {
        if (row < 0 || row > board.length - 1 || col < 0 || col > board.length - 1) {
            return;
        }
        int count = 0;
        if (board[row][col] != '*') {
            //check up
            if (row > 0 && board[row - 1][col] == '*') {
                count++;
            }
            //check down
            if (row < board.length - 1 && board[row][col] == '*') {
                count++;
            }
            //check left
            if (col > 0 && board[row][col - 1] == '*') {
                count++;
            }
            //check right
            if (col < board[0].length && board[row][col + 1] == '*') {
                count++;
            }
            //check northwest
            if (row > 0 && col > 0 && board[row - 1][col - 1] == '*') {
                count++;
            }
            //check northeast
            if (col < board.length - 1 && row > 0 && board[row - 1][col + 1] == '*') {
                count++;
            }
            //check southwest
            if (col > 0 && row < board.length - 1 && board[row + 1][col - 1] == '*') {
                count++;
            }
            //check southeast
            if (col < board.length - 1 && row < board.length - 1 && board[row + 1][col + 1] == '*') {
                count++;
            }
        }
        //set a number of nearby mines
        if (count != 0) {
            board[row][col] = Character.forDigit(count, 10);
        }
        //clear adjacent cells
        if (count == 0 && board[row][col] != '*') {
            calc(row, col -1, board);
            calc(row - 1, col, board);
            calc(row +1 , col, board);
            calc(row, col+1, board);
            calc(row - 1 , col - 1, board);
            calc(row +1 , col - 1, board);
            calc(row -1 , col+1, board);
            calc(row +1 , col+1, board);
        }
    }

}
