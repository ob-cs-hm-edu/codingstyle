package edu.hm.hafner.java2.sokoban;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Draws the Sokoban level into an image.
 *
 * @author Ullrich Hafner
 */
public class SokobanImageRenderer {
    private static final int BLOCK_SIZE = 64;

    /**
     * Returns the Sokoban level as an image.
     *
     * @return an image of this board
     */
    public BufferedImage toImage(final Sokoban sokoban, final Orientation orientation) {
        BufferedImage boardImage = new BufferedImage(sokoban.getWidth() * BLOCK_SIZE,
                sokoban.getHeight() * BLOCK_SIZE, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < sokoban.getHeight(); y++) {
            for (int x = 0; x < sokoban.getWidth(); x++) {
                drawImage(boardImage, asImageName(sokoban.getField(new Point(x, y))), x, y);
            }
        }
        drawImage(boardImage, sokoban.getPlayer(), asImageName(orientation));
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
        InputStream stream = SokobanImageRenderer.class.getResourceAsStream("/" + fileName);
        if (stream == null) {
            throw new IllegalArgumentException("Can't find image " + fileName);
        }
        return ImageIO.read(stream);
    }
}