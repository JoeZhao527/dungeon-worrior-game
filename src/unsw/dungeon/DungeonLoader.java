package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;
    private Portal correspond = null;
    private int doorId = 0;
    private int keyId = 0;
    private int switchCount = 0;
    private int enemyCount = 0;
    private int treasureCount = 0;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }

        LevelGoal levelGoal = new LevelGoal(dungeon);

        JSONObject jsonGoals = json.getJSONObject("goal-condition");
        loadGoals(levelGoal, jsonGoals);

        dungeon.addGoals(levelGoal);
        dungeon.attachGoals();

        System.out.println("constructing tree...");
        GoalTree tree = structureGoals(levelGoal, jsonGoals);
        System.out.println("... finished the tree!");
        levelGoal.addTree(tree);

        levelGoal.attemptOpenExit();

        return dungeon;
    }

    private void loadGoals(LevelGoal levelGoal, JSONObject json) {
        String goal = json.getString("goal");

        switch (goal) {
        case "AND":
            JSONArray subgoals = json.getJSONArray("subgoals");
        
            for (int i = 0; i < subgoals.length(); i++) {
                loadGoals(levelGoal, subgoals.getJSONObject(i));
            }
            break;
        case "OR":
            JSONArray subgoals2 = json.getJSONArray("subgoals");
        
            for (int i = 0; i < subgoals2.length(); i++) {
                loadGoals(levelGoal, subgoals2.getJSONObject(i));
            }
            break;
        case "exit":
            levelGoal.addExitGoal();
            break;
        case "boulders":
            levelGoal.addSwitchGoal(this.switchCount);
            break;
        case "treasure":
            levelGoal.addTreasureGoal(this.treasureCount);
            break;
        case "enemies":
            levelGoal.addEnemyGoal(this.enemyCount);
            break;
        }

    }

    private GoalTree structureGoals(LevelGoal levelGoal, JSONObject json) {

        GoalTree tree = null;
        String goal = json.getString("goal");

        switch (goal) {
        case "AND":
            System.out.println("processing AND");
            TreeAND treeAnd = new TreeAND();

            JSONArray subgoals = json.getJSONArray("subgoals");
        
            for (int i = 0; i < subgoals.length(); i++) {
                GoalTree temp = structureGoals(levelGoal, subgoals.getJSONObject(i));
                treeAnd.add(temp);
            }

            tree = treeAnd;
            break;

        case "OR":
            System.out.println("processing OR");
            TreeOR treeOr = new TreeOR();

            JSONArray subgoals2 = json.getJSONArray("subgoals");
        
            for (int i = 0; i < subgoals2.length(); i++) {
                GoalTree temp = structureGoals(levelGoal, subgoals2.getJSONObject(i));
                treeOr.add(temp);
            }

            tree = treeOr;
            break;

        case "exit":
            System.out.println("processing EXIT");
            Goal exitGoal = levelGoal.getExitGoal();
            tree = new TreeLeaf(exitGoal);
            break;

        case "boulders":
            System.out.println("processing SWITCH");
            Goal SwitchGoal = levelGoal.getSwitchGoal();
            tree = new TreeLeaf(SwitchGoal);
            break;

        case "treasure":
            System.out.println("processing TREASURE");
            Goal TreasureGoal = levelGoal.getTreasureGoal();
            tree = new TreeLeaf(TreasureGoal);
            break;

        case "enemies":
            System.out.println("processing ENEMIES");
            Goal EnemyGoal = levelGoal.getEnemyGoal();
            tree = new TreeLeaf(EnemyGoal);
            break;

        }

        return tree;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        
        Entity entity = null;
        switch (type) {

        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;

        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;

        case "portal":
            Portal portal = new Portal(x,y);
            onLoad(portal);
            if (correspond == null) {
                correspond = portal;
            } else {
                portal.addCorresponding(correspond);
                correspond = null;
            }
            entity = portal;
            break;

        case "sword":
            Sword sword = new Sword(x, y, 5);
            onLoad(sword);
            entity = sword;
            break;

        case "key":
            Key key = new Key(x, y, keyId);
            keyId++;
            onLoad(key);
            entity = key;
            break;

        case "door":
            Door door = new Door(dungeon,x,y,doorId);
            doorId++;
            onLoad(door);
            entity = door;
            break;

        case "invincibility":
            Potion potion = new Potion(x,y);
            onLoad(potion);
            entity = potion;
            break;

        case "exit":
            Exit exit = new Exit(x, y, dungeon);
            onLoad(exit);
            entity = exit;
            break;

        case "treasure":
            Treasure treasure = new Treasure(x, y, dungeon);
            treasureCount++;
            onLoad(treasure);
            entity = treasure;
            break;
        
        case "switch":
            FloorSwitch floorSwitch = new FloorSwitch(x, y, dungeon);
            switchCount++;
            onLoad(floorSwitch);
            entity = floorSwitch;
            break;

        case "boulder":
            Boulder boulder = new Boulder(x, y, dungeon);
            onLoad(boulder);
            entity = boulder;
            break;

        case "enemy":
            Enemy enemy = new Enemy(x, y, dungeon);
            enemyCount++;
            onLoad(enemy);
            entity = enemy;
            break;
        }

        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(Key key);

    public abstract void onLoad(Potion potion);

    public abstract void onLoad(Door door);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(FloorSwitch floorswitch);

    public abstract void onLoad(Enemy enemy);
}
