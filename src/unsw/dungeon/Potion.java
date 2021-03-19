package unsw.dungeon;

/**
 * If the player picks this up they become invincible to enemies. 
 * Colliding with an enemy should result in their immediate destruction. 
 * Because of this, all enemies will run away from the player when they are invincible. 
 * The effect of the potion only lasts 5s.
 * @author Haokai Zhao
 *
 */

public class Potion extends Collectables {

    private boolean collected;

    public Potion(int x, int y) {
        super(x,y);
        this.collected = false;
    }

    public void handleCollision(Player player) {
        if (isCollected()) {
            return;
        }
        if (player.getPotion() == null) {
            player.drinkPotion(this);
        } 
        else {
            player.removeEntity((Entity) this);
            player.drinkPotion(this);
        }
        setCollected();
        super.getExistence().setValue(false);
        //super.changeImage(null);
    }

    private void setCollected() {
        this.collected = true;
    }

    private boolean isCollected() {
        return this.collected;
    }
}