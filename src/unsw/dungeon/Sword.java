package unsw.dungeon;

/**
 * This can be picked up the player and used to kill enemies. 
 * Only one sword can be carried at once. 
 * Each sword is only capable of 5 hits and disappears after that. 
 * One hit of the sword is sufficient to destroy any enemy.
 * @author Haokai Zhao
 *
 */
public class Sword extends Collectables {
    
    private boolean collected;
    int durability;

    public Sword(int x, int y, int durability) {
        super(x,y);
        this.collected = false;
        this.durability = durability;
    }

    public void handleCollision(Player player) {
        if (isCollected()) {
            return;
        }
        if (player.getSword() == null) {
            player.pickupSword(this);
        } 
        else {
            player.removeEntity((Entity) this);
            player.pickupSword(this);
        }
        setCollected();
        super.getExistence().setValue(false);
    }

    public int getDurability() {
        return durability;
    }

    public void useSword(Player player) {
        this.durability -= 1;
        if (this.durability == 0) {
            player.removeSword(this);
        }
        System.out.println("durability: "+durability);
    }

    private void setCollected() {
        this.collected = true;
    }

    public boolean isCollected() {
        return this.collected;
    }
}