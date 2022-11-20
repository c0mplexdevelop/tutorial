package cc102.game;

public class Board {
    String[][] board;
    int winningRow = 0;
    int winningCol = 0;
    int winningDiag = 0;

    public Board(String[][] board) {
        this.board = board;
    }

    public boolean checkIfValid(int row, int col) {
        if (this.board[row][col] != " ") {
            return false;
        }

        return true;
    }

}
