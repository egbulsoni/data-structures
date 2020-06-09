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

        for (int i = 0; i < this.trials; i++) {
            Percolation perc = new Percolation(n);
            double opened = 0;
            while (!perc.percolates()) {
                int p = StdRandom.uniform(1, n + 1);
                int q = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(p, q)) {
                    perc.open(p, q);
                    opened++;
                }


            }

//            StdOut.println(s);
            thresholds[i] = opened / (n * n);
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


        StdOut.println("mean() = " + stats.mean());
        StdOut.println("stddev() = " + stats.stddev());
        StdOut.println("confidence interval = [" + stats.confidenceHi()
                + ", " + stats.confidenceLo() + "]");
//
    }
}
