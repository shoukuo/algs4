
public class Brute {

    public static void main(String[] args) {
        In infile = new In(args[0]);
        int num = Integer.parseInt(infile.readLine());
        Point[] points = new Point[num];
        int[] tmp = infile.readAllInts();
        for (int i = 0; i < num; i++)
        {
            points[i] = new Point(tmp[2*i], tmp[2*i+1]);
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            points[i].draw();
        }
        Point[] ps = new Point[4];
        for (int i = 0; i < num; i++)
            for (int j = i+1; j < num; j++)
                for (int k = j+1; k < num; k++)
                    for (int w = k+1; w < num; w++)
                    {
                        Point p1 = points[i];
                        Point p2 = points[j];
                        Point p3 = points[k];
                        Point p4 = points[w];
                        double slope1 = p1.slopeTo(p2);
                        double slope2 = p1.slopeTo(p3);
                        double slope3 = p1.slopeTo(p4);
                        //System.out.println("" + slope1 + slope2 + slope3);
                        if (Double.compare(slope1, slope2) == 0 && Double.compare(slope1, slope3) == 0)
                        {
                            ps[0] = p1;
                            ps[1] = p2;
                            ps[2] = p3;
                            ps[3] = p4;
                            Insertion.sort(ps);
                            String s = "" + ps[0].toString() + " -> " + ps[1].toString() + " -> " + ps[2].toString() + " -> " + ps[3].toString();
                            System.out.println(s);
                            StdDraw.setXscale(0, 32768);
                            StdDraw.setYscale(0, 32768);
                            ps[0].drawTo(ps[3]);
                        }
                    }
    }
}
