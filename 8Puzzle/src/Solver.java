import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private Board board;
    private Stack<Board> solutionQueue;
    private boolean isSolvable = false;
    private int moves = -1;
    public Solver(Board initial){
        if (initial==null){
            throw new IllegalArgumentException("Input cannot be null");
        }
        this.board = initial;
        solutionQueue = this.findSolution(board);

    } // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable(){
        return isSolvable;
    }// is the initial board solvable?

    public int moves(){
        return moves;
    }// min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution(){
        if (this.isSolvable){
            return this.solutionQueue;
        }
        else{
            return null;
        }
    }// sequence of boards in a shortest solution; null if unsolvable

    private Stack<Board> findSolution(Board board){
        Board twin = board.twin();
        Stack<Board> solutionQueue = new Stack<>();
        MinPQ<BoardWithMoves> pq = new MinPQ<>(compareBoard());
        moves = 0;
        if(board.isGoal()){
            solutionQueue.push(board);
            this.isSolvable = true;
            return solutionQueue;
        }
        if(twin.isGoal()){
            this.isSolvable = false;
            return new Stack<>();
        }
        BoardWithMoves init = new BoardWithMoves(board,null,0,false);
        BoardWithMoves initTwin = new BoardWithMoves(twin,null,0,true);
        pq.insert(init);
        pq.insert(initTwin);

        while (true) {
            BoardWithMoves curBwm = pq.delMin();
            // StdOut.println(curBwm.board);
            if (curBwm.board.isGoal()){
                if(curBwm.isTwin){
                    this.moves = -1;
                    this.isSolvable = false;
                    return new Stack<>();
                }
                this.moves = curBwm.moves;
                this.isSolvable = true;
                while(curBwm.parent!=null){
                    solutionQueue.push(curBwm.board);
                    curBwm = curBwm.parent;
                }
                solutionQueue.push(init.board);
                break;
            }
            else{
                for(Board n:curBwm.board.neighbors()){
                    if(curBwm.parent==null){
                        pq.insert(new BoardWithMoves(n,curBwm,curBwm.moves+1,curBwm.isTwin));
                        continue;
                    }
                    if(!n.equals(curBwm.parent.board)){
                        pq.insert(new BoardWithMoves(n,curBwm,curBwm.moves+1,curBwm.isTwin));
                    }
                }
            }
        }
        return solutionQueue;
    }

    private class BoardWithMoves{
        public Board board;
        public BoardWithMoves parent;
        public int moves;
        public int priority;
        public boolean isTwin;
        BoardWithMoves(Board board,BoardWithMoves parentBoard, int moves, boolean isTwin){
            this.board = board;
            this.moves = moves;
            this.parent = parentBoard;
            this.isTwin =isTwin;
            this.priority = board.manhattan()+moves;
        }
    } // 带moves的board

    private Comparator<BoardWithMoves> compareBoard() {
        class BoardComparator implements Comparator<BoardWithMoves>{
            @Override
            public int compare(BoardWithMoves bwm1, BoardWithMoves bwm2){
                int p1 = bwm1.priority;
                int p2 = bwm2.priority;
                if (p1>p2){
                    return 1;
                }
                else if(p1==p2){
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