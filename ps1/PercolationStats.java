
public class PercolationStats {

    private double[] X;
    private int rLen;
    private int times;
    
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
        {
            throw new java.lang.IllegalArgumentException();
        }
        rLen = N;
        times = T;
        
        X = new double[times];

        for (int t = 0; t < times; t++)
        {
            Percolation per = new Percolation(rLen);
            double sum = 0;
            while (!per.percolates())
            {
                int i = StdRandom.uniform(1, rLen+1);
                int j = StdRandom.uniform(1, rLen+1);
                if (!per.isOpen(i, j))
                {
                    sum += 1;
                    per.open(i, j);
                }
            }
            X[t] = sum / (rLen*rLen);
        }
    }
    
    public double mean() {
        return StdStats.mean(X);
    }
    
    public double stddev() {
        return StdStats.stddev(X);
    }
    
    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(times);
    }
    
    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(times);
    }
    
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);
        System.out.println("mean\t= " + ps.mean());
        System.out.println("stddev\t= " + ps.stddev());
        System.out.println("95% confidence interval = "+ps.confidenceLo()+", "+ps.confidenceHi());
    }
}
