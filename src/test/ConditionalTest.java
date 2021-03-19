package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class ConditionalTest {

    @Test
    public void exitANDTreasureTest() {

        // set up dungeon and its entities
        Dungeon dungeon = new Dungeon(1, 3);
        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);
        Treasure treasure = new Treasure(1, 2, dungeon);
        dungeon.addEntity(treasure);
        Exit exit = new Exit(1, 3, dungeon);
        dungeon.addEntity(exit);

        // set up the level goals and perform all attachments
        LevelGoal levelGoal = new LevelGoal(dungeon);
        levelGoal.addExitGoal();
        levelGoal.addTreasureGoal(1);
        exit.attachToGoal(levelGoal);
        treasure.attachToGoal(levelGoal);

        // creating a tree for the goal hierarchy
        TreeAND treeAND = new TreeAND();

        ExitGoal exitGoal = (ExitGoal) levelGoal.getExitGoal();
        TreeLeaf treeExit = new TreeLeaf(exitGoal);
        TreasureGoal treasureGoal = (TreasureGoal) levelGoal.getTreasureGoal();
        TreeLeaf treeTreasure = new TreeLeaf(treasureGoal);

        treeAND.add(treeExit);
        treeAND.add(treeTreasure);

        levelGoal.addTree(treeAND);

        // attempt to open exit door if conditions are met
        levelGoal.attemptOpenExit();

        // assert CLOSED exit state before collision with treasure
        assertEquals(false, exit.getState());

        // assert OPENED exit state after collision with treasure
        player.handleCollision(treasure);
        assertEquals(true, exit.getState());
    }

    @Test
    public void exitORTreasureTest() {
        // IDENTICAL TO ABOVE, EXCEPT SUBSITUTE AND WITH OR

        // set up dungeon and its entities
        Dungeon dungeon = new Dungeon(1, 3);
        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);
        Treasure treasure = new Treasure(1, 2, dungeon);
        dungeon.addEntity(treasure);
        Exit exit = new Exit(1, 3, dungeon);
        dungeon.addEntity(exit);

        // set up the level goals and perform all attachments
        LevelGoal levelGoal = new LevelGoal(dungeon);
        levelGoal.addExitGoal();
        levelGoal.addTreasureGoal(1);
        
        exit.attachToGoal(levelGoal);
        treasure.attachToGoal(levelGoal);

        // creating a tree for the goal hierarchy
        TreeOR treeOR = new TreeOR();

        ExitGoal exitGoal = (ExitGoal) levelGoal.getExitGoal();
        TreeLeaf treeExit = new TreeLeaf(exitGoal);
        TreasureGoal treasureGoal = (TreasureGoal) levelGoal.getTreasureGoal();
        TreeLeaf treeTreasure = new TreeLeaf(treasureGoal);

        treeOR.add(treeExit);
        treeOR.add(treeTreasure);

        levelGoal.addTree(treeOR);

        // attempt to open exit door if conditions are met
        levelGoal.attemptOpenExit();

        // assert OPENED exit state before collision with treasure
        assertEquals(true, exit.getState());

        // assert dungeon COMPLETE after collision with treasure
        player.handleCollision(treasure);
        assertEquals(true, dungeon.isComplete());
    }

    @Test
    public void andOrOrTest() {
        // (boulders OR treasure) AND (enemy OR exit)

        // set up dungeon and its entities
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon, 0, 0);
        Enemy enemy = new Enemy(1, 1, dungeon);
        Treasure treasure = new Treasure(2, 2, dungeon);
        Boulder boulder = new Boulder(4, 4, dungeon);
        FloorSwitch floorSwitch = new FloorSwitch(4, 4, dungeon);
        Exit exit = new Exit(5, 5, dungeon);

        dungeon.addEntity(player);
        dungeon.addEntity(enemy);
        dungeon.addEntity(treasure);
        dungeon.addEntity(exit);
        dungeon.addEntity(boulder);
        dungeon.addEntity(floorSwitch);

        // set up the level goals and perform all attachments
        LevelGoal levelGoal = new LevelGoal(dungeon);
        levelGoal.addEnemyGoal(1);
        levelGoal.addTreasureGoal(1);
        levelGoal.addSwitchGoal(1);
        levelGoal.addExitGoal();

        enemy.attachToGoal(levelGoal);
        treasure.attachToGoal(levelGoal);
        floorSwitch.attachToGoal(levelGoal);
        exit.attachToGoal(levelGoal);

        // creating a tree for the goal hierarchy
        // boulder OR treasure
        TreeOR st = new TreeOR();

        SwitchGoal switchGoal = (SwitchGoal) levelGoal.getSwitchGoal();
        TreeLeaf treeSwitch = new TreeLeaf(switchGoal);

        TreasureGoal treasureGoal = (TreasureGoal) levelGoal.getTreasureGoal();
        TreeLeaf treeTreasure = new TreeLeaf(treasureGoal);

        st.add(treeSwitch);
        st.add(treeTreasure);

        // enemy OR exit
        TreeOR ee = new TreeOR();

        EnemyGoal enemyGoal = (EnemyGoal) levelGoal.getEnemyGoal();
        TreeLeaf treeEnemy = new TreeLeaf(enemyGoal);

        ExitGoal ExitGoal = (ExitGoal) levelGoal.getExitGoal();
        TreeLeaf treeExit = new TreeLeaf(ExitGoal);

        ee.add(treeEnemy);
        ee.add(treeExit);

        // create AND structure and attach
        TreeAND tree = new TreeAND();
        tree.add(st);
        tree.add(ee);

        levelGoal.addTree(tree);

        // attempt to open exit door if conditions are met
        levelGoal.attemptOpenExit();

        // assert CLOSED exit state as it is in an AND
        assertEquals(false, exit.getState());

        // assert OPEN exit state AFTER boulder collision with floorSwitch
        boulder.checkCollision();
        assertEquals(true, exit.getState());
        assertEquals(false, dungeon.isComplete());

        // assert dungeon COMPLETE AFTER player collision with exit
        player.handleCollision(exit);
        assertEquals(true, dungeon.isComplete());
    }
}