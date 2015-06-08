package edu.hm.hafner.java2.sokoban;

/**
 * A Sokoban game that could be restarted. Additionally, the last step can be undone.
 *
 * @author Ullrich Hafner
 */
public class UndoableSokoban extends Sokoban implements UndoableSokobanGame {
    private Point initialPlayer;
    private PointSet initialTreasures;

    private Point lastPlayer;
    private PointSet lastTreasures;

    @Override
    public void validate() {
        super.validate();

        initialPlayer = getPlayer();
        initialTreasures = new PointSet(getTreasures());

        storeUndoState(initialPlayer, initialTreasures);
    }

    @Override
    public void restart() {
        resetPlayerAndTreasures(initialPlayer, initialTreasures);
    }

    private void resetPlayerAndTreasures(final Point player, final PointSet treasures) {
        setPlayer(player);
        removeExistingTreasures();
        setTreasures(treasures);
    }

    private void removeExistingTreasures() {
        PointSet treasures = getTreasures();
        for (int i = 0; i < treasures.size(); i++) {
            removeTreasure(treasures.get(i));
        }
    }

    private void setTreasures(final PointSet treasures) {
        for (int i = 0; i < treasures.size(); i++) {
            addTreasure(treasures.get(i));
        }
    }

    @Override
    public void moveLeft() {
        Point beforePlayer = getPlayer();
        PointSet beforeTreasures = new PointSet(getTreasures());

        super.moveLeft();

        storePreviousState(beforePlayer, beforeTreasures);
    }

    @Override
    public void moveRight() {
        Point beforePlayer = getPlayer();
        PointSet beforeTreasures = new PointSet(getTreasures());

        super.moveRight();

        storePreviousState(beforePlayer, beforeTreasures);
    }

    @Override
    public void moveUp() {
        Point beforePlayer = getPlayer();
        PointSet beforeTreasures = new PointSet(getTreasures());

        super.moveUp();

        storePreviousState(beforePlayer, beforeTreasures);
    }

    @Override
    public void moveDown() {
        Point beforePlayer = getPlayer();
        PointSet beforeTreasures = new PointSet(getTreasures());

        super.moveDown();

        storePreviousState(beforePlayer, beforeTreasures);
    }

    private void storePreviousState(final Point beforePlayer, final PointSet beforeTreasures) {
        if (!beforePlayer.isEqualTo(getPlayer())) {
            storeUndoState(beforePlayer, beforeTreasures);
        }
    }

    private void storeUndoState(final Point beforePlayer, final PointSet beforeTreasures) {
        lastPlayer = beforePlayer;
        lastTreasures = new PointSet(beforeTreasures);
    }

    @Override
    public void undo() {
        Point undoPlayer = getPlayer();
        PointSet undoTreasures = new PointSet(getTreasures());

        resetPlayerAndTreasures(lastPlayer, lastTreasures);

        lastPlayer = undoPlayer;
        lastTreasures = undoTreasures;
    }
}
