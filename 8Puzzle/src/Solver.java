import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private Board board;
    private MinPQ<BoardWithMoves> pq = new MinPQ<>(compareBoard());
    private LinkedQueue<Board> solutionQueue = new LinkedQueue<>();
    private boolean isSolvable = false;
    private int moves = -1;
    public Solver(Board initial){
        this.board = initial;

    } // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable(){
        return isSolvable;
    }// is the initial board solvable?
    public int moves(){
        return moves;
    }// min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution(){
        return board.neighbors();
    }// sequence of boards in a shortest solution; null if unsolvable

    // 带moves的board
    private class BoardWithMoves{
        public Board board;
        public int moves;
    }
    
    private Comparator<BoardWithMoves> compareBoard() {
        class BoardComparator implements Comparator<BoardWithMoves>{
            @Override
            public int compare(BoardWithMoves bwm1, BoardWithMoves bwm2){
                int p1 = bwm1.board.manhattan() + bwm1.moves;
                int p2 = bwm2.board.manhattan() + bwm2.moves;
                if (p1>p2){
                    return 1;
                }
                else if(p1==p1){
                    return 0;
                }
                return -1;
            }
        }
        return new BoardComparator();
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
            {
                StdOut.println(board);
            }
        }
    }

// solve a slider puzzle (given below)
}