package unsw.dungeon;

/**
 * Movable items in the dungeon
 * @author Haokai Zhao
 *
 */

public interface Movable {
    public void moveUp();
    public void moveDown();
    public void moveLeft();
    public void moveRight();

    public boolean hasMoved();
    public boolean checkMovable(int x, int y);
    public void transferTo(int x, int y);
}