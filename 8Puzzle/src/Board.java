public class Board {
    public Board(int[][] blocks){

    } // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public int dimension(){

    } // board dimension n
    public int hamming(){

    }// number of blocks out of place
    public int manhattan(){

    }// sum of Manhattan distances between blocks and goal
    public boolean isGoal(){

    }// is this board the goal board?
    public Board twin(){

    }// a board that is obtained by exchanging any pair of blocks
    @Override
    public boolean equals(Object y){

    }// does this board equal y?
    public Iterable<Board> neighbors(){

    }// all neighboring boards
    @Override
    public String toString(){

    }// string representation of this board (in the output format specified below)

    public static void main(String[] args){

    }// unit tests (not graded)
}