package s3;


/****************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    
 *  Dependencies:
 *  Author:
 *  Date:
 *
 *  Data structure for maintaining a set of 2-D points, 
 *    including rectangle and nearest-neighbor queries
 *
 *************************************************************************/

import java.util.Arrays;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

public class PointSET {
    // construct an empty set of points
	SET<Point2D> ptset;
    public PointSET() {
    	ptset = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return ptset.isEmpty();
    }

    // number of points in the set
    public int size() {
        return ptset.size();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
    	if(ptset.contains(p))
    	{
    		return;
    	}
    	ptset.add(p);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return ptset.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
    	StdDraw.setPenRadius(0.006);
    	for (Point2D p : ptset) {
    		p.draw();
    	}
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
    	SET<Point2D> ptinset = new SET<Point2D>();
    	for(Point2D p : ptset){
    		if((Double.compare((rect.xmin()),(p.x())) <= 0 && Double.compare(rect.xmax(), p.x()) >= 0) 
    			&& (Double.compare(rect.ymin(), p.y()) <= 0 && Double.compare(rect.ymax(), p.y()) >= 0))
    		{
    			ptinset.add(p);
    		}
    	}
        return ptinset;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
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
    }

    public static void main(String[] args) {
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
        PointSET set = new PointSET();
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
        if (set.contains(new Point2D(0.500000,1.000000)))
		{
			StdOut.println("bang");
		}
        set.draw();
        out.println();
    }

}
