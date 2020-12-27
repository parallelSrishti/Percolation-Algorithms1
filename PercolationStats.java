/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int n;
    private final int T;
    private double[] results;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        this.n = n;
        this.T = trials;
        this.results = new double[trials];
        for (int i = 0; i < this.T; i++) {
            results[i] = calculate();
        }
    }

    // calculate results
    private double calculate() {
        double op;
        Percolation perc = new Percolation(this.n);
        do {
            int r = StdRandom.uniform(1, this.n + 1);
            int c = StdRandom.uniform(1, this.n + 1);
            perc.open(r, c);
        } while (!perc.percolates());
        op = perc.numberOfOpenSites();
        return op / (this.n * this.n);

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - (1.96 * stddev() / Math.sqrt(T)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + (1.96 * stddev() / Math.sqrt(T)));
    }

    // test client
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, T);
        StdOut.println("mean =" + ps.mean());
        StdOut.println("stddev =" + ps.stddev());
        StdOut.println(
                "95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");

    }
}
