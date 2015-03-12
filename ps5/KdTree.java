
public class KdTree {
    private class BNode {
        private int level;
        private Point2D key;
        private RectHV rect;
        private BNode lchild, rchild;
        public BNode(Point2D k, int l, RectHV r)
        {
            key = k;
            level = l;
            lchild = null;
            rchild = null;
            rect = r;
        }
        public RectHV lsubRect()
        {
            return new RectHV(rect.xmin(), rect.ymin(), key.x(), rect.ymax());
        }
        public RectHV rsubRect()
        {
            return new RectHV(key.x(), rect.ymin(), rect.xmax(), rect.ymax());
        }
        public RectHV usubRect()
        {
            return new RectHV(rect.xmin(), key.y(), rect.xmax(), rect.ymax());
        }
        public RectHV dsubRect()
        {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), key.y());
        }
    }
    private class Segment {
        private Point2D start, end;
        private int flag;
        public Segment(Point2D p1, Point2D p2)
        {
            start = p1;
            end = p2;
            if (start.x() == end.x())   flag = 1;  // vertical
            else                        flag = 2;  //horizontal    
        }
        public String toString()
        {
            String s = start.toString() + " -> " + end.toString();
            return s;
        }
    }
    private class Segments {
        private Queue<Segment> qu;
        public Segments()
        {
            qu = new Queue<Segment>();
        }
        public void insert(Segment sg)
        {
            qu.enqueue(sg);
        }
        public Segment horizontalS(Point2D p)
        {
            double lx = 0, rx = 1;
            double curlDis = p.x() - lx;
            double currDis = rx - p.x();
            for (Segment sg : qu)
            {
                if (sg.flag == 1 && sg.start.y() < p.y() && sg.end.y() > p.y())
                {
                    if (sg.start.x() <= p.x())
                    {
                        double dis = p.x() - sg.start.x();
                        if (dis < curlDis)
                        {
                            lx = sg.start.x();
                            curlDis = dis;
                        }
                    } 
                    if (sg.start.x() > p.x())
                    {
                        double dis = sg.start.x() - p.x();
                        if (dis < currDis)
                        {
                            rx = sg.start.x();
                            currDis = dis;
                        }
                    }                    
                }
            }
            return new Segment(new Point2D(lx, p.y()), new Point2D(rx, p.y()));
        }
        public Segment verticalS(Point2D p)
        {
            double dy = 0, uy = 1;
            double curuDis = uy - p.y();
            double curdDis = p.y() - dy;
            for (Segment sg : qu)
            {
                if (sg.flag == 2 && sg.start.x() < p.x() && sg.end.x() > p.x())
                {
                    if (sg.start.y() <= p.y())
                    {
                        double dis = p.y() - sg.start.y();
                        if (dis < curdDis)
                        {
                            dy = sg.start.y();
                            curdDis = dis;
                        }
                    }  
                    if (sg.start.y() > p.y())
                    {
                        double dis = sg.start.y() - p.y();
                        if (dis < curuDis)
                        {
                            uy = sg.start.y();
                            curuDis = dis;
                        }
                    }                    
                }
            }
            return new Segment(new Point2D(p.x(), dy), new Point2D(p.x(), uy));           
        }
    }
    
    private int size;
    private BNode root;
    private Segments sgs;
    private Point2D nearp;
    private double neard;
    private int visited;
    public KdTree()                               // construct an empty set of points 
    {
         this.root = null;
         this.size = 0;
         this.sgs = new Segments();
    }
    
    public boolean isEmpty()                      // is the set empty? 
    {
        return size == 0;
    }
    
    public int size()                         // number of points in the set 
    {
        return this.size;
    }
    
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        this.root = put(null, this.root, p, 0, 0);
    }
    
    private BNode put(BNode pnode, BNode node, Point2D p, int l, int r)
    {
        if (node == null)
        {
            this.size += 1;
            RectHV rect = null;
            if (r == 0)      rect = new RectHV(0, 0, 1, 1);
            else if (r == 1) rect = pnode.lsubRect();
            else if (r == 2) rect = pnode.rsubRect();
            else if (r == 3) rect = pnode.dsubRect();
            else if (r == 4) rect = pnode.usubRect();
            return new BNode(p, l, rect);
        }
        if (node.level % 2 == 0)
        {
            if (p.x() < node.key.x())       node.lchild = put(node, node.lchild, p, l+1, 1);
            else if (p.x() > node.key.x())  node.rchild = put(node, node.rchild, p, l+1, 2);
            else if (p.y() != node.key.y()) node.rchild = put(node, node.rchild, p, l+1, 2);
        }
        else
        {
            if (p.y() < node.key.y())       node.lchild = put(node, node.lchild, p, l+1, 3);
            else if (p.y() > node.key.y())  node.rchild = put(node, node.rchild, p, l+1, 4);
            else if (p.x() != node.key.x()) node.rchild = put(node, node.rchild, p, l+1, 4);
        }
        return node;
    }
    
    public boolean contains(Point2D p)            // does the set contain point p? 
    {
        if (this.root == null) return false;
        BNode cur = this.root;
        while (cur != null)
        {
            if (cur.key.equals(p)) return true;
            if (cur.level % 2 == 0)
            {
                if (p.x() >= cur.key.x()) cur = cur.rchild;
                else cur = cur.lchild;
            }
            else
            {
                if (p.y() >= cur.key.y()) cur = cur.rchild;
                else cur = cur.lchild;   
            }
        }
        return false;
    }
    
    public void draw()                         // draw all points to standard draw 
    {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        sgs = new Segments();
        draw(this.root);
    }
    
    private void draw(BNode node)
    {
        if (node == null) return;
        StdDraw.setPenColor();
        StdDraw.setPenRadius(0.01);
        node.key.draw();
        Segment s;
        if (node.level % 2 == 0)
        {
            s = sgs.verticalS(node.key);
            System.out.println(s.toString());
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.005);
            s.start.drawTo(s.end);
        }
        else
        {
            s = sgs.horizontalS(node.key);
            System.out.println(s.toString());
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.005);
            s.start.drawTo(s.end);    
        }
        sgs.insert(s);
        //System.out.println(node.key.toString());
        draw(node.lchild);
        draw(node.rchild);
    }

    
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
    {
        Queue<Point2D> qu = new Queue<Point2D>();
        range(root, rect, qu);
        return qu;
    }
    
    private void range(BNode node, RectHV rect, Queue<Point2D> qu)
    {
        if (node == null) return;
        if (rect.intersects(node.rect))
        {
            if (rect.contains(node.key)) qu.enqueue(node.key);
            range(node.rchild, rect, qu);
            range(node.lchild, rect, qu);
        }    
    }
    
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        if (this.root == null) return null;
        this.nearp = root.key;
        this.neard = 2;
        this.visited = 0;
        nearest(root,  p);
        return nearp;
    }
    
    private void nearest(BNode node, Point2D p)
    {
        if (node == null) return;
        if (node.rect.distanceTo(p) <= this.neard)
        {
            this.visited += 1;
            if (node.key.distanceTo(p) < this.neard)
            {
                this.nearp = node.key;
                this.neard = node.key.distanceTo(p);
            }
            if (node.level % 2 == 0)
            {
                if (p.x() < node.key.x())
                {
                    nearest(node.lchild, p);
                    nearest(node.rchild, p);  
                }
                else 
                {
                    nearest(node.rchild, p);
                    nearest(node.lchild, p);                    
                }
            }
            else  // node.level % 2 == 1
            {
                if (p.y() < node.key.y())
                {
                    nearest(node.lchild, p);
                    nearest(node.rchild, p);                   
                }
                else
                {
                    nearest(node.rchild, p);
                    nearest(node.lchild, p);                           
                }
            }
        }
    }
    
    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
        KdTree dt = new KdTree();
//        for (int i = 1; i < 1000; i++)
//        {
//            double x = StdRandom.uniform();
//            double y = StdRandom.uniform();
//            dt.insert(new Point2D(x, y));
//            if (dt.size() != i) System.out.println("" + "dt.size() = " + dt.size() + "i = " + i);
//        }
        dt.insert(new Point2D(0.7, 0.2));
        dt.insert(new Point2D(0.5, 0.4));
        dt.insert(new Point2D(0.2, 0.3));
        dt.insert(new Point2D(0.4, 0.7));
        dt.insert(new Point2D(0.5, 0.7));
        dt.insert(new Point2D(0.7, 0.7));
        dt.insert(new Point2D(0.8, 0.7));
//        dt.draw();
//        System.out.println(dt.size());
//        Point2D nearp = dt.nearest(new Point2D(0.1, 0.5));
//        System.out.println(nearp.toString());
//        RectHV rect = new RectHV(0.4, 0.1, 0.95, 0.95);
//        rect.draw();
//        for(Point2D p : dt.range(rect))
//        {
//            System.out.println(p.toString());
//        }
    }
}
