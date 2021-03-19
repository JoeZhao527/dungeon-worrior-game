package unsw.dungeon;

public class Treasure extends Collectables {

    private boolean collected;
    private TreasureGoal treasureGoal;

    public Treasure(int x, int y, Dungeon dungeon) {
        super(x,y);
        this.collected = false;
    }

    public void attachToGoal(LevelGoal levelGoal) {
        for (Goal g : levelGoal.getGoals()) {
            if (g instanceof TreasureGoal) {
                TreasureGoal treasureGoal = (TreasureGoal) g;
                this.treasureGoal = treasureGoal;
                return;
            }
        }
    }
    
    public void handleCollision(Player player) {
        if (isCollected()) {
            return;
        }

        player.collectTreasure();
        System.out.println("collected treasure");
        super.getExistence().setValue(false);
        player.removeEntity(this);
        setCollected();
        this.treasureGoal.update();
    }

    private void setCollected() {
        this.collected = true;
    }

    private boolean isCollected() {
        return this.collected;
    }
    
}