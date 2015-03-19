package edu.hm.hafner.java2.sokoban;

import java.util.Arrays;

/**
 * A collection of points that contains no duplicates. More formally, a {@link PointSet} contains no pair of elements
 * {@code p1} and {@code p2} such that {@code p1.isEqualTo(p2)}. The order of the added elements is preserved.
 *
 * @author FIXME: add your name
 */
public class PointSet {
   /**
     * Example code to demonstrate behavior of class.
     *
     * @param args not used
     */
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void main(final String... args) {
        PointSet points = new PointSet();

        System.out.println(points.size());                              // 0
        System.out.println(points.add(new Point(0, 0)));                // true
        System.out.println(points.contains(new Point(0, 0)));           // true
        System.out.println(points.size());                              // 1

        PointSet copy = new PointSet(points);
        System.out.println(copy.size());                                // 1
        System.out.println(copy.contains(new Point(0, 0)));             // true

        System.out.println(copy.add(new Point(0, 0)));                // false
        System.out.println(copy.size());                              // 1
        System.out.println(copy.get(0).isEqualTo(new Point(0, 0)));   // true

        System.out.println(copy.remove(new Point(0, 0)));             // true
        System.out.println(copy.contains(new Point(0, 0)));           // false
        System.out.println(copy.size());                              // 0

        System.out.println(copy.remove(new Point(0, 0)));             // false

        System.out.println(points.size());                            // 1
    }
}
