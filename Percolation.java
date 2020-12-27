/* *****************************************************************************
 *  Name:              Srishti
 *  Coursera User ID:  Srishti Singh
 *  Last modified:     25/12/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int virtualTop, virtualBottom, size;
    private boolean[][] grid;
    private final WeightedQuickUnionUF obj;
    private final WeightedQuickUnionUF objB;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        this.obj = new WeightedQuickUnionUF(n * n + 2);
        this.objB = new WeightedQuickUnionUF(n * n + 1);
        this.size = n;
        this.grid = new boolean[n + 1][n + 1];
        virtualBottom = n * n + 1;
        virtualTop = 0;
        openSites = 0;
    }

    // conect virtual top site
    private void virtualTopConnect(int i, int j) {
        int p = xyTo1D(i, j);
        obj.union(this.virtualTop, p);
        objB.union(this.virtualTop, p);
    }

    // connect virtual bottom
    private void virtualBottomConnect(int i, int j) {
        int p = xyTo1D(i, j);
        obj.union(this.virtualBottom, p);
    }

    // connect site to its adjacent sites (neighbours)
    private void neighbourConnect(int i, int j) {
        validate(i, j);
        int p = xyTo1D(i, j);
        if (j < this.size && isOpen(i, j + 1)) {
            obj.union(p, xyTo1D(i, j + 1));
            objB.union(p, xyTo1D(i, j + 1));
        }
        if (j > 1 && isOpen(i, j - 1)) {
            obj.union(p, xyTo1D(i, j - 1));
            objB.union(p, xyTo1D(i, j - 1));
        }
        if (i < this.size && isOpen(i + 1, j)) {
            obj.union(p, xyTo1D(i + 1, j));
            objB.union(p, xyTo1D(i + 1, j));
        }
        if (i > 1 && isOpen(i - 1, j)) {
            obj.union(p, xyTo1D(i - 1, j));
            objB.union(p, xyTo1D(i - 1, j));
        }
    }

    // are indices valid?
    private void validate(int i, int j) {
        if (i < 1 || i > this.size || j < 1 || j > this.size)
            throw new IllegalArgumentException();
    }

    // maps 2D coordinates to 1D coordinates
    private int xyTo1D(int i, int j) {
        validate(i, j);
        return (i - 1) * this.size + j;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!this.grid[row][col]) {
            this.grid[row][col] = true;
            this.openSites += 1;
        }
        if (row == 1)
            virtualTopConnect(row, col);
        if (row == this.size)
            virtualBottomConnect(row, col);
        neighbourConnect(row, col);
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return this.grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return objB.find(this.virtualTop) == objB.find(xyTo1D(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return obj.find(virtualTop) == obj.find(virtualBottom);
    }

    // test client
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            StdOut.println(i + " " + j);
        }

    }
}



