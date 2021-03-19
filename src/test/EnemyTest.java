package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class EnemyTest {
    
    @Test
    public void creationTest() {
        Dungeon dungeon = new Dungeon(2, 2);
        Enemy enemy = new Enemy(0, 0, dungeon);
        
        dungeon.addEntity(enemy);

        assertEquals(enemy.getX(),0);
        assertEquals(enemy.getY(),0);
    }

    @Test
    public void playerCollisionTest() {
        Dungeon dungeon = new Dungeon(2, 2);
        Enemy enemy = new Enemy(0, 0, dungeon);
        dungeon.addEntity(enemy);
        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);

        player.handleCollision(enemy);

        // confirm player dies by print statement
        // level reset to be handled in milestone 3
    }

    @Test
    public void playerSwordTest() {
        Dungeon dungeon = new Dungeon(2, 2);
        Enemy enemy = new Enemy(1, 0, dungeon);
        dungeon.addEntity(enemy);
        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);
        Sword sword = new Sword(1, 1, 5);
        dungeon.addEntity(sword);

        // assert enemy is still in the dungeon
        assertEquals(true, dungeon.getAllEntities().contains(enemy));

        // player picks up sword and attacks
        player.handleCollision(sword);
        player.setHasMoved("up");
        player.useSword();

        // assert enemy is no longer in the dungeon
        assertEquals(false, dungeon.getAllEntities().contains(enemy));
    }

    @Test
    public void playerPotionTest() {
        Dungeon dungeon = new Dungeon(2, 2);
        Enemy enemy = new Enemy(1, 0, dungeon);
        dungeon.addEntity(enemy);
        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);
        Potion potion = new Potion(1, 1);
        dungeon.addEntity(potion);

        // assert enemy is still in the dungeon
        assertEquals(true, dungeon.getAllEntities().contains(enemy));

        // player drinks potion and collides with enemy
        player.handleCollision(potion);
        player.handleCollision(enemy);

        // assert enemy is no longer in the dungeon
        assertEquals(false, dungeon.getAllEntities().contains(enemy));
    }
}