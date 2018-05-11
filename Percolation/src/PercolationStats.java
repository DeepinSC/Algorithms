import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private Percolation[] percolation;
    private int n;
    private double mean;
    private double stdDev;

    public PercolationStats(int n, int trials){
        if (n <=0 || trials <= 0){
            throw new IllegalArgumentException("invalid n");
        }
        percolation = new Percolation[trials];
        this.n = n;
        for (int i=0;i< trials; i++) {
            percolation[i] = new Percolation(n);
            // 未连通就不停open
            while (!percolation[i].percolates()){
                int row = StdRandom.uniform(n)+1;
                int col = StdRandom.uniform(n)+1;
                // System.out.println(row+','+col);
                percolation[i].open(row,col);
            }
        }
        this.mean = mean();
        this.stdDev = stddev();

    }   // perform trials independent experiments on an n-by-n grid
    public double mean(){
        double [] mean  = new double[percolation.length];
        for (int i=0;i<percolation.length;i++){
            double percent = percolation[i].numberOfOpenSites() / (double)(n*n);
            mean[i] = percent;
        }
        return  StdStats.mean(mean);
    }   // sample mean of percolation threshold
    public double stddev(){
        double [] stdDev  = new double[percolation.length];
        for (int i=0;i<percolation.length;i++){
            double percent = percolation[i].numberOfOpenSites() / (double)(n*n);
            stdDev[i] = percent;
        }
        return StdStats.stddev(stdDev);
    }   // sample standard deviation of percolation threshold
    public double confidenceLo(){
        return mean - (1.96*stdDev)/Math.sqrt(percolation.length);
    }   // low  endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean + (1.96*stdDev)/Math.sqrt(percolation.length);
    }   // high endpoint of 95% confidence interval

    public static void main(String[] args){
        PercolationStats stat = new PercolationStats(200,100);
        System.out.println("mean: "+stat.mean());
        System.out.println("stddev: "+stat.stddev());
        System.out.println("lowconf: "+stat.confidenceLo());
        System.out.println("highconf: "+stat.confidenceHi());
    }      // test client (described below)
}