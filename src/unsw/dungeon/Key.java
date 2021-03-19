package unsw.dungeon;

/**
 * Can be picked up by the player when they move into the square containing it. 
 * The player can carry only one key at a time, and only one door has a lock that fits the key. 
 * It disappears once it is used to open its corresponding door.
 * @author Haokai Zhao
 *
 */

public class Key extends Collectables {

    int id;

    public Key(int x, int y, int id) {
        super(x,y);
        this.id = id;
    }

    public void handleCollision(Player player) {
        if (isCollected(player)) {
            player.setKey(this);
            super.getExistence().setValue(false);
            //super.changeImage(null);
        }
    }
    
    public boolean isCollected(Player player) {
        if (player.getKey() != null) {
            return false;
        }
        return true;
    }

    public int getId() {
        return this.id;
    }
}