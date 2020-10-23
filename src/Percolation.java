import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    // n*n ---> virtual top
    // n*n+1 ---> virtual bottom
    private int openSites = 0;
    private final int size;
    private final WeightedQuickUnionUF sites;
    private final boolean[] openedSites;
    private final int virtualTop;
    private final int virtualBottom;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = n;
        this.sites = new WeightedQuickUnionUF(n * n + 2);
        this.openedSites = new boolean[n * n];
        this.virtualTop = n * n;
        this.virtualBottom = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.size || col > this.size) {
            throw new IllegalArgumentException();
        }
        if (!isOpen(row, col)) {
            this.openSites += 1;
            this.openedSites[locaXY2Index(row, col)] = true;
            if (row == 1) {
                this.sites.union(locaXY2Index(row, col), this.virtualTop);
            }
            if (row == this.size) {
                this.sites.union(locaXY2Index(row, col), this.virtualBottom);
            }
        }
        // connected to neighbors
        if (inBorder(row-1, col) && this.isOpen(row-1, col)) {
            this.sites.union(locaXY2Index(row-1, col), locaXY2Index(row, col));
        }
        if (inBorder(row, col-1) && this.isOpen(row, col-1)) {
            this.sites.union(locaXY2Index(row, col-1), locaXY2Index(row, col));
        }
        if (inBorder(row+1, col) && this.isOpen(row+1, col)) {
            this.sites.union(locaXY2Index(row+1, col), locaXY2Index(row, col));
        }
        if (inBorder(row, col+1) && this.isOpen(row, col+1)) {
            this.sites.union(locaXY2Index(row, col+1), locaXY2Index(row, col));
        }
    }

    private boolean inBorder(int row, int col) {
        return (col % (this.size + 1)) * (row % (this.size + 1)) != 0;
    }

    private int locaXY2Index(int row, int col) {
        return this.size * (row-1) + (col-1);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.size || col > this.size) {
            throw new IllegalArgumentException();
        }
        return this.openedSites[locaXY2Index(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.size || col > this.size) {
            throw new IllegalArgumentException();
        }
        return this.sites.find(locaXY2Index(row, col)) == this.sites.find(this.virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.sites.find(this.virtualTop) == this.sites.find(this.virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        try {
            Percolation system = new Percolation(4);
            System.out.println("open site Row 2 Col 2");
            system.open(2, 2);
            System.out.println("Opened Sites: " + system.numberOfOpenSites());

            if (system.isFull(2, 2)) {
                System.out.println("(error) Row 2 Col 2 is full site");
            } else {
                System.out.println("(expected) Row 2 Col 2 is not full site");
            }

            System.out.println("open site Row 1 Col 2");
            system.open(1, 2);
            System.out.println("Opened Sites: " + system.numberOfOpenSites());

            if (system.isFull(2, 2)) {
                System.out.println("(expected) Row 2 Col 2 is full site");
            } else {
                System.out.println("(error) Row 2 Col 2 is not full site");
            }

            System.out.println("open site Row 4 Col 3");
            system.open(4, 3);
            System.out.println("Opened Sites: " + system.numberOfOpenSites());

            if (system.isFull(4, 3)) {
                System.out.println("(error) Row 4 Col 3 is full site");
            } else {
                System.out.println("(expected) Row 4 Col 3 is not full site");
            }
            System.out.println("open site Row 3 Col 2");
            system.open(3, 2);
            System.out.println("Opened Sites: " + system.numberOfOpenSites());

            if (system.isFull(3, 2)) {
                System.out.println("(expected) Row 3 Col 2 is full site");
            } else {
                System.out.println("(error) Row 3 Col 2 is not full site");
            }

            System.out.println("open site Row 4 Col 2");
            system.open(4, 2);
            System.out.println("Opened Sites: " + system.numberOfOpenSites());

            if (system.isFull(4, 2)) {
                System.out.println("(expected) Row 4 Col 2 is full site");
            } else {
                System.out.println("(error) Row 4 Col 2 is not full site");
            }

            System.out.println("does system percolate?");
            if (system.percolates()) {
                System.out.println("(expected) Yes");
            } else {
                System.out.println("(error) No");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
