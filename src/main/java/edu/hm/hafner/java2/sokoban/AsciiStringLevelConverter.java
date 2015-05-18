package edu.hm.hafner.java2.sokoban;

import org.apache.commons.lang3.StringUtils;

/**
 * Reads and creates a Sokoban level in ASCII format.
 *
 * @author Ullrich Hafner
 */
// Wall                 #
// Player 	            @
// Player on target     +
// Treasure             $
// Treasure on target   *
// Target 	            .
// Floor             	(Space)
// Comment              ::
public class AsciiStringLevelConverter implements StringLevelConverter {
    private static final String COMMENT = "::";

    @Override
    public SokobanGame convert(final String[] lines) {
        int width = computeWidth(lines);
        int height = computeHeight(lines);

        Field[][] game = new Field[height][width];
        Sokoban sokoban = new Sokoban();

        int y = 0;
        for (String line : lines) {
            if (hasContent(line)) {
                boolean isLineBeginning = false;
                for (int x = 0; x < line.length(); x++) {
                    char fileElement = line.charAt(x);
                    Field field;
                    if (fileElement == '#') {
                        field = Field.WALL;
                        isLineBeginning = true;
                    }
                    else if (fileElement == '.') {
                        field = Field.TARGET;
                    }
                    else if (fileElement == '@') {
                        field = Field.FLOOR;
                        sokoban.setPlayer(new Point(x, y));
                    }
                    else if (fileElement == '+') {
                        field = Field.TARGET;
                        sokoban.setPlayer(new Point(x, y));
                    }
                    else if (fileElement == '$') {
                        field = Field.FLOOR;
                        sokoban.addTreasure(new Point(x, y));
                    }
                    else if (fileElement == '*') {
                        field = Field.TARGET;
                        sokoban.addTreasure(new Point(x, y));
                    }
                    else  if (fileElement == ' ') {
                        if (isLineBeginning) {
                            field = Field.FLOOR;
                        }
                        else {
                            field = Field.BACKGROUND;
                        }
                    }
                    else {
                        throw new IllegalArgumentException(String.format(
                                "Illegal character detected at position (%d, %d): %c", x, y, fileElement));
                    }
                    game[y][x] = field;
                }
                for (int x = line.length(); x < width; x++) {
                    game[y][x] = Field.BACKGROUND;
                }
                y++;
            }
        }
        sokoban.setLevel(game);
        sokoban.validate();
        return sokoban;
    }

    private int computeWidth(final String[] lines) {
        int width = 0;
        for (String line : lines) {
            if (isNoComment(line)) {
                width = Math.max(width, line.length());
            }
        }
        return width;
    }

    private int computeHeight(final String[] lines) {
        int height = 0;
        for (String line : lines) {
            if (hasContent(line)) {
                height++;
            }
        }
        return height;
    }

    private boolean hasContent(final String line) {
        return isNoComment(line) && StringUtils.isNotBlank(line);
    }

    private boolean isNoComment(final String line) {
        return !line.trim().startsWith(COMMENT);
    }
}
