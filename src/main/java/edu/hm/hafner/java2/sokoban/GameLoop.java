package edu.hm.hafner.java2.sokoban;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static edu.hm.hafner.java2.sokoban.Field.*;
import static edu.hm.hafner.java2.sokoban.Orientation.*;

/**
 * Entry point for Sokoban.
 *
 * @author Ullrich Hafner
 */
public class GameLoop extends JPanel {
    public static void main(final String... args) {
        SokobanGame level;
        String name;
        if (args.length == 1) {
            String[] lines = new FileReader().readLines(args[0]);
            level = new BorderAsciiStringLevelConverter().convert(lines);
            name = args[0];
        }
        else if (args.length == 0) {
            level = createLevel();
            name = "KaraSokoban";
        }
        else {
            throw new IllegalArgumentException("Usage: java GameLoop [level-file-name]");
        }

        SwingUtilities.invokeLater(() -> {
            showLevel(createUndoableSokoban(level), name);
        });
    }

    private static UndoableSokobanGame createUndoableSokoban(final SokobanGame level) {
        UndoableSokoban sokoban = new UndoableSokoban();
        sokoban.setLevel(copyLevel(level));
        sokoban.setPlayer(level.getPlayer());
        PointSet treasures = level.getTreasures();
        for (int i = 0; i < treasures.size(); i++) {
            sokoban.addTreasure(treasures.get(i));
        }
        sokoban.validate();
        return sokoban;
    }

    private static Field[][] copyLevel(final SokobanGame level) {
        Field[][] fields = new Field[level.getHeight()][level.getWidth()];
        for (int y = 0; y < level.getHeight(); y++) {
            for (int x = 0; x < level.getWidth(); x++) {
                fields[y][x] = level.getField(new Point(x, y));
            }
        }
        return fields;
    }

    private static SokobanGame createLevel() {
        Field[][] fields = {
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
        UndoableSokoban sokoban = new UndoableSokoban();
        sokoban.setLevel(fields);
        sokoban.setPlayer(new Point(3, 4));
        sokoban.addTreasure(new Point(2, 4));
        sokoban.addTreasure(new Point(4, 5));
        sokoban.validate();
        return sokoban;
    }

    private static void showLevel(final UndoableSokobanGame sokoban, final String name) {
        SokobanGameRenderer painter = new SokobanGameRenderer();
        BufferedImage bitmap = painter.toImage(sokoban, DOWN);
        GameLoop game = new GameLoop(bitmap);
        game.setFocusable(true);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setSize(bitmap.getWidth(), bitmap.getHeight() + 32);
        frame.setFocusable(true);
        frame.setVisible(true);
        frame.setTitle(createTitleMessage(sokoban, name));
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                    sokoban.moveLeft();
                    game.setOrientation(LEFT);
                }
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                    sokoban.moveRight();
                    game.setOrientation(RIGHT);
                }
                else if (event.getKeyCode() == KeyEvent.VK_UP) {
                    sokoban.moveUp();
                    game.setOrientation(UP);
                }
                else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                    sokoban.moveDown();
                    game.setOrientation(DOWN);
                }
                else if (event.getKeyCode() == KeyEvent.VK_R) {
                    sokoban.restart();
                    game.setOrientation(DOWN);
                }
                else if (event.getKeyCode() == KeyEvent.VK_U) {
                    sokoban.undo();
                }
                else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                game.setImage(painter.toImage(sokoban, game.getOrientation()));
                frame.setTitle(createTitleMessage(sokoban, name));
                frame.repaint();
            }
        });
    }

    private static String createTitleMessage(final UndoableSokobanGame sokoban, final String name) {
        return name + " - " + sokoban;
    }

    /**
     * Sets the image of this frame.
     *
     * @param image the new image
     */
    protected void setImage(final BufferedImage image) {
        this.image = image;
        repaint();
    }

    private transient BufferedImage image;
    private Orientation orientation = DOWN;

    /**
     * Creates a new instance of {@link GameLoop}.
     *
     * @param image the image to show
     */
    GameLoop(final BufferedImage image) {
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

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(final Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Draws the Sokoban game state into an image.
     *
     * @author Ullrich Hafner
     */
    private static class SokobanGameRenderer {
        private static final int BLOCK_SIZE = 64;

        /**
         * Returns the Sokoban level as an image.
         *
         * @return an image of this board
         */
        public BufferedImage toImage(final SokobanGame sokoban, final Orientation orientationToDraw) {
            BufferedImage boardImage = new BufferedImage(sokoban.getWidth() * BLOCK_SIZE,
                    sokoban.getHeight() * BLOCK_SIZE, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < sokoban.getHeight(); y++) {
                for (int x = 0; x < sokoban.getWidth(); x++) {
                    drawImage(boardImage, asImageName(sokoban.getField(new Point(x, y))), x, y);
                }
            }
            drawImage(boardImage, sokoban.getPlayer(), asImageName(orientationToDraw));
            PointSet treasures = sokoban.getTreasures();
            for (int i = 0; i < treasures.size(); i++) {
                drawImage(boardImage, treasures.get(i), "treasure");
            }

            return boardImage;
        }

        private String asImageName(final Enum<?> type) {
            return type.name().toLowerCase();
        }

        private void drawImage(final BufferedImage boardImage, final Point position, final String imageName) {
            drawImage(boardImage, imageName, position.getX(), position.getY());
        }

        private void drawImage(final BufferedImage boardImage, final String imageName, final int x, final int y) {
            boardImage.createGraphics().drawImage(loadImage(imageName + ".png"),
                    x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, null);
        }

        private static BufferedImage loadImage(final String imageName) {
            try {
                return readImageFromStream(imageName);
            }
            catch (IOException exception) {
                throw new IllegalArgumentException("Can't read image " + imageName, exception);
            }
        }

        private static BufferedImage readImageFromStream(final String fileName) throws IOException {
            try (InputStream stream = SokobanGameRenderer.class.getResourceAsStream("/" + fileName)) {
                if (stream == null) {
                    throw new IllegalArgumentException("Can't find image " + fileName);
                }
                return ImageIO.read(stream);
            }
        }
    }
}
