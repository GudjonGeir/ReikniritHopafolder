package s3;
/*************************************************************************
 *************************************************************************/

import java.util.Arrays;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

public class KdTree {
	private static final boolean HORIZONTAL   = true;
    private static final boolean VERTICAL = false;
    
    private Node root;
    private int size;
    
	
	private class Node
	{
		private Point2D point;
		private Node left, right;
		private boolean axis;
		private RectHV rect;
		
		public Node(Point2D p, boolean a, RectHV rect)
		{
			this.point = p;
			this.axis = a;
			this.rect = rect;
		}
	}
	
	// construct an empty set of points
    public KdTree() {
    	root = null;
    	size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
    	if(root == null) 
    	{
    		RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
    		root = new Node(p, VERTICAL, rect);
    		size++;
    	}
    	else
    		insert(root, p);
    	
    };
    
    private void insert(Node n, Point2D p)
    {
    	if(n.point.equals(p))
    	{
    		return;
    	}
    	if(n.axis == VERTICAL)
    	{
    		double cmp = Double.compare(p.x(), n.point.x());
    		if (cmp < 0) // ef p < 0
			{
				if(n.left == null){
					RectHV rect = new RectHV(n.rect.xmin(), n.rect.ymin(), n.point.x(), n.rect.ymax() );
					n.left = new Node(p, HORIZONTAL, rect);		
					size++;
				}
				else insert(n.left, p);
			}
    		else
			{
    			if(n.right == null){
    				RectHV rect = new RectHV(n.point.x(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax() );
    				n.right = new Node(p, HORIZONTAL, rect);
    				size++;
    			}
				else insert(n.right, p);
			}
    	}
    	else
    	{
    		double cmp = Double.compare(p.y(), n.point.y());
    		if (cmp < 0) // ef p < 0
			{
				if(n.left == null){
					size++;
					RectHV rect = new RectHV(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.point.y());
					n.left = new Node(p, VERTICAL, rect);
					
				}
				else insert(n.left, p);
			}
    		else
			{
    			if(n.right == null){
    				size++;
    				RectHV rect = new RectHV(n.rect.xmin(), n.point.y(), n.rect.xmax(), n.rect.ymax());
    				n.right = new Node(p, VERTICAL, rect);
	
    			}
				else insert(n.right, p);
			}
    	}
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
    	if(root == null) return false;
        if(root.point.equals(p)) return true;
        return contains(root, p);
    }
    
    private boolean contains(Node n, Point2D p)
    {
    	if(n.point.equals(p))
			return true;
    	if(n.axis == VERTICAL)
    	{
    		double cmp = Double.compare(p.x(), n.point.x());
    		if (cmp < 0) // ef p < 0
			{
				if(n.left == null) return false;
				else return contains(n.left, p);
				
			}
    		else
			{
    			if(n.right == null) return false;
				else return contains (n.right, p);
			}
    		
    	}
    	else
    	{
    		double cmp = Double.compare(p.y(), n.point.y());
    		if (cmp < 0) // ef p < 0
			{
				if(n.left == null) return false;
				else return contains(n.left, p);
			}
    		else
			{
    			if(n.right == null) return false;
				else return contains(n.right, p);
			}

    	}
    }

    // draw all of the points to standard draw
    public void draw() {
    	StdDraw.setPenRadius(0.002);
    	StdDraw.line(0, 1, 1, 1);
    	StdDraw.line(0, 0, 1, 0);
    	StdDraw.line(0, 1, 0, 0);
    	StdDraw.line(1, 0, 1, 1);
    	draw(root);
    	
    	
    }
    
    private void draw(Node n)
    {
    	if(n.axis == VERTICAL)
    	{
    		StdDraw.setPenColor(StdDraw.RED);
    		StdDraw.setPenRadius(0.004);
    		StdDraw.line(n.point.x(), n.rect.ymax(), n.point.x(), n.rect.ymin());
    		StdDraw.setPenColor(StdDraw.BLACK);
    		StdDraw.setPenRadius(0.01);
    		n.point.draw();
    		if(n.left != null) draw(n.left);
    		if(n.right != null) draw(n.right);
    	}
    	else
    	{
    		StdDraw.setPenColor(StdDraw.BLUE);
    		StdDraw.setPenRadius(0.004);
    		StdDraw.line(n.rect.xmax(), n.point.y(), n.rect.xmin(), n.point.y());
    		StdDraw.setPenColor(StdDraw.BLACK);
    		StdDraw.setPenRadius(0.01);
    		n.point.draw();
    		if(n.left != null) draw(n.left);
    		if(n.right != null) draw(n.right);
    	}
    	
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
    	SET<Point2D> ptset = new SET<Point2D>();
        ptset = range(rect, root, ptset);
        return ptset;
    }
    
    private SET<Point2D> range(RectHV rect, Node n, SET<Point2D> ptset)
    {
    	if(n == null || !rect.intersects(n.rect))
    	{
    		return ptset;
    	}
    	
    	double xmincomp = Double.compare(n.point.x(), rect.xmin());
    	double xmaxcomp = Double.compare(rect.xmax(), n.point.x());
    	double ymincomp = Double.compare(n.point.y(), rect.ymin());
    	double ymaxcomp = Double.compare(rect.ymax(), n.point.y());
    	
    	if (xmincomp >= 0 && xmaxcomp >= 0 && ymincomp >= 0 && ymaxcomp >= 0) 
    		ptset.add(n.point);

    	
    	if (n.axis == VERTICAL)
    	{
    		if(xmincomp >= 0)
    			ptset = range(rect, n.left, ptset);
    		if(xmaxcomp >= 0)
    			ptset = range(rect, n.right, ptset);
    	}
    	else
    	{
    		if(ymincomp >= 0)
    			ptset = range(rect, n.left, ptset);
    		if(ymaxcomp >= 0)
    			ptset = range(rect, n.right, ptset);
    	}
    	return ptset;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
    	Point2D close = new Point2D(9,9);
        close = nearest(p, root, close);
        return close;
    }
    
    private Point2D nearest(Point2D p, Node n, Point2D close){
    	if(n == null){
    		return close;
    	}
    	else{
    		if(p.distanceSquaredTo(n.point) <= p.distanceSquaredTo(close))
    		{
    			close = n.point;
    		}
    		if(n.axis == VERTICAL){
    			double cmp = Double.compare(p.x(), n.point.x());
        		if (cmp < 0) // ef p < 0
    			{
    				if(n.left == null) return close;
    				else return nearest(p, n.left, close);
    				
    			}
        		else
    			{
        			if(n.right == null) return close;
    				else return nearest(p, n.right, close);
    			}
    		}
    		else{
    			if(p.distanceSquaredTo(n.point) <= p.distanceSquaredTo(close))
    			{
    				close = n.point;
    			}
    			double cmp = Double.compare(p.y(), n.point.y());
        		if (cmp < 0) // ef p < 0
    			{
    				if(n.left == null) return close;
    				else return nearest(p, n.left, close);
    				
    			}
        		else
    			{
        			if(n.right == null) return close;
    				else return nearest(p, n.right, close);
    			}
    		}
    	}
    }
    /*public Point2D nearest(Point2D p) {
    	if(ptset.isEmpty())
    	{
    		return null;
    	}
    	Point2D close = new Point2D(9,9);
    	for(Point2D iter : ptset){
    		if(p.distanceSquaredTo(iter) < p.distanceSquaredTo(close))
    		{
    			close = iter;
    		}
    	}
        return close;
    }*/


    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {
    	Point2D pt1 = new Point2D(0.897,0.422);
    	Point2D pt2 = new Point2D(0.798, 0.429);
    	Point2D pt3 = new Point2D(0.9, 0.448);
    	
    	StdOut.println(pt1.distanceSquaredTo(pt2));
    	StdOut.println(pt1.distanceSquaredTo(pt3));
    	//(0.897, 0.422): (0.798, 0.429)
    	//(0.897, 0.422): (0.9, 0.448)
        In in = new In();
        Out out = new Out();
//        int nrOfRecangles = in.readInt();
//        int nrOfPointsCont = in.readInt();
//        int nrOfPointsNear = in.readInt();
//        RectHV[] rectangles = new RectHV[nrOfRecangles];
//        Point2D[] pointsCont = new Point2D[nrOfPointsCont];
//        Point2D[] pointsNear = new Point2D[nrOfPointsNear];
//        for (int i = 0; i < nrOfRecangles; i++) {
//            rectangles[i] = new RectHV(in.readDouble(), in.readDouble(),
//                    in.readDouble(), in.readDouble());
//        }
//        for (int i = 0; i < nrOfPointsCont; i++) {
//            pointsCont[i] = new Point2D(in.readDouble(), in.readDouble());
//        }
//        for (int i = 0; i < nrOfPointsNear; i++) {
//            pointsNear[i] = new Point2D(in.readDouble(), in.readDouble());
//        }
        KdTree set = new KdTree();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble(), y = in.readDouble();
            set.insert(new Point2D(x, y));
        }
//        for (int i = 0; i < nrOfRecangles; i++) {
//            // Query on rectangle i, sort the result, and print
//            Iterable<Point2D> ptset = set.range(rectangles[i]);
//            int ptcount = 0;
//            for (Point2D p : ptset)
//                ptcount++;
//            Point2D[] ptarr = new Point2D[ptcount];
//            int j = 0;
//            for (Point2D p : ptset) {
//                ptarr[j] = p;
//                j++;
//            }
//            Arrays.sort(ptarr);
//            out.println("Inside rectangle " + (i + 1) + ":");
//            for (j = 0; j < ptcount; j++)
//                out.println(ptarr[j]);
//        }
//        out.println("Contain test:");
//        for (int i = 0; i < nrOfPointsCont; i++) {
//            out.println((i + 1) + ": " + set.contains(pointsCont[i]));
//        }
//
//        out.println("Nearest test:");
//        for (int i = 0; i < nrOfPointsNear; i++) {
//            out.println((i + 1) + ": " + set.nearest(pointsNear[i]));
//        }
        set.draw();

        out.println();
    }
}
