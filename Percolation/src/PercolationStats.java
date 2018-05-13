import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n;
    private double mean;
    private double stdDev;
    private double halfConlo;

    public PercolationStats(int n, int trials){
        if (n <=0 || trials <= 0){
            throw new IllegalArgumentException("invalid n");
        }
        Percolation[] percolation = new Percolation[trials];
        this.n = percolation.length;
        double [] mean  = new double[trials];
        this.n = n;
        for (int i=0;i< trials; i++) {
            percolation[i] = new Percolation(n);
            // 未连通就不停open
            while (!percolation[i].percolates()){
                int row = StdRandom.uniform(n)+1;
                int col = StdRandom.uniform(n)+1;
                percolation[i].open(row,col);
            }
            mean[i] = percolation[i].numberOfOpenSites() / (double)(n*n);
        }
        this.mean = StdStats.mean(mean);
        this.stdDev = StdStats.stddev(mean);
        this.halfConlo = (1.96*stdDev)/Math.sqrt(trials);

    }   // perform trials independent experiments on an n-by-n grid
    public double mean(){
        return  this.mean;
    }   // sample mean of percolation threshold
    public double stddev(){
        return  this.stdDev;
    }   // sample standard deviation of percolation threshold
    public double confidenceLo(){
        return mean - halfConlo;
    }   // low  endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean + halfConlo;
    }   // high endpoint of 95% confidence interval

    public static void main(String[] args){
        PercolationStats stat = new PercolationStats(200,100);
        System.out.println("mean: "+stat.mean());
        System.out.println("stddev: "+stat.stddev());
        System.out.println("lowconf: "+stat.confidenceLo());
        System.out.println("highconf: "+stat.confidenceHi());
    }      // test client (described below)
}