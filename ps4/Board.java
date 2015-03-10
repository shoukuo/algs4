
public class Board {
    private int[][] board;
    
    public Board(int[][] blocks) {
        int dim = blocks.length;
        this.board = new int[dim][dim];
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
                this.board[i][j] = blocks[i][j];
        }
    }
    public int dimension() {
        return this.board.length;
    }
    public int hamming() {
        int dim = this.board.length;
        int max = dim * dim;
        int count = 0;
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                int val = board[i][j];
                if ((val != (i*dim+j+1) % max) && (val != 0))
                    count += 1;               
            }
        }
        return count;    
    }
    public int manhattan() {
        int dim = this.board.length;
        int max = dim * dim;
        int count = 0;
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                int val = board[i][j];
                if ((val != (i*dim+j+1) % max) && (val != 0))
                {   
                    int pr = (val-1)/dim;
                    int pc = (val-1) % dim;
                    count += (Math.abs(pr-i))+(Math.abs(pc-j));    
                }
            }
        }
        return count;  
    }
    public boolean isGoal() {
       int dim = this.board.length;
       int max = dim * dim;
       for (int i = 0; i < dim; i++)
       {
           for (int j = 0; j < dim; j++)
               if (board[i][j] != (i*dim+j+1) % max)
                   return false;
       }
       return true;
    }
    public Board twin() {
        int dim = this.board.length;
        Board nboard = new Board(this.board);
       // indicate the row that do not have zero
        for (int i = 0; i < dim; i++) 
        {
            int val0 = nboard.board[i][0];
            int val1 = nboard.board[i][1];
            if (val0 != 0 && val1 != 0)
            {
                nboard.board[i][1] = val0;
                nboard.board[i][0] = val1;
                break;
            }
        }
        return nboard;
    }
    public boolean equals(Object y) {
        if (this == y) return true;
        if (null == y) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        int dim = this.board.length;
        int dimThat = that.board.length;
        if (dim != dimThat) return false;
        
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (this.board[i][j] != that.board[i][j]) return false;
            }
        }
        return true;
    }
    public Iterable<Board> neighbors() {
        Queue<Board> qu = new Queue<Board>();
        int dim = board.length;
        boolean flag = false;
        int i = 0, j = 0;
        for (i = 0; i < dim; i++)
        {
            for (j = 0; j < dim; j++)
                if (board[i][j] == 0) {
                    flag = true;
                    break;
                }
            if (flag) break;
        }
        //System.out.println("i = "+i+", j = "+j);
        //System.out.println("origin");
        if (i > 0) {

            Board nboard = new Board(board);
            exch(nboard.board, i, j, i-1, j);
            qu.enqueue(nboard);
        }
        if (i < dim-1) {
            Board nboard = new Board(board);
            exch(nboard.board, i, j, i+1, j);
            //System.out.println("i < dim-1");
            //StdOut.println(nboard);
            qu.enqueue(nboard);
        }
        if (j > 0) {
            Board nboard = new Board(board);
            exch(nboard.board, i, j, i, j-1);
            //System.out.println("j > 0");
            //StdOut.println(nboard);
            qu.enqueue(nboard);
        }
        if (j < dim-1) {
            Board nboard = new Board(board);
            exch(nboard.board, i, j, i, j+1);
            //System.out.println("j < dim-1");
            //StdOut.println(nboard);
            qu.enqueue(nboard);
        }
        return qu;
    }
    private void exch(int[][] blocks, int i1, int j1, int i2, int j2) {
        int tmp = blocks[i1][j1];
        blocks[i1][j1] = blocks[i2][j2];
        blocks[i2][j2] = tmp;
    }
    public String toString() {
        String str = "";
        int dim = this.board.length;
        str += dim;
        str += "\n";
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
                str += " " + this.board[i][j];
            if (i < dim-1) str += "\n";
        }
        return str;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial);
//        int ham = initial.hamming();
//        System.out.println(ham);
        for (Board board : initial.neighbors())
            StdOut.println(board);
        
    }
}
