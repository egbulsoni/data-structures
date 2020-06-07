import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private static final double AMOUNT = 1.96;
    private final double[] thresholds;
    private final int trials;


    //    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        this.trials = trials;

        thresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int opened = 0;
            while (!perc.percolates()) {
                int p = StdRandom.uniform(0, n);
                int q = StdRandom.uniform(0, n);
                if (!perc.isOpen(p, q)) {
                    perc.open(p, q);
                    opened++;
                }


            }
            double s = (double) opened / (double) n * n;
//            StdOut.println(s);
            thresholds[i] = s;
        }


    }

    //    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }


    //    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    //
    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (AMOUNT * stddev()) / Math.sqrt(this.trials);
    }

    //
//    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (AMOUNT * stddev()) / Math.sqrt(this.trials);
    }

    //
//    // test client (see below)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println("mean = " + stats.mean());
        StdOut.println("std deviation = " + stats.stddev());
        StdOut.println("95% confidence hi = " + stats.confidenceHi());
        StdOut.println("95% confidence lo = " + stats.confidenceLo());


        System.out.println("Type the size of the grid: ");
        System.out.println("How many trials? ");


    }
}
