import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class BruteCollinearPoints {

    private void checkPointsHaveNullOrDup(Point[] points){
        if (points == null) { throw new IllegalArgumentException("input array is null"); }
        for (int i=0;i<points.length;i++){
            // StdOut.println(points[i]);
            if (points[i] == null){
                throw new IllegalArgumentException("contain null pointer");
            }
        }
        Point[] newPoints = Arrays.copyOf(points,points.length);
        Collections.sort(Arrays.asList(newPoints));
        for (int i=0;i<newPoints.length;i++) {
            if (i > 0 && newPoints[i].compareTo(newPoints[i - 1]) == 0) {
                throw new IllegalArgumentException("contain duplicated pointers");
            }
        }
    }

    private Point[] points;
    private ArrayList<LineSegment> ls=new ArrayList<>();

    public BruteCollinearPoints(Point[] points){
        checkPointsHaveNullOrDup(points);
        this.points = Arrays.copyOf(points,points.length);
        Collections.sort(Arrays.asList(this.points));
        Comparator<Point> comparator = this.points[0].slopeOrder();
        int start = -1;
        int end = -1;

        for (int i = 0; i < this.points.length - 3;i++){
            for (int j = i+1; j < this.points.length - 2;j++){
                for (int k = j+1; k < this.points.length - 1;k++){
                    for (int l = k+1; l < this.points.length;l++){
                        if(this.points[i].slopeTo(this.points[j])==this.points[i].slopeTo(this.points[k])
                                && this.points[i].slopeTo(this.points[j])==this.points[i].slopeTo(this.points[l])){
                            Point[] array = {this.points[i],this.points[j],this.points[k],this.points[l]};
                            // Point max = Collections.max(Arrays.asList(array));
                            // Point min = Collections.min(Arrays.asList(array));
                            LineSegment e = new LineSegment(this.points[i],this.points[l]);
                            if (i != start || l != end) {
                                ls.add(e);
                                start = i;
                                end = l;
                            }
                        }
                    }


                }
            }
        }

    }    // finds all line segments containing 4 points
    public int numberOfSegments(){
        return this.ls.size();
    }        // the number of line segments
    public LineSegment[] segments(){
        return ls.toArray(new LineSegment[ls.size()]);
    }
}
