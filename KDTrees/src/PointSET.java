import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;

public class PointSET {
    public PointSET(){

    } // construct an empty set of points
    public boolean isEmpty(){
        return true;
    } // is the set empty?
    public int size() {
        return 0;
    } // number of points in the set
    public void insert(Point2D p){

    }  // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p){
        return true;
    } // does the set contain point p?
    public void draw(){

    } // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect){
        return new Iterable<Point2D>() {
            @Override
            public Iterator<Point2D> iterator() {
                return null;
            }
        };
    } // all points that are inside the rectangle (or on the boundary)
    public Point2D nearest(Point2D p) {
        return new Point2D(0,0);
    } // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    } // unit testing of the methods (optional)
}