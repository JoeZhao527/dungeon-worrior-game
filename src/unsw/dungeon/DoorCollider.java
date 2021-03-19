package unsw.dungeon;

/**
 * try to open the corresponding door when the player collider with this
 * @author Haokai Zhao
 *
 */

public class DoorCollider extends Entity {
    int id;
    Door door;

    public DoorCollider(int x, int y, Door door) {
        super(x,y);
        this.id = door.getId();
        this.door = door;
    }

    public boolean openDoor(Player player) {
        Key key = player.getKey();
        if (door.getState() == false && key != null && key.getId() == this.id) {
            this.door.open();
            player.removeKey(key);
            return true;
        }
        return false;
    }
}