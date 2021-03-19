package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class SwordTest {
    
    @Test
    public void placeTest() {

        Dungeon dungeon = new Dungeon(2,2);
        Sword sword1 = new Sword(0,1,5);
        Sword sword2 = new Sword(1,1,5);

        dungeon.addEntity(sword1);
        assertEquals(sword1.getX(), 0);
        assertEquals(sword1.getY(), 1);
        assertEquals(sword1.getDurability(), 5);

        dungeon.addEntity(sword2);
        assertEquals(sword2.getX(), 1);
        assertEquals(sword2.getY(), 1);
        assertEquals(sword2.getDurability(), 5);
    }

    @Test
    public void pickupTest() {
        Dungeon dungeon = new Dungeon(2, 2);
        Sword sword = new Sword(0,1,5);
        Player player = new Player(dungeon, 2, 2);

        dungeon.addEntity(sword);
        dungeon.addEntity(player);

        assertEquals(player.getSword(), null);
        sword.handleCollision(player);
        assertEquals(sword.isCollected(), true);
        assertEquals(player.getSword(), sword);
    }

    @Test
    public void useSword() {
        Dungeon dungeon = new Dungeon(2, 2);
        Sword sword = new Sword(0,1,5);
        Player player = new Player(dungeon, 2, 2);

        dungeon.addEntity(sword);
        dungeon.addEntity(player);

        player.handleCollision(sword);
        for (int i = 5; i > 0; i--) {
            assertEquals(sword.getDurability(), i);
            sword.useSword(player);
        }
        assertEquals(player.getSword(), null);
    }
}