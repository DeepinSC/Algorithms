
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.SET;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class KdTree {

    private TreeSet<Point2D> treeSet;
    private Node root;
    private int size;

    private static class Node {
        private Point2D p;
        // the point

        private RectHV rect;
        // the axis-aligned rectangle corresponding to this node
        // todo：这个空间跟父节点有关，比如在父节点的左侧，那么他存在的空间就在父节点分割区域左边。

        private boolean isVertical;

        private Node lb=null;
        // the left/bottom subtree

        private Node rt=null;
        // the right/top subtree

        Node(Point2D p, boolean isVertical){
            this.p = p;
            this.isVertical = isVertical;
        }
    }

    public KdTree(){
        root = null;
        size = 0;

    } // construct an empty set of points
    public boolean isEmpty(){
        return root == null;
    } // is the set empty?
    public int size() {
        return size;
    } // number of points in the set

    public boolean contains(Point2D p){
        return get(root,p)!=null;
    } // does the set contain point p?

    private Node get(Node x, Point2D key){
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (x == null) {
            return null;
        }

        if (x.p.equals(key)){
            return x;
        }

        int cmp;
        if (x.isVertical){
            cmp = Point2D.X_ORDER.compare(x.p,key);
        }
        else{
            cmp = Point2D.Y_ORDER.compare(x.p,key);
        }

        if (cmp > 0){
            return get(x.lb, key);
        }
        else{
            return get(x.rt, key);
        }
    }

    public void insert(Point2D p){
        if (p == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (get(root,p)!=null) {
            return;
        }
        root = put(root,p,true, null);
    }  // add the point to the set (if it is not already in the set)


    private Node put(Node x, Point2D key, boolean isVertical, Node parent){
        if (x == null){
            Node node = new Node(key,isVertical);
            RectHV rectHV;
            if (parent==null){
                rectHV = new RectHV(0,0,1,1);
            }
            else if(isVertical){
                if (key.y()<parent.p.y()){
                    rectHV = new RectHV(parent.rect.xmin(),parent.rect.ymin(),parent.rect.xmax(),parent.p.y());
                }
                else{
                    rectHV = new RectHV(parent.rect.xmin(),parent.p.y(),parent.rect.xmax(),parent.rect.ymax());
                }

            }
            else{
                if (key.x()<parent.p.x()){
                    rectHV = new RectHV(parent.rect.xmin(),parent.rect.ymin(),parent.p.x(),parent.rect.ymax());
                }
                else{
                    rectHV = new RectHV(parent.p.x(),parent.rect.ymin(),parent.rect.xmax(),parent.rect.ymax());
                }
            }
            node.rect = rectHV;
            size++;
            return node;
        }
        int cmp;
        if (x.isVertical){
            cmp = Point2D.X_ORDER.compare(x.p,key);
        }
        else{
            cmp = Point2D.Y_ORDER.compare(x.p,key);
        }

        if (cmp > 0){
            /* StdOut.println("---");
            StdOut.println(cmp);
            StdOut.println(key);
            StdOut.println(x.p);
            StdOut.println(x.isVertical);
            StdOut.println("---"); */
            x.lb = put(x.lb,key,!isVertical,x);
        }
        else {
            /* StdOut.println("---");
            StdOut.println(cmp);
            StdOut.println(key);
            StdOut.println(x.p);
            StdOut.println(x.isVertical);
            StdOut.println("---"); */
            x.rt = put(x.rt,key,!isVertical,x);
        }
        return x;
    }

    private void drawNode(Node node){
        if (node==null){
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        Point2D p = node.p;
        // StdOut.println(p);
        RectHV rectHV = node.rect;
        p.draw();
        if(!node.isVertical){
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.005);
            StdDraw.line(rectHV.xmin(),p.y(),rectHV.xmax(),p.y());
        }
        else{
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.005);
            StdDraw.line(p.x(),rectHV.ymin(),p.x(),rectHV.ymax());
        }
        drawNode(node.lb);
        drawNode(node.rt);

    }

    public void draw(){
        drawNode(root);
    } // draw all points to standard draw


    private void RangeSearch(SET<Point2D> set,Node n,RectHV rect){
        if (n==null){
            return;
        }
        if (!n.rect.intersects(rect)){
            return;
        }
        if(rect.contains(n.p)){
            set.add(n.p);
        }
        if(n.lb!=null){
            RangeSearch(set,n.lb,rect);
        }
        if(n.rt!=null){
            RangeSearch(set,n.rt,rect);
        }
    }

    public Iterable<Point2D> range(RectHV rect){
        SET<Point2D> set = new SET<>();
        RangeSearch(set,root,rect);
        return set;
    } // all points that are inside the rectangle (or on the boundary)

    private Node findNearstNodeByBinarySearch(Point2D p,Node n){
        if (n.rect.contains(p) && n.lb==null && n.rt==null){
            return n;
        }
        if(n.lb.rect.contains(p)){
            return findNearstNodeByBinarySearch(p,n.lb);
        }
        if(n.rt.rect.contains(p)){
            return findNearstNodeByBinarySearch(p,n.rt);
        }
        return null;
    }

    private Node findNodeRecursively(Point2D p,Node n,Node nn){
        double minDis = nn.p.distanceTo(p);
        double rectDis = n.rect.distanceTo(p);
        // 如果到矩阵的距离比最短距离还大，就不搜索了
        if(rectDis>minDis){
            return nn;
        }

        double dis = n.p.distanceTo(p);

        // 更新距离
        Node nn_new = nn;
        if(dis<minDis){
            nn_new = n;
        }

        // 先搜索左子树，并更新
        if (n.lb!=null){
            nn_new = findNodeRecursively(p,n.lb,nn_new);
        }
        // 再搜右子树再更新
        if (n.rt!=null){
            nn_new = findNodeRecursively(p,n.rt,nn_new);
        }
        return nn_new;
    }

    public Point2D nearest(Point2D p) {
        double minDis = Double.POSITIVE_INFINITY;
        // Node nn = findNearstNodeByBinarySearch(p,root);
        return findNodeRecursively(p,root,root).p;
    } // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdTree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdTree.insert(p);
        }
        StdOut.println(kdTree.size);
        kdTree.draw();
        StdDraw.show();

    } // unit testing of the methods (optional)
}