import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double MULTI = 1.96;

    private final int trials;
    private final double[] xs;

    //    private Percolation percolation;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
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
            xs[i] = attempt / ((double) n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(xs);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(xs);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - MULTI * this.stddev() / Math.sqrt(this.trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + MULTI * this.stddev() / Math.sqrt(this.trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
//        int n = 4;
//        int trials = 1000;
        try {
            PercolationStats percolationStats = new PercolationStats(n, trials);
            System.out.println(String.format("%-20s= %f", "mean", percolationStats.mean()));
            System.out.println(String.format("%-20s= %f", "stddev", percolationStats.stddev()));
            System.out.println(String.format("%-20s= %f, %f", "95% confidence interval", percolationStats.confidenceLo(), percolationStats.confidenceHi()));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
