import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Go {
    static String[][] goBoard = {
        {null, null, "-◯", "-◯", null, null, null, null, null},
        {null, "-◯", "-●", "-●", "-◯", null, null, null, null},
        {null, "-◯", "-●", null, "-●", "-◯", null, null, null},
        {null, "-◯", "-●", "-●", "-●", "-◯", null, null, null},
        {null, "-◯", "-●", null, "-●", "-◯", null, null, null},
        {null, null, "-◯", "-●", "-●", "-◯", null, null, null},
        {null, null, null, "-◯", "-◯", null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null}
    };
    static boolean[][] checked = new boolean[9][9];
    static boolean[][] lives = new boolean[9][9];
    static boolean[][] territory = new boolean[9][9];
    static boolean[][] beenChecked = new boolean[9][9];
    static final int BOARD_SIZE = 9;

    public static void main(String[] args) {
        boolean turn = true; // true for Black, false for White
        Scanner scn = new Scanner(System.in);

        initializeLookupTables();

        while (true) {
            displayBoard();
            System.out.println("\n" + (turn ? "Black" : "White") + "'s turn to move!");
            
            int[] move = getValidMove(scn, turn);
            int moveY = move[0];
            int moveX = move[1];
            
            goBoard[moveY][moveX] = turn ? "-●" : "-◯";
            
            processTurn(moveY, moveX);
            
            turn = !turn;
        }
    }

    private static void initializeLookupTables() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                lives[i][j] = true;
                territory[i][j] = false;
                beenChecked[i][j] = false;
            }
        }
    }

    private static void processTurn(int y, int x) {
        updateLookupTables(y, x);
        captureOpponentPieces(!isBlackPiece(goBoard[y][x]));
    }

    private static void updateLookupTables(int y, int x) {
        resetBeenChecked();
        if (!hasLiberties(y, x)) {
            lives[y][x] = false;
        }

    }

    private static void resetBeenChecked() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                beenChecked[i][j] = false;
            }
        }
    }

    private static boolean isBlackPiece(String piece) {
        return "-●".equals(piece);
    }



    private static void captureOpponentPieces(boolean opponentColor) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (goBoard[i][j] != null && goBoard[i][j].equals(opponentColor ? "-●" : "-◯")) {
                    if (!hasLiberties(i, j)) {
                        removePiece(i, j);
                        lives[i][j] = false;
                    }
                }
            }
        }
        resetChecked();
    }

    private static boolean hasLiberties(int y, int x) {
        if (y < 0 || y >= BOARD_SIZE || x < 0 || x >= BOARD_SIZE || beenChecked[y][x]) {
            return false;
        }
        
        beenChecked[y][x] = true;
        
        if (goBoard[y][x] == null) {
            return true;
        }
        
        String currentColor = goBoard[y][x];
        
        return hasLiberties(y-1, x) || hasLiberties(y+1, x) || 
               hasLiberties(y, x-1) || hasLiberties(y, x+1);
    }


}



