import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    /**
     * grid contains the blocks to open and perform operations
     * openSites counts how many sites are open
     * gridId refers to the element the pair row/col represents
     * sink is the virtual bottom to percolate
     */
    // creates n-by-n grid, with all sites initially blocked
    private boolean[][] grid;
    private int openSites = 0;
    private final WeightedQuickUnionUF gridId;
    private final int sink;

    public Percolation(int n) {
        gridId = new WeightedQuickUnionUF(n * n + 2);
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
//        return (row - 1) * (grid.length - 1) + col;
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
                gridId.union(currentPoint, getGridId(neighborRow, neighborCol));

        } else {
            // default
            gridId.union(0, currentPoint);
        }
        // check bottom
        neighborRow = row + 1;
        if (neighborRow < grid.length) {
            if (grid[neighborRow][neighborCol])
                gridId.union(currentPoint, getGridId(neighborRow, neighborCol));
        } else if (!percolates()) {
            // default
            gridId.union(sink, currentPoint);
        }

        // check right
        neighborRow = row;
        neighborCol = col + 1;
        if (neighborCol < grid.length)
            if (grid[neighborRow][neighborCol])
                gridId.union(currentPoint, getGridId(neighborRow, neighborCol));


        // check left
        neighborCol = col - 1;
        if (neighborCol >= 0)
            if (grid[neighborRow][neighborCol])
                gridId.union(currentPoint, getGridId(neighborRow, neighborCol));


    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row < 0 || row >= grid.length)
                || (col < 0 || col >= grid.length))
            throw new IllegalArgumentException("row/col must be between 0 and grid length");

        if (!grid[row][col]) {
            grid[row][col] = true;
            openSites += 1;
            uniteNeighbors(row, col);

        }


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row < 0 || row >= grid.length)
                || (col < 0 || col >= grid.length))
            throw new IllegalArgumentException("row/col must be between 0 and grid length");

        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row < 0 || row >= grid.length)
                || (col < 0 || col >= grid.length))
            throw new IllegalArgumentException("row/col must be between 0 and grid length");


        return gridId.find(0) == gridId.find(getGridId(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return gridId.find(0) == gridId.find(sink);
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
