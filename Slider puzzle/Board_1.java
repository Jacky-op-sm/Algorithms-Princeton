
/**
 *
 * this should be a 1d-version of the BOARD;
 * use char[] as instance variables;
 *
 * but i am too lazy to change it to pass the memory require of the test.
 * maybe in the future i will fix it;
 *
 *
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Board_1 {

    private char[] tiles;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board_1(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new char[n * n];
        for (int i = 0, index = 0; i < n; i += 1, index += 1) {
            for (int j = 0; j < n; j += 1) {
                this.tiles[index] = Character.forDigit(tiles[i][j], 10);
            }
        }
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        //tiles
        int[][] tiles = new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] tiles2 = new int[][]{{1, 8, 3}, {4, 0, 2}, {7, 6, 5}};
        int[][] tiles3 = new int[][]{{1, 0}, {3, 2}};


        Board board3 = new Board(tiles3);
        Board board2 = new Board(tiles2);
        //StdOut.println(board.dimension());
        //StdOut.println(board.hamming());
        //StdOut.println(board.manhattan());
        //StdOut.println(board.twin());
        //StdOut.println(board.twin().equals(board2));
        for (Board b : board3.neighbors()) {
            StdOut.println(b.toString());
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (char c : tiles) {
            s.append(String.format("%2d ", c));
        }
        s.append("\n");
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n * n; i += 1) {
                if (tiles[i] == '0') continue;
                if (tiles[i] != i + 1) {
                    hamming += 1;
                }
            }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n * n; i++) {
                char c = tiles[i];
                if (c == '0') continue;
                int row = (c - 1) / n;
                int col = c - 1 - row * n;
                manhattan = manhattan + Math.abs(i - row) + Math.abs(j - col);
            }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board other = (Board) y;
        if (other.dimension() != this.dimension()) {
            return false;
        }
        return Arrays.deepEquals(tiles, other.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Deque<Board> board = new ArrayDeque<>();
        int[] rs = find(tiles, 0);
        int row = rs[0];
        int col = rs[1];

        //create neighbors BOARDS
        if (col == 0) {
            int[][] copy = makeCopy(tiles);
            swap(copy, row, col, row, col + 1);
            board.addFirst(new Board(copy));
        } else if (col == n - 1) {
            int[][] copy = makeCopy(tiles);
            swap(copy, row, col, row, col - 1);
            board.addFirst(new Board(copy));
        } else {
            int[][] copy = makeCopy(tiles);
            int[][] copy2 = makeCopy(tiles);
            swap(copy, row, col, row, col - 1);
            swap(copy2, row, col, row, col + 1);
            board.addFirst(new Board(copy));
            board.addFirst(new Board(copy2));
        }

        if (row == 0) {
            int[][] copy = makeCopy(tiles);
            swap(copy, row, col, row + 1, col);
            board.addFirst(new Board(copy));
        } else if (row == n - 1) {
            int[][] copy = makeCopy(tiles);
            swap(copy, row, col, row - 1, col);
            board.addFirst(new Board(copy));
        } else {
            int[][] copy = makeCopy(tiles);
            int[][] copy2 = makeCopy(tiles);
            swap(copy, row, col, row + 1, col);
            swap(copy2, row, col, row - 1, col);
            board.addFirst(new Board(copy));
            board.addFirst(new Board(copy2));
        }
        return board;
    }

    private int[] find(char[] tiles, int k) {
        int[] rs = new int[]{-1, -1};
        //find the row and col of the ZERO
        for (int i = 0; i < n; i++) {
            if (rs[0] >= 0) {
                break;
            }
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == k) {
                    rs[0] = i;
                    rs[1] = j;
                    break;
                }
            }
        }
        return rs;
    }

    private void swap(int[][] tiles, int row1, int col1, int row2, int col2) {
        int temp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = temp;
    }

    private int[][] makeCopy(int[][] tiles) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copy = makeCopy(tiles);
        int[] rs1 = find(tiles, 1);
        int[] rs2 = find(tiles, 2);
        swap(copy, rs1[0], rs1[1], rs2[0], rs2[1]);
        return new Board(copy);
    }

}

 */