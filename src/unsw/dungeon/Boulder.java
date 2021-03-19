
package unsw.dungeon;

import java.util.List;

public class Boulder extends Entity implements Movable {

    private Dungeon dungeon;
    private String moved = "false";
 
    public Boulder(int x, int y, Dungeon dungeon) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public boolean checkMovable(int x, int y) {
        if (x < 0 || x > dungeon.getWidth() - 1) return false;
        if (y < 0 || y > dungeon.getHeight()- 1) return false;
        List<Entity> entityList = dungeon.getEntity(x, y);
        for (Entity e : entityList) {
            if (e instanceof Wall) return false;
            if (e instanceof Door && ((Door)e).getState() == false) return false;
            if (e instanceof Exit) return false;
            if (e instanceof Boulder) return false;
            if (e instanceof Collectables) return false;

        }
        return true;
    }

    public void handleCollision(Player player) {
        switch (player.lastMove()) {
        case "up":
            this.moveUp();
            checkCollision();
            return;
        case "down":
            this.moveDown();
            checkCollision();
            return;
        case "left":
            this.moveLeft();
            checkCollision();
            return;
        case "right":
            this.moveRight(); 
            checkCollision(); 
            return;          
        }
    }

    public void checkCollision() {
        for (Entity e : dungeon.getEntity(this.getX(), this.getY())) {
            if (e instanceof Portal) ((Portal) e).transfer((Movable) this);
            if (e instanceof FloorSwitch) ((FloorSwitch) e).activate();;
        }
    }

    public boolean hasMoved() {
        if (this.moved.equals("false")) {
            return false;
        }
        return true;
    }

    public void moveUp() {
        y().set(getY() - 1);
        this.moved = "up";
    }

    public void moveDown() {
        y().set(getY() + 1);
        this.moved = "down";
    }

    public void moveLeft() {
        x().set(getX() - 1);
        this.moved = "left";
    }

    public void moveRight() {
        x().set(getX() + 1);
        this.moved = "right";
    }

    public void transferTo(int x, int y) {
        x().set(x);
        y().set(y);
    }

    // for test only
    public void setHasMoved(String s) {
        this.moved = s;
    }
}