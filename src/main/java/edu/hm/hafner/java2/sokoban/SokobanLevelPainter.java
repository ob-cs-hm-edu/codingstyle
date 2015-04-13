package edu.hm.hafner.java2.sokoban;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static edu.hm.hafner.java2.sokoban.Field.*;

/**
 * Shows a Sokoban level in a panel.
 *
 * @author Ullrich Hafner
 */
public class SokobanLevelPainter extends JPanel {
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Field[][] level = new Field[][]{
                        {BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND},
                        {BACKGROUND, WALL, WALL, WALL, WALL, BACKGROUND, BACKGROUND, BACKGROUND},
                        {BACKGROUND, WALL, FLOOR, TARGET, WALL, BACKGROUND, BACKGROUND, BACKGROUND},
                        {BACKGROUND, WALL, FLOOR, FLOOR, WALL, WALL, WALL, BACKGROUND},
                        {BACKGROUND, WALL, TARGET, FLOOR, FLOOR, FLOOR, WALL, BACKGROUND},
                        {BACKGROUND, WALL, FLOOR, FLOOR, FLOOR, FLOOR, WALL, BACKGROUND},
                        {BACKGROUND, WALL, FLOOR, FLOOR, WALL, WALL, WALL, BACKGROUND},
                        {BACKGROUND, WALL, WALL, WALL, WALL, BACKGROUND, BACKGROUND, BACKGROUND},
                        {BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND, BACKGROUND},
                };
                Sokoban sokoban = new Sokoban();
                sokoban.setLevel(level);
                sokoban.setPlayer(new Point(3, 4));
                sokoban.addTreasure(new Point(2, 4));
                sokoban.addTreasure(new Point(4, 5));
                sokoban.validate();

                createAndShowGUI(sokoban);
            }
        });
    }

    /**
     * Shows the specified Sokoban level in a panel.
     *
     * @param sokoban the sokoban level
     */
    private static void createAndShowGUI(final Sokoban sokoban) {
        SokobanImageRenderer painter = new SokobanImageRenderer();
        BufferedImage bitmap = painter.toImage(sokoban, Orientation.DOWN);
        SokobanLevelPainter panel = new SokobanLevelPainter(bitmap);
        panel.setFocusable(true);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(bitmap.getWidth(), bitmap.getHeight() + 32);
        frame.setFocusable(true);
        frame.setVisible(true);
        frame.setTitle(sokoban.toString());
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_ENTER) {
                    System.exit(0);
                }
            }
        });
    }

    private BufferedImage image;

    /**
     * Creates a new instance of {@link SokobanLevelPainter}.
     *
     * @param image the image to show
     */
    SokobanLevelPainter(final BufferedImage image) {
        super();

        this.image = image;
    }

    /**
     * Drawing an image can allow for more flexibility in processing/editing.
     */
    @Override
    protected void paintComponent(final Graphics graphics) {
        graphics.drawImage(image, 0, 0, this);
    }
}
