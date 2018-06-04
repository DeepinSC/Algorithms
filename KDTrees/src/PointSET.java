import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.SET;

import java.util.Iterator;

public class PointSET {
    private SET<Point2D> set;
    public PointSET(){
        set  = new SET<>();
    } // construct an empty set of points
    public boolean isEmpty(){
        return set.isEmpty();
    } // is the set empty?
    public int size() {
        return set.size();
    } // number of points in the set
    public void insert(Point2D p){
        if (p==null){
            throw new IllegalArgumentException("point cannot be null");
        }
        set.add(p);
    }  // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p){
        return set.contains(p);
    } // does the set contain point p?
    public void draw(){
        for(Point2D e:set){
            e.draw();
        }
    } // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect){
        SET<Point2D> rangeSet = new SET<>();
        for(Point2D e:set){
            if(rect.contains(e)){
                rangeSet.add(e);
            }
        }
        return rangeSet;
    } // all points that are inside the rectangle (or on the boundary)
    public Point2D nearest(Point2D p) {
        if (p==null){
            throw new IllegalArgumentException("point cannot be null");
        }
        if (set.isEmpty()){
            return null;
        }
        double min_dis = -1;
        Point2D min_p = new Point2D(-1,-1);
        for(Point2D e:set){
            double dis = p.distanceTo(e);
            if (min_dis == -1 || dis < min_dis){
                min_dis = dis;
                min_p = e;
            }
        }
        return min_p;
    } // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
        StdOut.println(brute.set.size());
        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();
        StdOut.println(brute.nearest(new Point2D(0.81, 0.30)));
        StdDraw.show();

        StdDraw.enableDoubleBuffering();
        while (true) {

            // user starts to drag a rectangle
            if (StdDraw.isMousePressed() && !isDragging) {
                x0 = x1 = StdDraw.mouseX();
                y0 = y1 = StdDraw.mouseY();
                isDragging = true;
            }

            // user is dragging a rectangle
            else if (StdDraw.isMousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
            }

            // user stops dragging rectangle
            else if (!StdDraw.isMousePressed() && isDragging) {
                isDragging = false;
            }

            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw the rectangle
            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                    Math.max(x0, x1), Math.max(y0, y1));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : brute.range(rect))
            {
                p.draw();
            }

            StdDraw.show();
            StdDraw.pause(20);
        }
    } // unit testing of the methods (optional)
}