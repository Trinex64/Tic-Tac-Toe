import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int[][] board = { //  Empty = 0  |  X = 1  |  O = 2  \\
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };

        int winner = 0; // 0 = null | 1 = X | 2 = O | 3 = Draw
        int current_player = 1; // 1 = X | 2 = O

        System.out.println("Welcome to tic tac toe.");
        System.out.println("Answer with the coordinates of where you want to go.");
        System.out.println("Coordinates should look like 11 (Top left), 33 (Bottom right) or 22 (Middle)");
        System.out.println("Row goes first, then column. Example: 12 (Top middle)");
        System.out.println("X will start first.\n");

        while (winner == 0) {

            PrintBoard(board);
            String answer = scanner.next();

            if (answer.length() != 2) {
                System.out.println("Please enter a valid coordinate\n");
                continue;
            }

            // Indexes the position determined by the user's answer
            int current_index;
            try {
                current_index = board[answer.charAt(0) - '0' - 1][answer.charAt(1) - '0' - 1];
            } catch (Exception ArrayIndexOutOfBoundsException) {
                System.out.println("Please enter a valid coordinate\n");
                continue;
            }

            if (current_index == 0) {
                board[(int) answer.charAt(0) - '0' - 1][(int) answer.charAt(1) - '0' - 1] = current_player;
            } else {
                System.out.println("Space already occupied.\n");
                continue;
            }

            winner = CheckForDraw(board);
            if (winner != 0) {
                break;
            }
            winner = CheckBoard(board, current_player); // Checks for winner
            current_player = (current_player == 1) ? 2 : 1; // Switch player's turn

        }

        PrintBoard(board);
        if (winner != 4) { // If not draw, print winner
            System.out.println("Player " + winner + " Wins!");
        } else {
            System.out.println("Draw!");
        }

    }

    // Prints the entire board
    public static void PrintBoard(int[][] board) {

        char empty_space = '-';
        char x_space = 'X';
        char o_space = 'O';

        for (int[] i : board) {

            for (int x : i) {
                System.out.print((x == 0) ? empty_space : (x == 1) ? x_space : o_space);
                System.out.print(' ');
            }

            System.out.println();
        }
        System.out.println();

    }

    // Checks for possible win cases on the board
    public static int CheckBoard(int[][] board, int current_player) {
        int[] win_case_x = {1,1,1};
        int[] win_case_o = {2,2,2};

        int row_count = board.length;
        int column_count = board[0].length;

        // Case 1 - Matching Row
        for (int[] i: board) {
            boolean x_wins = Arrays.toString(i).equals(Arrays.toString(win_case_x));
            boolean o_wins = Arrays.toString(i).equals(Arrays.toString(win_case_o));
            if (x_wins || o_wins) {
                return  current_player;
            }
        }

        // Case 2 - Matching Column
        // Transpose Columns
        int[][] vertical_board = new int[column_count][row_count];
        for (int x = 0; x < column_count; x++) {
            for (int y = 0; y < column_count; y++) {
                vertical_board[y][x] = board[x][y];
            }
        }

        for (int[] i: vertical_board) {
            boolean x_wins = Arrays.toString(i).equals(Arrays.toString(win_case_x));
            boolean o_wins = Arrays.toString(i).equals(Arrays.toString(win_case_o));

            if (x_wins || o_wins) {
                return  current_player;
            }
        }

        // Case 3 - Matching Diagonal
        int[] transformation1 = {board[0][2], board[1][1], board[2][0]};
        int[] transformation2 = {board[0][0], board[1][1], board[2][2]};
        boolean x_wins = Arrays.toString(transformation1).equals(Arrays.toString(win_case_x)) || Arrays.toString(transformation2).equals(Arrays.toString(win_case_x));
        boolean o_wins = Arrays.toString(transformation2).equals(Arrays.toString(win_case_o)) || Arrays.toString(transformation1).equals(Arrays.toString(win_case_o));

        return (x_wins || o_wins) ? current_player : 0;
    }

    // Checks if board is fully occupied
    public static int CheckForDraw(int[][] board) {
        for (int[] x: board) {
            for (int y: x) {

                if (y == 0) {
                    return 0; // If 0 is found, winner value stays at 0
                }

            }
        }

        return 4; // If no 0's are found, winner value is set to 4 (draw ending)
    }

}