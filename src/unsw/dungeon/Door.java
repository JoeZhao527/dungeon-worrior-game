package unsw.dungeon;

/**
 * 
 * Exists in conjunction with a single key that can open it. 
 * If the player holds the key, they can open the door by moving through it. 
 * Once open it remains so. The client will be satisfied if dungeons can be made with up to 3 doors.
 * @author Haokai Zhao
 *
 */

public class Door extends Entity {
    boolean isOpened = false;
    Dungeon dungeon;
    int id;
    public Door(Dungeon dungeon, int x, int y, int id) {
        super(x,y);
        this.dungeon = dungeon;
        this.id = id;
        // super.setImage(new Image((new File("images/closed_door.png")).toURI().toString()));
        createCollider();
    }

    public void open() {
        //super.changeImage(new Image((new File("images/open_door.png")).toURI().toString()));
        super.getExistence().setValue(false);
        this.isOpened = true;
    }

    public boolean getState() {
        return isOpened;
    }

    public void createCollider() {
        if (getY() > 0) {
            DoorCollider up = new DoorCollider(getX(), getY() - 1, this);
            this.dungeon.addEntity(up);
        }
        if (getY() < dungeon.getHeight() - 1) {
            DoorCollider down = new DoorCollider(getX(), getY() + 1, this);
            this.dungeon.addEntity(down);
        }
        if (getX() > 0) {
            DoorCollider left = new DoorCollider(getX() - 1, getY(), this);
            this.dungeon.addEntity(left);
        }
        if (getX() < dungeon.getWidth() - 1) {
            DoorCollider right = new DoorCollider(getX() + 1, getY(), this);
            this.dungeon.addEntity(right);
        }
    }

    public int getId() {
        return this.id;
    }
}