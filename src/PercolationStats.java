import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double sum = 0;
    private int trials = 0;
    private final double[] xs;

    //    private Percolation percolation;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        xs = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int attempt = 0;
            while (!percolation.percolates()) {
                int k = StdRandom.uniform(n) + 1;
                int m = StdRandom.uniform(n) + 1;
                if (!percolation.isOpen(k, m)) {
                    attempt += 1;
                    percolation.open(k, m);
                }
            }
            xs[i] = attempt / (double) percolation.totalSize();
            sum += attempt / (double) percolation.totalSize();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return sum / this.trials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double ret = 0;
        for (int i = 0; i < this.trials; i++) {
            ret += Math.pow(this.xs[i] - this.mean(), 2);
        }
        return Math.sqrt(ret / (this.trials - 1));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - 1.96 * this.stddev() / Math.sqrt(this.trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(this.trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(200, 100);
        System.out.println(percolationStats.mean());
        System.out.println(percolationStats.stddev());
        System.out.println(percolationStats.confidenceLo());
        System.out.println(percolationStats.confidenceHi());
    }
}