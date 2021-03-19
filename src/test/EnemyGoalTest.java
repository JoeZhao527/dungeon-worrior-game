package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class EnemyGoalTest {

    @Test
    public void basicGoalTest() {

        // set up dungeon and its entities
        Dungeon dungeon = new Dungeon(1, 4);
        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);
        Enemy enemy = new Enemy(1, 2, dungeon);
        dungeon.addEntity(enemy);

        // set up the level goal and perform all attachments
        LevelGoal levelGoal = new LevelGoal(dungeon);
        levelGoal.addEnemyGoal(1);
        enemy.attachToGoal(levelGoal);

        // creating a tree for the goal hierarchy
        EnemyGoal enemyGoal = (EnemyGoal) levelGoal.getEnemyGoal();
        TreeLeaf tree = new TreeLeaf(enemyGoal);
        levelGoal.addTree(tree);

        // assert incomplete state BEFORE enemy dies
        assertEquals(false, enemyGoal.isComplete());
        
        // assert COMPLETE state AFTER enemy dies
        enemy.getKilled(player);
        assertEquals(true, enemyGoal.isComplete());
    }

    @Test
    public void multipleEnemyTest() {

        // set up dungeon and its entities
        Dungeon dungeon = new Dungeon(1, 4);
        Player player = new Player(dungeon, 1, 1);
        dungeon.addEntity(player);
        Enemy enemy = new Enemy(1, 2, dungeon);
        dungeon.addEntity(enemy);
        Enemy enemy2 = new Enemy(1, 3, dungeon);
        dungeon.addEntity(enemy2);

        // set up the level goal and perform all attachments
        LevelGoal levelGoal = new LevelGoal(dungeon);
        levelGoal.addEnemyGoal(2);
        enemy.attachToGoal(levelGoal);
        enemy2.attachToGoal(levelGoal);

        // creating a tree for the goal hierarchy
        EnemyGoal enemyGoal = (EnemyGoal) levelGoal.getEnemyGoal();
        TreeLeaf tree = new TreeLeaf(enemyGoal);
        levelGoal.addTree(tree);

        // assert incomplete state BEFORE ONE enemy dies
        assertEquals(false, enemyGoal.isComplete());
        
        // assert incomplete state AFTER ONE enemy dies
        enemy.getKilled(player);
        assertEquals(false, enemyGoal.isComplete());
        
        // assert COMPLETE state AFTER TWO enemy dies
        enemy2.getKilled(player);
        assertEquals(true, enemyGoal.isComplete());
    }

}