import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Deque;

public class Solver {

    private final MinPQ<SearchNode> pq;
    private final MinPQ<SearchNode> pq_twin;
    private SearchNode SN;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        pq = new MinPQ<>();
        pq_twin = new MinPQ<>();

        Board twin = initial.twin();
        int priority = initial.manhattan();
        int priority_twin = twin.manhattan();

        SearchNode sn = new SearchNode(null, 0, initial, priority);
        SearchNode sn_twin = new SearchNode(null, 0, twin, priority_twin);

        pq.insert(sn);
        pq_twin.insert(sn_twin);

        SN = pq.delMin();
        SearchNode SN_twin = pq_twin.delMin();
        while (!SN.board.isGoal() && !SN_twin.board.isGoal()) {
            //SearchNode SN_twin = pq_twin.delMin();
            for (Board b : SN.board.neighbors()) {
                if (SN.previous == null || !b.equals(SN.previous.board)) {
                    priority = b.manhattan();
                    pq.insert(new SearchNode(SN, SN.move + 1, b, priority + SN.move + 1 ));
                }
            }
            SN = pq.delMin();

            for (Board b : SN_twin.board.neighbors()) {
                if (SN_twin.previous == null || !b.equals(SN_twin.previous.board)) {
                    priority_twin = b.manhattan();
                    pq_twin.insert(new SearchNode(SN_twin, SN_twin.move + 1, b, priority_twin + SN_twin.move + 1));
                }
            }
            SN_twin = pq_twin.delMin();
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return SN.board.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return SN.move;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Deque<Board> boards = new ArrayDeque<>();
        SearchNode ns = SN;
        while (ns.previous != null) {
            boards.addFirst(ns.board);
            ns = ns.previous;
        }
        boards.addFirst(ns.board);
        return boards;
    }

    private class SearchNode implements Comparable<SearchNode> {

        SearchNode previous;
        int move;
        Board board;
        int priority;

        SearchNode(SearchNode previous, int move, Board board, int priority) {
            this.previous = previous;
            this.move = move;
            this.board = board;
            this.priority = priority;
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.priority - o.priority;
        }

        /**
         *
         public Comparator<SearchNode> HammingPriority() {
         return new hp();
         }
         private class hp implements Comparator<SearchNode> {

        @Override public int compare(SearchNode o1, SearchNode o2) {
        int hpn = o1.board.hamming() + o1.move;
        int hpn2 = o2.board.hamming() + o1.move;
        return hpn - hpn2;
        }
        }

         public Comparator<SearchNode> ManhattanPriority() {
         return new mp();
         }

         private class mp implements Comparator<SearchNode> {
        @Override public int compare(SearchNode o1, SearchNode o2) {
        int hpn = o1.board.manhattan() + o1.move;
        int hpn2 = o2.board.manhattan() + o1.move;
        return hpn - hpn2;
        }
        }
         */
    }

}