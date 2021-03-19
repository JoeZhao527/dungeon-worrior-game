package unsw.dungeon;

import java.util.List;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Enemy extends Entity implements Movable{
    private Dungeon dungeon;
    private EnemyGoal enemyGoal;
    private int direction;
    private boolean moved;
    private Timeline tl;

    public Enemy(int x, int y, Dungeon dungeon) {
        super(x, y);
        this.dungeon = dungeon;
        this.direction = 0;
        this.moved = true;
        tl = new Timeline(new KeyFrame(Duration.seconds(0.3), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                enemyMove();
            }
        }));
        if (tl != null) {
            tl.setCycleCount(Timeline.INDEFINITE);
            tl.play();
        }
    }

    private void enemyMove() {
        CalculatePath c = new CalculatePath(dungeon.getPlayer(), this, dungeon);
        int direction = c.nextStep();
        if (direction != -1) {
            if (direction == 0) moveUp();
            else if (direction == 1) moveDown();
            else if (direction == 2) moveLeft();
            else moveRight();
        }
    }

    public void attachToGoal(LevelGoal levelGoal) {
        for (Goal g : levelGoal.getGoals()) {
            if (g instanceof EnemyGoal) {
                EnemyGoal enemygoal = (EnemyGoal) g;
                this.enemyGoal = enemygoal;
                return;
            }
        }
    }

    public void handleCollision(Player player) {
        if (player.getPotion() != null) {
            enemyDied(player);
        } else {
            player.dead();
            System.out.println("player's dead");
            tl.stop();
        }
    }
    public void stop() {
        if (tl != null) {
            tl.stop();
        }
    }
    public void getKilled(Player player) {
        enemyDied(player);
    }

    @Override
    public void moveUp() {
        if (checkMovable(getX(), getY()-1)) {
            y().set(getY() - 1);
            setMoved();
        }
    }

    @Override
    public void moveDown() {
        if (checkMovable(getX(), getY()+1)) {
            y().set(getY() + 1);
            setMoved();
        }
    }

    @Override
    public void moveLeft() {
        if (checkMovable(getX()-1, getY())) {
            x().set(getX() - 1);
            setMoved();
        }
    }

    @Override
    public void moveRight() {
        if (checkMovable(getX()+1, getY())) {
            x().set(getX() + 1);
            setMoved();
        }
    }
    
    @Override
    public boolean hasMoved() {
        return this.moved;
    }

    public void setMoved() {
        this.moved = true;
        for(Entity e : dungeon.getEntity(this.getX(), this.getY())) {
            if (e instanceof Player) handleCollision((Player)e);
        }
    }
    
    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int d) {
        this.direction = d;
	}
    
    @Override
    public boolean checkMovable(int x, int y) {
        this.moved = false;
        if (x < 0 || x > dungeon.getWidth() - 1) return false;
        if (y < 0 || y > dungeon.getHeight()- 1) return false;
        List<Entity> entityList = dungeon.getEntity(x, y);
        for (Entity e : entityList) {
            if (e instanceof Wall) return false;
            if (e instanceof Door && ((Door)e).getState() == false) return false;
            if (e instanceof Exit) return false;
            if (e instanceof Boulder) return false;
            if (e instanceof Portal) return false;
        }
        this.moved = true;
        return true;
    }

    @Override
    public void transferTo(int x, int y) {
        x().set(x);
        y().set(y);
    }

    private void enemyDied(Player player) {
        super.getExistence().setValue(false);
        tl.stop();
        player.removeEntity(this);
        tl.stop();
        if (enemyGoal != null) {
            enemyGoal.update();
        }
    }
}