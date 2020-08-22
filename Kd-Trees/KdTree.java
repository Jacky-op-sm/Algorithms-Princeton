import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private Node root;
    private int size;
    private List<Point2D> ps;

    // construct an empty set of points
    public KdTree() {
        size = 0;
        ps = new ArrayList<>();
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {



        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        StdDraw.enableDoubleBuffering();
        String c = "1";
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            StdDraw.text(x,y, c);
            c += 1;
        }
        Point2D test = new Point2D(0.88, 0.05);
        kdtree.draw();

        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.RED);
        test.draw();
        StdDraw.show();
        StdDraw.pause(40);
        StdOut.println(kdtree.nearest(test));

        /**
        KdTree kdtree = new KdTree();
        kdtree.insert(new Point2D(0.5,0.5));
        kdtree.insert(new Point2D(0.4,0.3));
        kdtree.insert(new Point2D(0.4,0.3));
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.03);
        StdDraw.setPenColor(StdDraw.RED);
        RectHV test = new RectHV(0, 0, 1, 1);
        for (Point2D p : kdtree.range(test)) {
            p.draw();
        }
        StdDraw.show();
        StdDraw.pause(40);
        StdOut.println(kdtree.size());
         */
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validate(p);
        double[] position = new double[]{0, 0, 1, 1};
        root = insert(root, p, position, true);
    }

    private Node insert(Node n, Point2D p, double[] position, boolean vertical) {
        if (n == null) {
            size += 1;
            return new Node(p, new RectHV(position[0], position[1], position[2], position[3]));
        }
        if (vertical) {
            double cmp = p.x() - n.p.x();
            if (cmp < 0) {
                position[2] = n.p.x();
                n.lb = insert(n.lb, p, position, false);
            } else {
                if (!n.p.equals(p)) {
                    position[0] = n.p.x();
                    n.rt = insert(n.rt, p, position, false);
                }
            }
        } else {
            double cmp = p.y() - n.p.y();
            if (cmp < 0) {
                position[3] = n.p.y();
                n.lb = insert(n.lb, p, position, true);
            } else {
                if (!n.p.equals(p)) {
                    position[1] = n.p.y();
                    n.rt = insert(n.rt, p, position, true);
                }
            }
        }
        return n;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validate(p);
        return contains(root, p, true);
    }

    private boolean contains(Node n, Point2D p, boolean vertical) {
        if (n == null) return false;
        if (n.p.equals(p)) return true;
        if (vertical) {
            double cmp = p.x() - n.p.x();
            if (cmp < 0) return contains(n.lb, p, false);
            else return contains(n.rt, p, false);
        } else {
            double cmp = p.y() - n.p.y();
            if (cmp < 0) return contains(n.lb, p, true);
            else return contains(n.rt, p, true);
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, true);
    }

    private void draw(Node x, boolean vertical) {
        if (x == null) {
            return;
        }
        if (vertical) {
            draw(x.lb, false);

            //draw the point
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            x.p.draw();

            //draw the vertical line
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.002);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());

            draw(x.rt, false);
        } else {
            draw(x.lb, true);

            //draw the point
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            x.p.draw();

            //draw the horizontal line
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.002);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());

            draw(x.rt, true);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        return range(root, rect);
    }

    private Iterable<Point2D> range(Node n, RectHV rect) {
        List<Point2D> temp = new ArrayList<>();

        if (n == null) {
            return temp;
        }

        if (!n.rect.intersects(rect)) {
        }
        else {
            if (rect.contains(n.p)) temp.add(n.p);
            for (Point2D p : range(n.lb, rect)) {
                temp.add(p);
            }
            for (Point2D p : range(n.rt, rect)) {
                temp.add(p);
            }
        }
        return temp;
    }

    //validate the input p
    private void validate(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validate(p);
        if (isEmpty()) {
            return null;
        }
        return nearest(root, p, root.p);
    }

    private Point2D nearest(Node n, Point2D p, Point2D best_so_far) {
        /**
         * 存在问题：
         * 不能用rect.contains(p)来判断优先级
         * 因为后面的rect可能两者都不包含
         * 应该还是用方位来判断
         * 或者有什么更好的方式;
         */
        if (n == null) {
            return null;
        }

        //case1: no child
        if (n.lb == null && n.rt == null) {
            if (n.p.distanceSquaredTo(p) < best_so_far.distanceSquaredTo(p))
                best_so_far = n.p;
        }

        //case2: only left child
        else if (n.rt == null) {
            if (n.p.distanceSquaredTo(p) < best_so_far.distanceSquaredTo(p))
                best_so_far = n.p;
            if (best_so_far.distanceSquaredTo(p) > n.lb.rect.distanceSquaredTo(p))
                best_so_far = nearest(n.lb, p, best_so_far);
        }

        //case2: only right child
        else if (n.lb == null) {
            if (n.p.distanceSquaredTo(p) < best_so_far.distanceSquaredTo(p))
                best_so_far = n.p;
            if (best_so_far.distanceSquaredTo(p) > n.rt.rect.distanceSquaredTo(p))
                best_so_far = nearest(n.rt, p, best_so_far);
        }


        //case3 : both child
        else {
            if (n.p.distanceSquaredTo(p) < best_so_far.distanceSquaredTo(p))
                best_so_far = n.p;
            if (n.lb.rect.contains(p) || n.lb.rect.distanceSquaredTo(p) < n.rt.rect.distanceSquaredTo(p)) {
                best_so_far = nearest(n.lb, p, best_so_far);
                if (best_so_far.distanceSquaredTo(p) > n.rt.rect.distanceSquaredTo(p))
                    best_so_far = nearest(n.rt, p, best_so_far);
            } else {
                best_so_far = nearest(n.rt, p, best_so_far);
                if (best_so_far.distanceSquaredTo(p) > n.lb.rect.distanceSquaredTo(p))
                    best_so_far = nearest(n.lb, p, best_so_far);
            }
        }
        return best_so_far;
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        private Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }
}
