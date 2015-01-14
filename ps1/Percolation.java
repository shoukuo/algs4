
public class Percolation {
    
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf1;
    private boolean[] state;
    private int rLen;
    
    public Percolation(int N) {
        if (N <= 0) 
            throw new java.lang.IllegalArgumentException();
        
        rLen = N;
        uf = new WeightedQuickUnionUF(rLen * rLen + 3);
        uf1 = new WeightedQuickUnionUF(rLen*rLen + 1);
        state = new boolean[rLen * rLen + 2];
        
        for (int i = 1; i < rLen * rLen + 1; i++)
        {
            state[i] = false;
        }
        state[0] = true;
        state[rLen*rLen+1] = true;
    }
    
    private void checkCordinate(int i, int j) {
        if (i < 1 || i > rLen || j < 1 || j > rLen)
        {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }
    
    public void open(int i, int j) {
        checkCordinate(i, j);
        
        if (i > 1)
        {
            if (isOpen(i-1, j))
            {
                uf.union(rLen*(i-2)+j, rLen*(i-1)+j);
                uf1.union(rLen*(i-2)+j, rLen*(i-1)+j);
            }
        }
        else
        {
            uf.union(0, j);
            uf1.union(0, j);
        }
        
        if (j > 1)
        {
            if (isOpen(i, j-1))
            {
                uf.union(rLen*(i-1)+j-1, rLen*(i-1)+j);
                uf1.union(rLen*(i-1)+j-1, rLen*(i-1)+j);
            }
        }
        
        if (j < rLen)
        {
            if (isOpen(i, j+1))
            {
                uf.union(rLen*(i-1)+j+1, rLen*(i-1)+j);
                uf1.union(rLen*(i-1)+j+1, rLen*(i-1)+j);
            }
        }

        if (i < rLen)
        {
            if (isOpen(i+1, j))
            {
                uf.union(rLen*i+j, rLen*(i-1)+j);
                uf1.union(rLen*i+j, rLen*(i-1)+j);
            }
        }
        else
        {
            uf.union(rLen*rLen+1, rLen*(i-1)+j);
        }
        state[rLen*(i-1) + j] = true;
    }
    
    public boolean isOpen(int i, int j) {
        checkCordinate(i, j);
        return state[rLen * (i-1) + j];
    }
    
    public boolean isFull(int i, int j) {
        checkCordinate(i, j);
        return uf1.connected(0, rLen*(i-1) + j);
    }
    
    public boolean percolates() {
        return uf.connected(0, rLen*rLen+1);
    }
    
    public static void main(String[] args) {
        Percolation per1 = new Percolation(1);
        System.out.println("" + per1.isFull(1, 1));
        
        Percolation per2 = new Percolation(3);
        System.out.println("" + per2.isFull(1, 1));
        System.out.println("hello, world!");
    }
}
