package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class TreasureGoalTest {

    @Test
    public void basicGoalTest() {

        // set up dungeon and its entities
        Dungeon dungeon = new Dungeon(1, 4);
        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);
        Treasure treasure = new Treasure(1, 2, dungeon);
        dungeon.addEntity(treasure);

        // set up the level goal and perform all attachments
        LevelGoal levelGoal = new LevelGoal(dungeon);
        levelGoal.addTreasureGoal(1);
        treasure.attachToGoal(levelGoal);

        // creating a tree for the goal hierarchy
        TreasureGoal treasureGoal = (TreasureGoal) levelGoal.getTreasureGoal();
        TreeLeaf tree = new TreeLeaf(treasureGoal);
        levelGoal.addTree(tree);

        // assert incomplete state BEFORE collision with ONE treasure
        assertEquals(false, treasureGoal.isComplete());
        
        // assert COMPLETE state AFTER collision with ONE treasure
        player.handleCollision(treasure);
        assertEquals(true, treasureGoal.isComplete());
    }

    @Test
    public void multipleTreasureTest() {

        // set up dungeon and its entities
        Dungeon dungeon = new Dungeon(1, 4);
        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);
        Treasure treasure = new Treasure(1, 2, dungeon);
        dungeon.addEntity(treasure);
        Treasure treasure2 = new Treasure(1, 3, dungeon);
        dungeon.addEntity(treasure2);

        // set up the level goal and perform all attachments
        LevelGoal levelGoal = new LevelGoal(dungeon);
        levelGoal.addTreasureGoal(2);
        treasure.attachToGoal(levelGoal);
        treasure2.attachToGoal(levelGoal);

        // creating a tree for the goal hierarchy
        TreasureGoal treasureGoal = (TreasureGoal) levelGoal.getTreasureGoal();
        TreeLeaf tree = new TreeLeaf(treasureGoal);
        levelGoal.addTree(tree);

        // assert incomplete state BEFORE collision with ONE treasure
        assertEquals(false, treasureGoal.isComplete());
        
        // assert incomplete state AFTER collision with ONE treasure
        player.handleCollision(treasure);
        assertEquals(false, treasureGoal.isComplete());

        // assert COMPLETE state AFTER collision with TWO treasures
        player.handleCollision(treasure2);
        assertEquals(true, treasureGoal.isComplete());
    }
}