package s3;
/*************************************************************************
 *************************************************************************/

import java.util.Arrays;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdDraw;

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
		
		public Node(Point2D p, boolean a)
		{
			this.point = p;
			this.axis = a;
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
    		root = new Node(p, VERTICAL);
    		size++;
    	}
    	else
    		insert(root, p);
    	
    };
    
    private void insert(Node n, Point2D p)
    {
    	if(n.axis == VERTICAL)
    	{
    		double cmp = Double.compare(p.x(), n.point.x());
    		if (cmp < 0) // ef p < 0
			{
				if(n.left == null){
					n.left = new Node(p, HORIZONTAL);
					size++;
				}
				else insert(n.left, p);
			}
    		else
			{
    			if(n.right == null){
    				n.right = new Node(p, HORIZONTAL);
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
					n.left = new Node(p, VERTICAL);
				}
				else insert(n.left, p);
			}
    		else
			{
    			if(n.right == null){
    				size++;
    				n.right = new Node(p, VERTICAL);
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
    	if(n.axis == VERTICAL)
    	{
    		double cmp = Double.compare(p.x(), n.point.x());
    		if (cmp < 0) // ef p < 0
			{
				if(n.left == null) return false;
				else return contains(n.left, p);
				
			}
    		else if(cmp > 0)
			{
    			if(n.right == null) return false;
				else return contains (n.right, p);
			}
    		else if(n.point.equals(p))
    			return true;
    		else return false;
    	}
    	else
    	{
    		double cmp = Double.compare(p.y(), n.point.y());
    		if (cmp < 0) // ef p < 0
			{
				if(n.left == null) return false;
				else return contains(n.left, p);
			}
    		else if(cmp > 0)
			{
    			if(n.right == null) return false;
				else return contains(n.right, p);
			}
    		else if(n.point.equals(p))
    			return true;
    		else return false;
    	}
    }

    // draw all of the points to standard draw
    public void draw() {
    	double max_x = 1;
    	double min_x = 0;
    	double max_y = 1;
    	double min_y = 0;
    	draw(root, max_x, min_x, max_y, min_y, VERTICAL);
    	
    }
    
    private void draw(Node n, double max_x, double min_x, double max_y, double min_y, boolean axis)
    {
    	if(axis = VERTICAL)
    	{
    		StdDraw.setPenColor(StdDraw.BLACK);
    		n.point.draw();
    		StdDraw.setPenColor(StdDraw.RED);
    		n.point.drawTo(new Point2D(max_x, n.point.y()));
    		n.point.drawTo(new Point2D(min_x, n.point.x()));
    		draw(n.left, n.point.x(), min_x, max_y, min_y, !axis);
    		draw(n.right, max_x, n.point.x(), max_y, min_y, !axis);
    	}
    	else
    	{
    		StdDraw.setPenColor(StdDraw.BLACK);
    		n.point.draw();
    		StdDraw.setPenColor(StdDraw.BLUE);
    		n.point.drawTo(new Point2D(n.point.x(), max_y));
    		n.point.drawTo(new Point2D(n.point.x(), min_y));
    		draw(n.left, max_x, min_x, n.point.y(), min_y, !axis);
    		draw(n.right, max_x, n.point.x(), max_y, n.point.x(), !axis);
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
    	if(n == null)
    	{
    		return null;
    	}
    	else if((Double.compare((rect.xmin()),(n.point.x())) <= 0 && Double.compare(rect.xmax(), n.point.x()) >= 0) 
    			&& (Double.compare(rect.ymin(), n.point.y()) <= 0 && Double.compare(rect.ymax(), n.point.y()) >= 0))
    		{
			ptset.add(n.point);
    		}
		else if(n.axis == VERTICAL)
		{
			if(Double.compare(n.left.point.x(), rect.xmax()) <= 0 && Double.compare(n.left.point.x(), rect.xmin()) >= 0 )
			{
				range(rect, n.left, ptset);
			}
			else if(Double.compare(n.right.point.x(), rect.xmax()) <= 0 && Double.compare(n.right.point.x(), rect.xmin()) >= 0 )
			{
				range(rect, n.right, ptset);
			}
		}
		else if(n.axis == HORIZONTAL)
		{
			if(Double.compare(n.left.point.y(), rect.ymax()) <= 0 && Double.compare(n.left.point.y(), rect.ymin()) >= 0 )
			{
				range(rect, n.left, ptset);
			}
			else if(Double.compare(n.right.point.y(), rect.ymax()) <= 0 && Double.compare(n.right.point.y(), rect.ymin()) >= 0 )
			{
				range(rect, n.right, ptset);
			}
		}
    	return ptset;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        return p;
    }

    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {
        In in = new In();
        Out out = new Out();
        int nrOfRecangles = in.readInt();
        int nrOfPointsCont = in.readInt();
        int nrOfPointsNear = in.readInt();
        RectHV[] rectangles = new RectHV[nrOfRecangles];
        Point2D[] pointsCont = new Point2D[nrOfPointsCont];
        Point2D[] pointsNear = new Point2D[nrOfPointsNear];
        for (int i = 0; i < nrOfRecangles; i++) {
            rectangles[i] = new RectHV(in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsCont; i++) {
            pointsCont[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsNear; i++) {
            pointsNear[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        KdTree set = new KdTree();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble(), y = in.readDouble();
            set.insert(new Point2D(x, y));
        }
        for (int i = 0; i < nrOfRecangles; i++) {
            // Query on rectangle i, sort the result, and print
            Iterable<Point2D> ptset = set.range(rectangles[i]);
            int ptcount = 0;
            for (Point2D p : ptset)
                ptcount++;
            Point2D[] ptarr = new Point2D[ptcount];
            int j = 0;
            for (Point2D p : ptset) {
                ptarr[j] = p;
                j++;
            }
            Arrays.sort(ptarr);
            out.println("Inside rectangle " + (i + 1) + ":");
            for (j = 0; j < ptcount; j++)
                out.println(ptarr[j]);
        }
        out.println("Contain test:");
        for (int i = 0; i < nrOfPointsCont; i++) {
            out.println((i + 1) + ": " + set.contains(pointsCont[i]));
        }

        out.println("Nearest test:");
        for (int i = 0; i < nrOfPointsNear; i++) {
            out.println((i + 1) + ": " + set.nearest(pointsNear[i]));
        }

        out.println();
    }
}
