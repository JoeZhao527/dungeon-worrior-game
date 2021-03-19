/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private LevelGoal levelGoal;
    private boolean completed;
    private BooleanProperty completedProperty;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.levelGoal = null;
        this.completed = false;
        this.completedProperty = new SimpleBooleanProperty(false);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void addGoals(LevelGoal levelGoal) {

        System.out.println("entered dungeon.addgoals");
        this.levelGoal = levelGoal;

        LevelGoal print = this.levelGoal;
        for (Goal g : print.getGoals()) {
            System.out.println(g.getClass().toString());
        }
    }

    public ArrayList<Goal> getGoals() {
        return levelGoal.getGoals();
    }

    public GoalTree getTree() {
        return levelGoal.getTree();
    }

    public void completeDungeon() {
        System.out.println("marking dungeon as complete");
        this.completed = true;
        this.completedProperty.setValue(true);
    }

    public boolean isComplete() {
        return this.completed;
    }
    public BooleanProperty getCompleteProperty() {
        return this.completedProperty;
    }
	public void remove(Entity e) {
        entities.remove(e);
	}

    public List<Entity> getAllEntities() {
        return this.entities;
    }

	public List<Entity> getEntity(int x, int y) {
        List<Entity> result = new ArrayList<>();
        for (Entity e : entities) {
            if (e == null) continue;
            if (e.getX() == x && e.getY() == y) result.add(e);
        }
		return result;
    }
    
    public void attachGoals() {
        for (Entity e : this.entities) {
            if (e instanceof Treasure ) {
                Treasure treasure = (Treasure) e;
                treasure.attachToGoal(levelGoal);
            } else if (e instanceof FloorSwitch) {
                FloorSwitch floorSwitch = (FloorSwitch) e;
                floorSwitch.attachToGoal(levelGoal);
            } else if (e instanceof Exit) {
                Exit exit = (Exit) e;
                exit.attachToGoal(levelGoal);
            } else if (e instanceof Enemy) {
                Enemy enemy = (Enemy) e;
                enemy.attachToGoal(levelGoal);
            }
        }
    }
}
