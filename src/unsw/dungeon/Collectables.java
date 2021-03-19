package unsw.dungeon;

/**
 * A collectable in the dungeon.
 * Can be collected by the player when collide with player
 * @author Haokai Zhao
 *
 */

public abstract class Collectables extends Entity {

    public Collectables(int x, int y) {
        super(x,y);
    }

    abstract void handleCollision(Player player);
}