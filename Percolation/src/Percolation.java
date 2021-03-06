import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // 还是使用Quick Union，定义一维数组树
    // mmp有库
    private final int n;
    private final WeightedQuickUnionUF UF;
    private final WeightedQuickUnionUF UF_full;
    private int openSite=0;
    private boolean[] array;

    private void checkIndex(int row, int col){
        if (row <= 0 || row > n) throw new IllegalArgumentException("row index row out of bounds");
        if (col <= 0 || col > n) throw new IllegalArgumentException("row index row out of bounds");
    }

    public Percolation(int n) {
        if (n <=0){
            throw new IllegalArgumentException("invalid n");
        }

        this.n = n;
        // 0 代表上部， n*n+1 代表下部
        UF = new WeightedQuickUnionUF(n*n+2);
        UF_full = new WeightedQuickUnionUF(n*n+1);

        // 0代表not open
        array = new boolean[n*n+2];
        for (int i = 0;i < n*n+2 ;i++){
            array[i] = false;
        }
        array[0] = true;
        // array[n*n+1] = 1;
    }    // create n-by-n grid, with all sites blocked

    public void open(int row, int col){
        checkIndex(row,col);
        int up = row - 1;
        int left = col - 1;
        int right = col + 1;
        int down = row + 1;

        int index = n*(row-1)+col;

        if (up == 0){
            UF.union(0,index);
            UF_full.union(0,index);
        }
        else if (up > 0 && isOpen(up,col)){
            UF.union(n*(up-1)+col,index);
            UF_full.union(n*(up-1)+col,index);
        }

        if (left > 0 && isOpen(row,left)){
            UF.union(n*(row-1)+left,index);
            UF_full.union(n*(row-1)+left,index);
        }

        if (right <= n  && isOpen(row,right)){
            UF.union(n*(row-1)+right,index);
            UF_full.union(n*(row-1)+right,index);
        }

        if (down == n+1){
            UF.union(n*n+1,index);
        }
        else if (down <= n && isOpen(down,col)){
            UF.union(n*(down-1)+col,index);
            UF_full.union(n*(down-1)+col,index);
        }

        if (array[index] != true){
            array[index] = true;
            openSite ++;
        }


    }    // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col){
        checkIndex(row,col);
        return array[n*(row-1)+col] == true;
    }  // is site (row, col) open?

    public boolean isFull(int row, int col) {
        checkIndex(row,col);
        return UF_full.connected(0, n*(row-1)+col);
    } // is site (row, col) full?

    public int numberOfOpenSites(){
        return openSite;
    }    // number of open sites
    public boolean percolates(){
        return UF.connected(0, n*n+1);
    }// does the system percolate?

    public static void main(String[] args){
        Percolation per = new Percolation(3);
        per.open(1,2);
        per.open(2,1);
        System.out.println(per.isFull(2,1));
        per.open(2,3);
        per.open(3,2);
        System.out.println(per.percolates());
        System.out.println("num:"+per.numberOfOpenSites());
        per.open(2,2);
        System.out.println(per.isFull(3,3));
        System.out.println(per.percolates());
    }  // test client (optional)
}