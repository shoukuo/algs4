
public class PointSET {
    private SET<Point2D> set;
    public PointSET()                               // construct an empty set of points 
    {
        this.set = new SET<Point2D>();
    }
    
    public boolean isEmpty()                      // is the set empty? 
    {
        return this.set.isEmpty();
    }
    
    public int size()                         // number of points in the set 
    {
        return this.set.size();
    }
    
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) throw new java.lang.NullPointerException();
        this.set.add(p);
    }
    
    public boolean contains(Point2D p)            // does the set contain point p? 
    {
        if (p == null) throw new java.lang.NullPointerException();
        return this.set.contains(p);
    }
    
    public void draw()                         // draw all points to standard draw 
    {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : this.set)
        {
            p.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
    {
        if (rect == null) throw new java.lang.NullPointerException(); 
        Queue<Point2D> qu = new Queue<Point2D>();
        for (Point2D p : this.set)
        {
            if (rect.contains(p)) qu.enqueue(p);
        }
        return qu;
    }
    
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        if (p == null) throw new java.lang.NullPointerException(); 
        if (this.set.isEmpty()) return null;
        Point2D nearP = this.set.max();
        double nearD = nearP.distanceTo(p);
        for (Point2D ptmp : this.set)
        {
            double dis = ptmp.distanceTo(p);
            if (dis < nearD)
            {
                nearP = ptmp;
                nearD = dis;
            }
        }
        return nearP;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
        PointSET dt = new PointSET();
        dt.insert(new Point2D(0.7, 0.2));
        dt.insert(new Point2D(0.5, 0.4));
        dt.insert(new Point2D(0.2, 0.3));
        dt.insert(new Point2D(0.4, 0.7));
        dt.insert(new Point2D(0.9, 0.6));
        dt.draw();
//        Point2D nearp = dt.nearest(new Point2D(0.2, 0.2));
//        System.out.println(nearp.toString());  
        RectHV rect = new RectHV(0.1, 0.1, 0.95, 0.95);
        rect.draw();
        for (Point2D p : dt.range(rect))
        {
            System.out.println(p.toString());
        }
    }
}
