import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private int n;
    private int[][] tiles;
    private MinPQ<Board> pq;

    public Board(int[][] blocks){
        this.n = blocks.length;
        tiles = new int[n][n];
        for (int i=0;i<blocks.length;i++){
            for(int j=0;j<blocks[0].length;j++){
                tiles[i][j] = blocks[i][j];
            }
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
                    int rowDis = Math.abs(i - tiles[i][j]/n);
                    int colDis = Math.abs(j - tiles[i][j]%n);
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

        int pre_i = 0;
        int pre_j = 0;
        for (int i=1;i<n;i++){
            for(int j=0;j<n;j++) {
                if (result.tiles[pre_i][pre_j] == 0){
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
        return pq;
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
            StdOut.println(initial);
            StdOut.println(initial.hamming());
            StdOut.println(initial.manhattan());
            StdOut.println(initial.twin());
        }
    }// unit tests (not graded)
}