import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] stats;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int n, int trials) {
        validate(n, trials);
        stats = new double[trials];
        for (int i = 0; i < trials; i += 1) {
            Percolation exp = new Percolation(n);
            while (!exp.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                exp.open(row, col);
            }
            double openRatio = (double) exp.numberOfOpenSites() / (n * n);
            stats[i] = openRatio;
        }

    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trial = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(n, trial);
        System.out.println("mean            = " + test.mean());
        System.out.println("stddev          = " + test.stddev());
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(stats);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(stats);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double m = mean();
        double s = stddev();
        return m - 1.96 * s / (Math.sqrt(stats.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double m = mean();
        double s = stddev();
        return m + 1.96 * s / (Math.sqrt(stats.length));
    }

    // validate that (row, col) is a valid
    private void validate(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
    }

}


