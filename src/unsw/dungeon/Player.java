package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */

public class Player extends Entity implements Movable {

    private Dungeon dungeon;
    private Key key = null;
    private Sword sword = null;
    private Potion potion = null;
    private int treasure = 0;
    private String moved = "false";
    private BooleanProperty alive;
    private BooleanProperty hasSword;
    private BooleanProperty hasPotion;
    private BooleanProperty hasKey;
    
    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.alive = new SimpleBooleanProperty(true);
        this.hasSword = new SimpleBooleanProperty(false);
        this.hasPotion = new SimpleBooleanProperty(false);
        this.hasKey = new SimpleBooleanProperty(false);
    }

    public void dead() {
        this.alive.setValue(false);
    }
    public BooleanProperty getAlive() {
        return this.alive;
    }

    public BooleanProperty hasKey() {
        return this.hasKey;
    }

    public void removeKey(Key key) {

        removeEntity((Entity) key);
        setKey(null);
        this.hasKey.setValue(false);
    }

    public BooleanProperty hasPotion() {
        return this.hasPotion;
    }

    public void removeSword(Sword sword) {

        removeEntity(sword);
        setSword(null);
        this.hasSword.setValue(false);
    }

    public void pickupSword(Sword sword) {

        setSword(sword);
        this.hasSword.setValue(true);
    }

    public BooleanProperty hasSword() {
        return this.hasSword;
    }

    public void handleCollision(Entity entity) {
        if (entity instanceof Potion) ((Potion) entity).handleCollision(this);
        if (entity instanceof Sword) ((Sword) entity).handleCollision(this);
        if (entity instanceof Key) ((Key) entity).handleCollision(this);
        if (entity instanceof Portal) ((Portal) entity).transfer((Movable) this);
        if (entity instanceof DoorCollider) ((DoorCollider) entity).openDoor(this);
        if (entity instanceof Enemy) ((Enemy) entity).handleCollision(this);

        if (entity instanceof Treasure) {
            ((Treasure) entity).handleCollision(this);
            this.treasure++;
        }

        if (entity instanceof Boulder) {
            ((Boulder) entity).handleCollision(this);
        }

        if (entity instanceof FloorSwitch) {
            ((FloorSwitch) entity).deactivate();
        }

        if (entity instanceof Exit) {
            ((Exit) entity).handleCollision(this);
        }
    }

    public boolean checkMovable(int x, int y) {
        if (x < 0 || x > dungeon.getWidth() - 1) return false;
        if (y < 0 || y > dungeon.getHeight()- 1) return false;
        List<Entity> entityList = dungeon.getEntity(x, y);
        for (Entity e : entityList) {
            if (e instanceof Wall) return false;
            if (e instanceof Door && ((Door)e).getState() == false) return false;
            if (e instanceof Exit && ((Exit)e).getState() == false) return false;
            if (e instanceof Boulder) {
                switch (lastMove()) {
                case "up":
                    return ((Boulder) e).checkMovable(x, y-1);
                case "down":
                    return ((Boulder) e).checkMovable(x, y+1);
                case "left":
                    return ((Boulder) e).checkMovable(x-1, y);
                case "right":
                    return ((Boulder) e).checkMovable(x+1, y);
                }
            }
        }
        return true;
    }

    public boolean hasMoved() {
        if (this.moved.equals("false")) {
            return false;
        }
        return true;
    }

    public void moveUp() {
        this.moved = "up";
        if (checkMovable(getX(), getY()-1)) {
            y().set(getY() - 1);
        } else {
            this.moved = "false";
        }
    }

    public void moveDown() {
        this.moved = "down";
        if (checkMovable(getX(), getY()+1)) {
            y().set(getY() + 1);
        } else {
            this.moved = "false";
        }
    }

    public void moveLeft() {
        this.moved = "left";
        if (checkMovable(getX()-1, getY())) {
            x().set(getX() - 1);
        } else {
            this.moved = "false";
        }
    }

    public void moveRight() {
        this.moved = "right";
        if (checkMovable(getX()+1, getY())) {
            x().set(getX() + 1);
        } else {
            this.moved = "false";
        }
    }

    public void transferTo(int x, int y) {
        x().set(x);
        y().set(y);
    }

    public String lastMove() {
        return this.moved;
    }

	public Key getKey() {
		return this.key;
	}

	public void removeEntity(Entity c) {
        dungeon.remove((Entity) c);
	}

	public void setKey(Key key) {
        this.key = key;
        this.hasKey.setValue(true);
	}

	public void collectTreasure() {
        this.treasure++;
	}

	public int countTreasure() {
        return this.treasure;
	}

	public Potion getPotion() {
		return this.potion;
	}

    public void setPotion(Potion potion) {
        this.potion = potion;
        if (potion == null) {
            this.hasPotion.setValue(false);
        } else {
            this.hasPotion.setValue(true);
        }
    }
	public void drinkPotion(Potion potion) {
        setPotion(potion);
        System.out.println("became invincible");
        Timer timer = new Timer();
        TimerTask potionEnd = new MyTask(this);
        timer.schedule(potionEnd, (long)5000);
    }

	public Sword getSword() {
        return this.sword;
	}

	public void setSword(Sword sword) {
        this.sword = sword;
    }

    public void useSword() {
        if (this.sword != null) {
            System.out.println("facing "+ this.moved);
            sword.useSword(this);
            List<Entity> entityList = new ArrayList<Entity>();
            for (Entity e : dungeon.getEntity(getX(), getY()-1)) entityList.add(e);
            for (Entity e : dungeon.getEntity(getX(), getY()+1)) entityList.add(e);
            for (Entity e : dungeon.getEntity(getX()-1, getY())) entityList.add(e);
            for (Entity e : dungeon.getEntity(getX()+1, getY())) entityList.add(e);
            for (Entity e : entityList) {
                if (e instanceof Enemy) {
                    ((Enemy)e).getKilled(this);
                }
            }
        }
    }
    
    // for test only
    public void setHasMoved(String s) {
        this.moved = s;
    }
    
    private static class MyTask extends TimerTask{
        Player player;
        public MyTask(Player player) {
            this.player = player;
        }

        public void run() {
            System.out.println("became vulnerable");
            player.removeEntity(player.potion);
            player.setPotion(null);
        }
    }
}

