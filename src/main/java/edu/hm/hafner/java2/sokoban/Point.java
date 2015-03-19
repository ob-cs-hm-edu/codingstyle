package edu.hm.hafner.java2.sokoban;

import javax.annotation.concurrent.Immutable;

/**
 * A point represents a location in {@code (x,y)} coordinate space,
 * specified in integer precision. Instances of this class are immutable.
 *
 * @author FIXME: add your name
 */
@Immutable
public class Point {
    /**
     * Example code to demonstrate behavior of class.
     *
     * @param args not used
     */
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void main(final String... args) {
        Point point = new Point(3, 4);

        System.out.println(point.getX()); // Gibt 3 aus
        System.out.println(point.getY()); // Gibt 4 aus

        System.out.println(point.isEqualTo(point)); // true
        System.out.println(point.isEqualTo(new Point(3, 4))); // true
        System.out.println(point.isEqualTo(new Point(4, 2))); // false
        System.out.println(point.isEqualTo(null)); // false

        // move* gibt neue Instanz zurück (ohne point zu verändern)
        System.out.println(point.moveLeft().getX()); // Gibt 2 aus
        System.out.println(point.moveRight().getX()); // Gibt 4 aus
        System.out.println(point.moveUp().getY()); // Gibt 3 aus
        System.out.println(point.moveDown().getY()); // Gibt 5 aus
    }
}
