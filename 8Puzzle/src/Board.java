import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    private int n;
    private int[][] tiles;


    public Board(int[][] blocks){
        this.n = blocks.length;
        this.tiles = new int[n][];
        for (int i=0; i<n; i++){
            this.tiles[i] = Arrays.copyOf(blocks[i], n);
        }
    } // construct a board from an n-by-n array of blocks
      // (where blocks[i][j] = block in row i, column j)

    public int dimension(){
        return this.tiles.length;
    } // board dimension n
    public int hamming(){
        int count = 0;
        for (int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if (tiles[i][j] == 0){
                    continue;
                }
                if(tiles[i][j] != i*n+j+1){
                    count++;
                }
            }
        }
        return count;
    } // number of blocks out of place
    public int manhattan(){
        int count = 0;
        for (int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if (tiles[i][j] == 0){
                    continue;
                }
                if(tiles[i][j] != i*n+j+1){
                    int rowDis = Math.abs(i - (tiles[i][j]-1)/n);
                    int colDis = Math.abs(j - (tiles[i][j]-1)%n);
                    //StdOut.println(tiles[i][j]+","+i+","+j);
                    //StdOut.println(rowDis+" "+colDis);
                    count+= rowDis + colDis;
                }
            }
        }
        return count;
    }// sum of Manhattan distances between blocks and goal
    public boolean isGoal(){
        return manhattan()==0;
    }// is this board the goal board?
    public Board twin(){
        Board result = new Board(this.tiles);

        int pre_i = -1;
        int pre_j = -1;
        for (int i=0;i<n;i++){
            for(int j=0;j<n;j++) {
                if (result.tiles[i][j]==0){
                    continue;
                }
                if (pre_i == -1 && result.tiles[i][j] != 0){
                    pre_i = i;
                    pre_j = j;
                    continue;
                }
                int temp = result.tiles[pre_i][pre_j];
                result.tiles[pre_i][pre_j] = result.tiles[i][j];
                result.tiles[i][j] = temp;
                return result;
            }
        }
        return result;
    } // a board that is obtained by exchanging any pair of blocks
    @Override
    public boolean equals(Object y){
        if (y == this) { return true; }
        if (y == null) { return false; }
        if (y.getClass() != this.getClass()) { return false; }
        Board that = (Board) y;
        if(this.n!=that.n){
            return false;
        }
        else{
            if(this.tiles[0].length!=that.tiles[0].length){
                return false;
            }
        }
        for (int i=0;i<n;i++){
            for(int j=0;j<n;j++) {
                if(this.tiles[i][j]!=that.tiles[i][j]){
                    return false;
                }
            }
        }
        return true;
    } // does this board equal y?
    public Iterable<Board> neighbors(){
        // todo：改成普通队列，用0元素和周围的进行交换。
        LinkedQueue<Board> queue = new LinkedQueue<>();
        int zero_i = -1;
        int zero_j = -1;

        // find zero
        for (int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if (tiles[i][j] == 0) {
                    zero_i = i;
                    zero_j = j;
                    break;
                }
            }
            if (zero_i!=-1){
                break;
            }
        }

        // 如果上边可换
        if (zero_i > 0){
            Board board_up = new Board(this.tiles);
            int up = board_up.tiles[zero_i-1][zero_j];
            board_up.tiles[zero_i-1][zero_j] = 0;
            board_up.tiles[zero_i][zero_j] = up;
            queue.enqueue(board_up);
        }

        // 如果下面可换
        if (zero_i < n-1){
            Board board_bottom = new Board(this.tiles);
            int bottom = board_bottom.tiles[zero_i+1][zero_j];
            board_bottom.tiles[zero_i+1][zero_j] = 0;
            board_bottom.tiles[zero_i][zero_j] = bottom;
            queue.enqueue(board_bottom);
        }

        // 如果左面可换
        if (zero_j > 0){
            Board board_left = new Board(this.tiles);
            int left = board_left.tiles[zero_i][zero_j-1];
            board_left.tiles[zero_i][zero_j-1] = 0;
            board_left.tiles[zero_i][zero_j] = left;
            queue.enqueue(board_left);
        }

        // 如果右面可换
        if (zero_j < n-1){
            Board board_right = new Board(this.tiles);
            int right = board_right.tiles[zero_i][zero_j+1];
            board_right.tiles[zero_i][zero_j+1] = 0;
            board_right.tiles[zero_i][zero_j] = right;
            queue.enqueue(board_right);
        }

        return queue;
    }// all neighboring boards
    @Override
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
    }// string representation of this board (in the output format specified below)

    public static void main(String[] args){
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            StdOut.println(initial.isGoal());
            StdOut.println(initial.hamming());
            StdOut.println("manhattan: "+initial.manhattan());
            StdOut.println(initial.twin());
            StdOut.println(initial);
            Iterable<Board> iter = initial.neighbors();
            for (Board e:iter){
                //StdOut.println("manhattan: "+e.manhattan());
                // StdOut.println(e);
            }
        }
    }// unit tests (not graded)
}