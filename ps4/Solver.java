import java.util.Comparator;

public class Solver {
    
    private class SNode {
        private Board board;
        private int moves;
        private SNode preSNode;
        SNode(Board b, SNode pre, int m) {
            board = b;
            moves = m;
            preSNode = pre;
        }
    }
    
    private Board ini, iniTwin;
    private Stack<SNode> st1, st2;
    private boolean solvable;
    private int moves;
    
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null)
            throw new java.lang.NullPointerException();
        this.ini = initial;
        this.iniTwin = initial.twin();
        st1 = new Stack<SNode>();
        st2 = new Stack<SNode>();
        moves = 0;
        solvable = false;
        puzzle();
    }
    
    private void puzzle()
    {
        MinPQ<SNode> mpq1 = new MinPQ<SNode>(new PqOrder());
        MinPQ<SNode> mpq2 = new MinPQ<SNode>(new PqOrder());
        mpq1.insert(new SNode(ini, null, 0));
        mpq2.insert(new SNode(iniTwin, null, 0));
        
        while (!mpq1.isEmpty() && !mpq2.isEmpty())
        {
//            System.out.println("hello");
            SNode cur1 = mpq1.delMin();
            st1.push(cur1);
            if (cur1.board.isGoal()) {
                solvable = true;
                moves = cur1.moves;
                break;
            }
            SNode cur2 = mpq2.delMin();
            st2.push(cur2);
            if (cur2.board.isGoal()) {
                solvable = false;
                break;
            }
            for (Board bd : cur1.board.neighbors())
            {
                if (cur1.preSNode == null || !bd.equals(cur1.preSNode.board))
                    mpq1.insert(new SNode(bd, cur1, cur1.moves+1));
            }
            for (Board bd : cur2.board.neighbors())
            {
                if (cur2.preSNode == null || !bd.equals(cur2.preSNode.board))
                    mpq2.insert(new SNode(bd, cur2, cur2.moves+1)); 
            }
        }
    }
    private class PqOrder implements Comparator<SNode> {
        public int compare(SNode b1, SNode b2) {
            int val1 = b1.board.manhattan() + b1.moves;
            int val2 = b2.board.manhattan() + b2.moves;
            if (val1 > val2) return 1;
            else if (val1 < val2) return -1;
            else return 0;
        }
    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return this.solvable;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (this.solvable)
            return moves;
        return -1;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (!this.solvable)
            return null;
        Stack<Board> solst = new Stack<Board>();
        SNode start = st1.peek();
        while (start != null)
        {
            solst.push(start.board);
            start = start.preSNode;
        }
        return solst;
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
       
        // solve the puzzle
        Solver solver = new Solver(initial);

        StdOut.println(solver.moves());
        StdOut.println(solver.solution());
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
