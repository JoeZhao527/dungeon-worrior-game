package unsw.dungeon;

/**
 * Teleports entities to a corresponding portal.
 * @author Haokai Zhao
 *
 */

public class Portal extends Entity {
    Portal corresponding = null;

    public Portal(int x, int y) {
        super(x,y);
    }

    public void addCorresponding(Portal portal) {
        this.corresponding = portal;
        if (portal.corresponding == null) portal.addCorresponding(this);
    }

    public void transfer(Movable movable) {
        if (!movable.hasMoved()) {
            return;
        }
        movable.transferTo(corresponding.getX(), corresponding.getY());
    }
}