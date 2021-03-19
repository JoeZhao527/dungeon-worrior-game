package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;
public class DoorTest {
    
    @Test
    public void creationTest() {
        Dungeon dungeon = new Dungeon(2,2);
        Door door = new Door(dungeon, 0,0,0);

        dungeon.addEntity(door);
        assertEquals(door.getId(), 0);
        assertEquals(door.getX(),0);
        assertEquals(door.getY(), 0);
    }

    @Test
    public void openTest() {
        Dungeon dungeon = new Dungeon(2,2);
        Door door = new Door(dungeon, 0,0,0);

        assertEquals(door.getState(), false);
        door.open();
        assertEquals(door.getState(), true);
    }

    @Test
    public void colliderTest() {
        Dungeon dungeon = new Dungeon(2,2);
        Door door = new Door(dungeon, 0,0,0);

        dungeon.addEntity(door);

        for (Entity e : dungeon.getEntity(1, 0)) {
            assertEquals(e instanceof DoorCollider, true);
        }

        for (Entity e : dungeon.getEntity(0, 1)) {
            assertEquals(e instanceof DoorCollider, true);
        }
    }

    @Test
    public void openDoorTest() {
        Dungeon dungeon = new Dungeon(2,2);
        Door door = new Door(dungeon, 0,0,0);
        Player player = new Player(dungeon,1,1);
        Key key0 = new Key(0,1,0);
        Key key1 = new Key(1,0,1);
        DoorCollider dc = new DoorCollider(1, 0, door);

        dungeon.addEntity(door);
        dungeon.addEntity(player);
        dungeon.addEntity(key0);
        dungeon.addEntity(key1);

        player.setKey(key1);
        assertEquals(dc.openDoor(player), false);
        assertEquals(door.getState(), false);

        player.setKey(key0);
        assertEquals(dc.openDoor(player), true);
        assertEquals(door.getState(), true);
    }
}