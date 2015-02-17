
public class Fast {

    public static void main(String[] args) {
        In infile = new In(args[0]);
        int num = Integer.parseInt(infile.readLine());      
        int[] tmp = infile.readAllInts();
        
        Point[] points = new Point[num];
        Point[] backup = new Point[num];
        
        for (int i = 0; i < num; i++)
        {
            backup[i] = new Point(tmp[2*i], tmp[2*i+1]);
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            backup[i].draw();
        }
        
        for (int i = 0; i < num; i++)
        {
            for (int m = 0; m < num; m++)
                points[m] = backup[m];
            Point tp = points[i];
            points[i] = points[0];
            points[0] = tp;
            //System.out.println("origin = " + i);
            sort(points, 1, num-1);
            Point oriPoint = points[0];
            int j = 1;
            while (j < num)
            {
                Point startPoint = points[j];
                double slope = oriPoint.slopeTo(startPoint);
                //System.out.println("start j = " + j + ", startslope = " + slope);
                int count = 1;
                while (++j < num)
                {
                    double stmp = oriPoint.slopeTo(points[j]);
                    //System.out.println("seq j = " + j + ", stmpslope = " + stmp);
                    if (Double.compare(slope, stmp) == 0)
                    {
                        count += 1;
                        //System.out.println("count = " + count);
                    }
                    else
                        break;
                }
                if (count >= 3)
                {
                    String s = "";
                    Point[] ps = new Point[count+1];
                    ps[0] = oriPoint;
                    for (int w = 0; w < count; w++)
                    {
                        ps[w+1] = points[j-count+w];
                    }
                    Quick3way.sort(ps);
                    if (ps[0] == oriPoint) 
                    {
                        s += ps[0].toString();
                        for (int v = 1; v < count+1; v++)
                        {
                            s += " -> "; 
                            s += ps[v];
                        }
                        StdDraw.setXscale(0, 32768);
                        StdDraw.setYscale(0, 32768);
                        System.out.println(s);
                        ps[0].drawTo(ps[count]);                        
                    }

                }
            }
        } //for      
    }
    
    private static void exch(Point[] points, int i, int j)
    {
        Point tmp = points[i];
        points[i] = points[j];
        points[j] = tmp;
    }
    
    private static  void sort(Point[] points, int lo, int hi)
    {
        if (lo >= hi) return;
        int lt = lo, gt = hi;
        int i = lo+1;
        Point v = points[lo];
        while (i <= gt)
        {
            int cmp = points[0].SLOPE_ORDER.compare(points[i], v);
            if (cmp > 0) exch(points, i, gt--);
            else if (cmp < 0) exch(points, i++, lt++);
            else i++;
        }
        sort(points, lo, lt-1);
        sort(points, gt+1, hi);
    }
}
