import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    //TODOS
//    LINT THE
//    FUCK OUT
//    OF THIS
//    SHIT,
//    HANDLE ERRORS
//    CORRECTLY,
//    FIX BACKWASH
//    PROBLEM!

    // creates n-by-n grid, with all sites initially blocked
    private boolean[][] grid;
    //    private boolean percolated = false;
    private int openSites = 0;
    private final WeightedQuickUnionUF gridid;
    private final int sink;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        gridid = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n][n];
        sink = getGridId(n - 1, n - 1) + 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }

    }

    private int getGridId(int row, int col) {
        return row * grid.length + col + 1;
//        return (row - 1) * grid.length + col;
    }


    private void uniteNeighbors(int row, int col) {

        int neighborRow;
        int neighborCol;
        int currentPoint = getGridId(row, col);

        neighborRow = row - 1;
        neighborCol = col;
        // check top
        if (neighborRow >= 0) {
            if (grid[neighborRow][neighborCol])
                gridid.union(currentPoint, getGridId(neighborRow, neighborCol));

        } else {
            // default

            gridid.union(0, currentPoint);
        }
        // check bottom
        neighborRow = row + 1;
        if (neighborRow < grid.length) {
            if (grid[neighborRow][neighborCol])
                gridid.union(currentPoint, getGridId(neighborRow, neighborCol));
        } else if (!percolates()) {
            // default
            gridid.union(sink, currentPoint);
        }

        // check right
        neighborRow = row;
        neighborCol = col + 1;
        if (neighborCol < grid.length)
            if (grid[neighborRow][neighborCol])
                gridid.union(currentPoint, getGridId(neighborRow, neighborCol));


        // check left
        neighborCol = col - 1;
        if (neighborCol >= 0)
            if (grid[neighborRow][neighborCol])
                gridid.union(currentPoint, getGridId(neighborRow, neighborCol));


    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        StdOut.println(row + " " + col);
        try {
            if ((row >= 0 && row < grid.length) && (col >= 0 && col < grid.length))
                if (!grid[row][col]) {
                    grid[row][col] = true;
                    openSites += 1;
                    uniteNeighbors(row, col);
                    StdOut.println("row = " + row + " col = " + col);

                }
        } catch (IllegalArgumentException ex) {
//            StdOut.println(ex.getMessage());
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        try {
            return grid[row][col];
        } catch (IllegalArgumentException ex) {
//            StdOut.println(ex.getMessage());

        }

        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        try {
            return gridid.find(0) == gridid.find(getGridId(row, col));
        } catch (IllegalArgumentException ex) {
//            StdOut.println(ex.getMessage());
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return gridid.find(0) == gridid.find(sink);
    }


//    public static void main(String[] args) {
//
//        Percolation perc = new Percolation(14);
//        perc.open(14, 0);
//        perc.open(-1, -1);
//        perc.open(3, 3);
//        perc.open(4, 3);
//        perc.open(4, 6);
//
//    }
}
