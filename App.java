import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Go {
    static String[][] goBoard = new String[9][9];
    static boolean[][] checked = new boolean[9][9];
    static final int BOARD_SIZE = 9;

    public static void main(String[] args) {
        boolean turn = true; // true for Black, false for White
        Scanner scn = new Scanner(System.in);

        while (true) {
            displayBoard();
            System.out.println("\n" + (turn ? "Black" : "White") + "'s turn to move!");
            
            int[] move = getValidMove(scn, turn);
            int moveY = move[0];
            int moveX = move[1];
            
            goBoard[moveY][moveX] = turn ? "-●" : "-◯";
            
            captureOpponentPieces(!turn);
            
            turn = !turn;
        }
    }

    private static void displayBoard() {
        System.out.println("  0 1 2 3 4 5 6 7 8");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (goBoard[i][j] == null) {
                    System.out.print(j == 0 ? "|" : "-|");
                } else {
                    System.out.print(goBoard[i][j]);
                }
            }
            System.out.println();
        }
    }

    private static int[] getValidMove(Scanner scn, boolean turn) {
        while (true) {
            System.out.println("Enter coordinates (row column): ");
            String[] input = scn.nextLine().split(" ");
            if (input.length != 2) {
                System.out.println("Invalid input. Please enter two numbers.");
                continue;
            }
            
            try {
                int moveY = Integer.parseInt(input[0]);
                int moveX = Integer.parseInt(input[1]);
                
                if (moveY < 0 || moveY >= BOARD_SIZE || moveX < 0 || moveX >= BOARD_SIZE) {
                    System.out.println("Out of bounds. Try again.");
                    continue;
                }
                
                if (goBoard[moveY][moveX] != null) {
                    System.out.println("Space already occupied. Try again.");
                    continue;
                }
                
                return new int[]{moveY, moveX};
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers.");
            }
        }
    }

    private static void captureOpponentPieces(boolean opponentColor) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (goBoard[i][j] != null && goBoard[i][j].equals(opponentColor ? "-●" : "-◯")) {
                    if (!hasLiberties(i, j)) {
                        removePiece(i, j);
                    }
                }
            }
        }
        resetChecked();
    }

    private static boolean hasLiberties(int y, int x) {
        if (y < 0 || y >= BOARD_SIZE || x < 0 || x >= BOARD_SIZE || checked[y][x]) {
            return false;
        }
        
        checked[y][x] = true;
        
        if (goBoard[y][x] == null) {
            return true;
        }
        
        String currentColor = goBoard[y][x];
        
        return hasLiberties(y-1, x) || hasLiberties(y+1, x) || 
               hasLiberties(y, x-1) || hasLiberties(y, x+1);
    }

    private static void removePiece(int y, int x) {
        goBoard[y][x] = null;
    }

    private static void resetChecked() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                checked[i][j] = false;
            }
        }
    }
}



