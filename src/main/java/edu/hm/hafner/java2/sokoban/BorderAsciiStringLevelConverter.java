package edu.hm.hafner.java2.sokoban;

/**
 * Reads and creates a Sokoban level in ASCII format. Each level is decorated with a border using {@link
 * Field#BACKGROUND}.
 *
 * @author Ullrich Hafner
 */
public class BorderAsciiStringLevelConverter extends AsciiStringLevelConverter {
    @Override
    public SokobanGame convert(final String[] lines) {
        SokobanGame game = super.convert(lines);

        Sokoban sokoban = new Sokoban();
        sokoban.setLevel(createBorder(game));
        sokoban.setPlayer(translate(game.getPlayer()));
        PointSet oldTreasures = game.getTreasures();
        for (int i = 0; i < oldTreasures.size(); i++) {
            sokoban.addTreasure(translate(oldTreasures.get(i)));
        }
        return sokoban;
    }

    private Field[][] createBorder(final SokobanGame game) {
        int height = game.getHeight() + 2;
        int width = game.getWidth() + 2;
        Field[][] fields = new Field[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
                    fields[y][x] = Field.BACKGROUND;
                }
                else {
                    fields[y][x] = game.getField(new Point(x - 1, y - 1));
                }
            }
        }
        return fields;
    }

    private Point translate(final Point treasure) {
        return treasure.moveDown().moveRight();
    }
}
