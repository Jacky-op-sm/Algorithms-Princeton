import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BruteCollinearPoints {

    private List<LineSegment> LS = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        //handle the corner case.
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }
        Point[] points_copy = points.clone();
        Arrays.sort(points_copy, positionOrder());
        for (int i = 0; i < points_copy.length - 1; i += 1) {
            Point p1 = points_copy[i];
            Point p2 = points_copy[i + 1];
            if (p1.compareTo(p2) == 0) {
                throw new IllegalArgumentException();
            }
        }

        //four loops
        for (int i = 0; i < points_copy.length; i += 1) {
            for (int j = i + 1; j < points_copy.length; j += 1) {
                for (int m = j + 1; m < points_copy.length; m += 1) {
                    for (int n = m + 1; n < points_copy.length; n += 1) {
                        Point p = points_copy[i];
                        Point r = points_copy[j];
                        Point s = points_copy[m];
                        Point q = points_copy[n];
                        Double s1 = p.slopeTo(r);
                        Double s2 = p.slopeTo(s);
                        Double s3 = p.slopeTo(q);
                        if (s1.equals(s2) && s2.equals(s3)) {
                            LineSegment ls = new LineSegment(p, q);
                            LS.add(ls);
                        }
                    }
                }
            }
        }


    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private Comparator<Point> positionOrder() {
        /* YOUR CODE HERE */
        return new PositionOrder();
    }

    // the number of line segments
    public int numberOfSegments() {
        return LS.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] rs = new LineSegment[LS.size()];
        int i = 0;
        for (LineSegment ls : LS) {
            rs[i] = ls;
            i += 1;
        }
        return rs;
    }

    private class PositionOrder implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            return o1.compareTo(o2);
        }
    }
}
