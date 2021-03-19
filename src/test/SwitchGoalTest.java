package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class SwitchGoalTest {

    @Test
    public void basicGoalTest() {

        // set up dungeon and its entities
        Dungeon dungeon = new Dungeon(1, 4);
        Boulder boulder = new Boulder(1, 1, dungeon);
        dungeon.addEntity(boulder);
        FloorSwitch floorSwitch = new FloorSwitch(1, 2, dungeon);
        dungeon.addEntity(floorSwitch);

        // set up the level goal and perform all attachments
        LevelGoal levelGoal = new LevelGoal(dungeon);
        levelGoal.addSwitchGoal(1);
        floorSwitch.attachToGoal(levelGoal);

        // creating a tree for the goal hierarchy
        SwitchGoal switchGoal = (SwitchGoal) levelGoal.getSwitchGoal();
        TreeLeaf tree = new TreeLeaf(switchGoal);
        levelGoal.addTree(tree);

        // assert incomplete state BEFORE boulder collision with ONE switch
        assertEquals(false, switchGoal.isComplete());
        
        // assert COMPLETE state AFTER boulder collision with ONE switch
        boulder.moveDown();
        boulder.checkCollision();
        assertEquals(true, switchGoal.isComplete());
    }

    @Test
    public void deactivateSwitchTest() {

        // set up dungeon and its entities
        Dungeon dungeon = new Dungeon(1, 4);
        Player player = new Player(dungeon, 1, 2);
        dungeon.addEntity(player);
        Boulder boulder = new Boulder(1, 1, dungeon);
        dungeon.addEntity(boulder);
        FloorSwitch floorSwitch = new FloorSwitch(1, 2, dungeon);
        dungeon.addEntity(floorSwitch);

        // set up the level goal and perform all attachments
        LevelGoal levelGoal = new LevelGoal(dungeon);
        levelGoal.addSwitchGoal(1);
        floorSwitch.attachToGoal(levelGoal);

        // creating a tree for the goal hierarchy
        SwitchGoal switchGoal = (SwitchGoal) levelGoal.getSwitchGoal();
        TreeLeaf tree = new TreeLeaf(switchGoal);
        levelGoal.addTree(tree);

        // assert incomplete state BEFORE boulder collision with ONE switch
        assertEquals(false, switchGoal.isComplete());
        
        // assert COMPLETE state AFTER boulder collision with ONE switch
        boulder.moveDown();
        boulder.checkCollision();
        assertEquals(true, switchGoal.isComplete());

        // assert INCOMPLETE state AFTER player collision with switch
        player.handleCollision(floorSwitch);
        assertEquals(false, switchGoal.isComplete());
    }

    @Test
    public void multipleSwitchTest() {

        // set up dungeon and its entities
        Dungeon dungeon = new Dungeon(1, 4);
        Boulder boulder = new Boulder(1, 2, dungeon);
        dungeon.addEntity(boulder);
        FloorSwitch floorSwitch = new FloorSwitch(1, 3, dungeon);
        dungeon.addEntity(floorSwitch);
        
        Boulder boulder2 = new Boulder(1, 3, dungeon);
        dungeon.addEntity(boulder2);
        FloorSwitch floorSwitch2 = new FloorSwitch(1, 4, dungeon);
        dungeon.addEntity(floorSwitch2);

        // set up the level goal and perform all attachments
        LevelGoal levelGoal = new LevelGoal(dungeon);
        levelGoal.addSwitchGoal(2);
        floorSwitch.attachToGoal(levelGoal);
        floorSwitch2.attachToGoal(levelGoal);

        // creating a tree for the goal hierarchy
        SwitchGoal switchGoal = (SwitchGoal) levelGoal.getSwitchGoal();
        TreeLeaf tree = new TreeLeaf(switchGoal);
        levelGoal.addTree(tree);

        // assert incomplete state BEFORE boulder collision with ONE switch
        assertEquals(false, switchGoal.isComplete());
        
        // assert incomplete state AFTER boulder collision with ONE switch
        boulder.moveDown();
        boulder.checkCollision();
        assertEquals(false, switchGoal.isComplete());
        
        // assert COMPLETE state AFTER boulder collision with TWO switches
        boulder2.moveDown();
        boulder2.checkCollision();
        assertEquals(true, switchGoal.isComplete());
    }

}