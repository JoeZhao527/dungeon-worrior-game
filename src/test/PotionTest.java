package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class PotionTest {
    
    @Test
    public void creationTest() {
        Dungeon dungeon = new Dungeon(2, 2);
        Potion Potion = new Potion(0, 0);

        dungeon.addEntity(Potion);
        assertEquals(Potion.getY(), 0);
        assertEquals(Potion.getX(),0);
    }

    @Test
    public void collectionTest() {
        Dungeon dungeon = new Dungeon(2, 2);
        Potion Potion = new Potion(0, 0);
        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);
        dungeon.addEntity(Potion);

        assertEquals(player.getPotion(), null);
        player.handleCollision(Potion);
        assertEquals(player.getPotion(), Potion);
    }

}