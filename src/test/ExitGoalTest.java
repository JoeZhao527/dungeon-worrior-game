package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class ExitGoalTest {

    @Test
    public void basicGoalTest() {

        // set up dungeon and its entities
        Dungeon dungeon = new Dungeon(1, 2);
        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);
        Exit exit = new Exit(1, 2, dungeon);
        dungeon.addEntity(exit);

        // set up the level goal and perform all attachments
        LevelGoal levelGoal = new LevelGoal(dungeon);
        levelGoal.addExitGoal();        
        exit.attachToGoal(levelGoal);

        // creating a tree for the goal hierarchy
        ExitGoal exitGoal = (ExitGoal) levelGoal.getExitGoal();
        TreeLeaf tree = new TreeLeaf(exitGoal);
        levelGoal.addTree(tree);

        // assert incomplete state before collision
        assertEquals(false, exitGoal.isComplete());

        // assert complete state after collision
        player.handleCollision(exit);
        assertEquals(true, exitGoal.isComplete());
    }

}