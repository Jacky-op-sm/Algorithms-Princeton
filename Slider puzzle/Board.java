import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class Board {

    private int[][] tiles;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i += 1) {
            for (int j = 0; j < n; j += 1) {
                this.tiles[i][j] = tiles[i][j];
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
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != n * i + j + 1) {
                    hamming += 1;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tar = tiles[i][j];
                if (tar == 0) continue;
                int row = (tar - 1) / n;
                int col = tar - 1 - row * n;
                manhattan = manhattan + Math.abs(i - row) + Math.abs(j - col);
            }
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

        if (!out_of_bound(row + 1, col)) {
            int[][] copy = makeCopy(tiles);
            swap(copy, row, col, row + 1, col);
            board.addFirst(new Board(copy));
        }
        if (!out_of_bound(row - 1, col)) {
            int[][] copy = makeCopy(tiles);
            swap(copy, row, col, row - 1, col);
            board.addFirst(new Board(copy));
        }
        if (!out_of_bound(row, col + 1)) {
            int[][] copy = makeCopy(tiles);
            swap(copy, row, col, row, col + 1);
            board.addFirst(new Board(copy));
        }
        if (!out_of_bound(row, col - 1)) {
            int[][] copy = makeCopy(tiles);
            swap(copy, row, col, row, col - 1);
            board.addFirst(new Board(copy));
        }
        return board;

        /**
         * bad version;
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
         */
    }

    boolean out_of_bound(int row, int col) {
        return row < 0 || col < 0 || row >= tiles.length || col >= tiles[0].length;
    }

    private int[] find(int[][] tiles, int k) {
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