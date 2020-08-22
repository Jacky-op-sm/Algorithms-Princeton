import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;


public class PointSET {

    private SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<>();
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    //validate the input p
    private void validate(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validate(p);
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validate(p);
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        SET<Point2D> rs = new SET<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                rs.add(p);
            }
        }
        return rs;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validate(p);
        if (isEmpty()) return null;
        Point2D min = points.min();
        for (Point2D ps : points) {
            if (ps.distanceSquaredTo(p) < min.distanceSquaredTo(p)) {
                min = ps;
            }
        }
        return min;
    }
}