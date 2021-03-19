package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class PortalTest {
    
    @Test
    public void creationTest() {
        Dungeon dungeon = new Dungeon(2,2);
        Portal portal1 = new Portal(0,0);
        Portal portal2 = new Portal(1,1);
        
        portal1.addCorresponding(portal2);
        dungeon.addEntity(portal1);
        dungeon.addEntity(portal2);

        assertEquals(portal1.getX(),0);
        assertEquals(portal1.getY(),0);

        assertEquals(portal2.getX(),1);
        assertEquals(portal2.getY(),1);
    }

    @Test
    public void transportTest() {
        Dungeon dungeon = new Dungeon(2,2);
        Portal portal1 = new Portal(0,0);
        Portal portal2 = new Portal(1,1);
        Player player = new Player(dungeon,0,1);
        
        portal1.addCorresponding(portal2);
        dungeon.addEntity(portal1);
        dungeon.addEntity(portal2);
        dungeon.addEntity(player);

        player.handleCollision(portal2);
        assertEquals(player.getX(), 0);
        assertEquals(player.getY(), 1);
        assertEquals(player.checkMovable(1, 1), true);
        player.setHasMoved("up");
        player.handleCollision(portal2);
        assertEquals(player.getX(), portal1.getX());
        assertEquals(player.getY(), portal1.getY());
    }
}