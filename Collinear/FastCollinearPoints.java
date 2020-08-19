import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {

    private List<LineSegment> LS = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        //handle the corner case.
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        Arrays.sort(points, positionOrder());
        for (int i = 0; i < points.length - 1; i += 1) {
            Point p1 = points[i];
            Point p2 = points[i + 1];
            if (p1.compareTo(p2) == 0) {
                throw new IllegalArgumentException();
            }
        }

        Point[] PO = points.clone();
        for (int i = 0; i < PO.length; i += 1) {
            Point target = PO[i];
            Arrays.sort(points, target.slopeOrder());
            List<Integer> ind_siz = index_size(points);

            int j = 0;
            while (j < ind_siz.size()) {
                int index = ind_siz.get(j);
                int size = ind_siz.get(j + 1);
                Point[] temp = new Point[size - 1];

                //copy points from index to index + size - 1 into temp;

                for (int n = 0, m = index; n < size - 1; m += 1, n += 1) {
                    temp[n] = points[m];
                }

                Arrays.sort(temp, positionOrder());
                if (target.compareTo(temp[0]) < 0) {
                    LineSegment ls = new LineSegment(target, temp[temp.length - 1]);
                    LS.add(ls);
                }
                j += 2;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
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

    private Comparator<Point> positionOrder() {
        /* YOUR CODE HERE */
        return new PositionOrder();
    }

    private List<Integer> index_size(Point[] points) {
        List<Integer> rs = new ArrayList<>();
        Point target = points[0];
        int i = 1;
        while (i < points.length) {
            int j = 2;
            while (i < points.length && target.slopeTo(points[i - 1]) == target.slopeTo(points[i])) {
                j += 1;
                i += 1;
            }
            if (j >= 4) {
                rs.add(i - j + 1);
                rs.add(j);
            }
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
