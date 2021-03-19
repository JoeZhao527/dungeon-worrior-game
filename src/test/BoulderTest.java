package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class BoulderTest {
    @Test
    public void creationTest() {
        Dungeon dungeon = new Dungeon(2,2);
        Boulder boulder = new Boulder(0,0,dungeon);

        dungeon.addEntity(boulder);
        assertEquals(boulder.getX(),0);
        assertEquals(boulder.getY(),0);
    }
    
    @Test
    public void checkMovableTest() {
        Dungeon dungeon = new Dungeon(3,3);
        Boulder boulder = new Boulder(1,1,dungeon);
        List<Entity> list = new ArrayList<>();
        Wall wall = new Wall(0,1);
        list.add(wall);
        Door door = new Door(dungeon,1,0,0);
        list.add(door);
        Exit exit = new Exit(1,2,dungeon);
        list.add(exit);
        Boulder boulder2 = new Boulder(0, 0, dungeon);
        list.add(boulder2);
        Treasure treasure = new Treasure(2,1,dungeon);
        list.add(treasure);

        for (Entity e : list) {
            dungeon.addEntity(e);
            assertEquals(boulder.checkMovable(e.getX(), e.getY()),false);
        }
        
    }

    @Test
    public void handleCollisionTest() {
        Dungeon dungeon = new Dungeon(3,3);
        Boulder boulder = new Boulder(1,1,dungeon);
        Player player = new Player(dungeon, 0, 0);

        dungeon.addEntity(boulder);
        dungeon.addEntity(player);

        player.setHasMoved("up");
        boulder.handleCollision(player);
        assertEquals(boulder.getX(), 1);
        assertEquals(boulder.getY(), 0);

        player.setHasMoved("down");
        boulder.handleCollision(player);
        assertEquals(boulder.getX(),1);
        assertEquals(boulder.getY(), 1);

        player.setHasMoved("left");
        boulder.handleCollision(player);
        assertEquals(boulder.getX(),0);
        assertEquals(boulder.getY(),1);

        player.setHasMoved("right");
        boulder.handleCollision(player);
        assertEquals(boulder.getX(),1);
        assertEquals(boulder.getY(),1);
    }

    @Test
    public void checkCollisionTest() {
        Dungeon dungeon = new Dungeon(3,3);
        Boulder boulder = new Boulder(1,1,dungeon);
        Portal portal1 = new Portal(0,0);
        Portal portal2 = new Portal(1,1);
        
        portal1.addCorresponding(portal2);
        dungeon.addEntity(portal1);
        dungeon.addEntity(portal2);
        dungeon.addEntity(boulder);

        boulder.setHasMoved("up");
        boulder.checkCollision();
        assertEquals(boulder.getX(), portal1.getX());
        assertEquals(boulder.getY(), portal1.getY());

        Boulder boulder2 = new Boulder(2,2,dungeon);
        FloorSwitch fs = new FloorSwitch(2, 2, dungeon);
        dungeon.addEntity(fs);
        dungeon.addEntity(boulder2);

        boulder2.checkCollision();
        assertEquals(fs.isActive(), true);
    }

    @Test
    public void moveTest() {
        Dungeon dungeon = new Dungeon(3,3);
        Boulder boulder = new Boulder(1,1,dungeon);

        boulder.moveUp();
        assertEquals(boulder.getX(), 1);
        assertEquals(boulder.getY(), 0);

        boulder.moveDown();
        assertEquals(boulder.getX(), 1);
        assertEquals(boulder.getY(), 1);

        boulder.moveLeft();
        assertEquals(boulder.getX(), 0);
        assertEquals(boulder.getY(), 1);

        boulder.moveRight();
        assertEquals(boulder.getX(), 1);
        assertEquals(boulder.getY(), 1);
    }
}