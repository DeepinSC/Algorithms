import java.util.ArrayList;

public class BruteCollinearPoints {

    private void checkPointsHaveNull(Point[] points){
        if (points == null) { throw new IllegalArgumentException("input array is null"); }
        for (int i=0;i<points.length;i++){
            if (points[i] == null){
                throw new IllegalArgumentException("contain null pointer");
            }
        }
    }

    private Point[] points;

    public BruteCollinearPoints(Point[] points){
        checkPointsHaveNull(points);
        this.points = points;

    }    // finds all line segments containing 4 points
    public int numberOfSegments(){
        return segments().length;
    }        // the number of line segments
    public LineSegment[] segments(){
        ArrayList<LineSegment> arrayList = new ArrayList<>();

        for (int i = 0; i < points.length - 3;i++){
            for (int j = i+1; j < points.length - 2;j++){
                for (int k = j+1; k < points.length - 1;k++){
                    for (int l = k+1; l < points.length;l++){
                        if(points[i].slopeTo(points[j])==points[i].slopeTo(points[k])
                                && points[i].slopeTo(points[j])==points[i].slopeTo(points[l])){
                            arrayList.add(new LineSegment(points[i],points[l]));
                        }
                    }
                }
            }
        }
        LineSegment[] lineSegments = new LineSegment[arrayList.size()];
        for (int i=0;i<arrayList.size();i++){
            lineSegments[i] = arrayList.get(i);
        }
        return lineSegments;
    }
}
