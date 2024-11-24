import java.util.Scanner;

public class SudokuSolver {
    public static final int GRID_SIZE = 9;

    public static void main(String[] args) {
        int[][] board = new int[GRID_SIZE][GRID_SIZE];
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Sudoku grid row by row (use 0 for empty cells):");

        // Input validation for the Sudoku grid
        for (int i = 0; i < GRID_SIZE; i++) {
            while (true) { // Loop until valid input is provided
                System.out.println("Enter row " + (i + 1) + " (exactly 9 digits separated by spaces):");
                String rowInput = scanner.nextLine();
                String[] numbers = rowInput.trim().split("\\s+"); // Split input by spaces

                if (numbers.length != GRID_SIZE) {
                    System.out.println("Invalid input! Please enter exactly 9 numbers.");
                    continue;
                }

                boolean valid = true;
                for (int j = 0; j < GRID_SIZE; j++) {
                    try {
                        board[i][j] = Integer.parseInt(numbers[j]);
                        if (board[i][j] < 0 || board[i][j] > 9) {
                            System.out.println("Numbers must be between 0 and 9.");
                            valid = false;
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please enter only numbers.");
                        valid = false;
                        break;
                    }
                }

                if (valid) {
                    break; // Exit loop if input is valid
                }
            }
        }

        System.out.println("Input board:");
        printBoard(board);

        if (solveBoard(board)) {
            System.out.println("Solved board:");
            printBoard(board);
        } else {
            System.out.println("No solution exists.");
        }

        scanner.close();
    }

    // Prints the Sudoku board in a readable format
    public static void printBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    // Recursive function to solve the Sudoku board using Backtracking
    public static boolean solveBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col] == 0) { // Empty cell found
                    for (int num = 1; num <= GRID_SIZE; num++) { // Try numbers 1-9
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num; // Place number

                            if (solveBoard(board)) { // Recursive call
                                return true;
                            }

                            board[row][col] = 0; // Backtrack
                        }
                    }
                    return false; // No number fits
                }
            }
        }
        return true; // Solved
    }

    // Checks if placing a number in a specific cell is valid
    public static boolean isValid(int[][] board, int row, int col, int num) {
        // Check if the number is already in the row
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        // Check if the number is already in the column
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // Check if the number is already in the 3x3 subgrid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true; // Placement is valid
    }
}

