import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class FastCollinearPoints {

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
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points){
        checkPointsHaveNullOrDup(points);
        this.points = Arrays.copyOf(points,points.length);
        Collections.sort(Arrays.asList(this.points));
        ArrayList<LineSegment> arrayList = new ArrayList<>();
        ArrayList<Double> slopes = new ArrayList<>();
        ArrayList<Point> endPoints = new ArrayList<>();
        ArrayList<String> lineStrSet = new ArrayList<>();
        for (int i = 0; i < this.points.length - 3;i++){
            Point p0 = this.points[i];
            Point[] remainP = Arrays.copyOfRange(this.points,i+1,this.points.length);
            Comparator<Point> comparator = p0.slopeOrder();
            Arrays.sort(remainP,comparator);
            // nlogn
            // 将元素寄存在外面
            double slope = 0;
            int count = 2;
            for(int j = 0; j < remainP.length;j++){
                Point p1 = remainP[j];
                double j_slope = p0.slopeTo(p1);
                if (j==0){
                    slope = j_slope;
                    continue;
                }
                if (slope == j_slope){
                    count ++;
                    continue;
                }
                // 中间终止了
                if (slope != j_slope && count >= 4){
                    Point endPoint = remainP[j-1];
                    String lineStr = endPoint.toString()+','+slope;
                    // StdOut.println(lineStr);
                    if (!lineStrSet.contains(lineStr)) {
                        arrayList.add(new LineSegment(p0, endPoint));
                        lineStrSet.add(lineStr);
                    }
                    count = 2;
                    slope = p0.slopeTo(remainP[j]);
                }
                // 结束了，count<4
                else{
                    count = 2;
                    slope = p0.slopeTo(remainP[j]);
                }
            }
            // 循环结束了
            if (count >= 4){
                Point endPoint = remainP[remainP.length - 1];
                String lineStr = endPoint.toString()+','+slope;
                // StdOut.println(lineStr);
                if (!endPoints.contains(endPoint) || !slopes.contains(slope)) {
                    if (!lineStrSet.contains(lineStr)) {
                        arrayList.add(new LineSegment(p0, endPoint));
                        lineStrSet.add(lineStr);
                    }
                }
            }

        }

        LineSegment[] lineSegments = new LineSegment[arrayList.size()];
        for (int i=0;i<arrayList.size();i++){
            lineSegments[i] = arrayList.get(i);
        }
        this.segments = lineSegments;
    }     // finds all line segments containing 4 or more points
    public int numberOfSegments(){
        return this.segments.length;
    } // the number of line segments

    public LineSegment[] segments(){
        return this.segments;
    }
}
