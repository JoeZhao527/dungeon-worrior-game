package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class KeyTest {
    
    @Test
    public void creationTest() {
        Dungeon dungeon = new Dungeon(2,2);
        Key key = new Key(0,0,0);

        dungeon.addEntity(key);
        assertEquals(key.getY(), 0);
        assertEquals(key.getX(),0);
        assertEquals(key.getId(), 0);
    }

    @Test
    public void collectionTest() {
        Dungeon dungeon = new Dungeon(2,2);
        Key key = new Key(0,0,0);
        Player player = new Player(dungeon,1,1);
        dungeon.addEntity(player);
        dungeon.addEntity(key);

        assertEquals(player.getKey(), null);
        player.handleCollision(key);
        assertEquals(player.getKey(), key);
    }

}