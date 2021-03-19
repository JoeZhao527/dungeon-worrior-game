package unsw.dungeon;

public class FloorSwitch extends Entity {

    private SwitchGoal switchGoal;
    private boolean active;

    public FloorSwitch(int x, int y, Dungeon dungeon) {
        super(x, y);
        this.active = false;
    }

    public void attachToGoal(LevelGoal levelGoal) {
        for (Goal g : levelGoal.getGoals()) {
            if (g instanceof SwitchGoal) {
                SwitchGoal switchGoal = (SwitchGoal) g;
                switchGoal.addSwitch(this);
                this.switchGoal = switchGoal;
                return;
            }
        }
    }

    public boolean isActive() {
        return this.active;
    }
    
    public void activate() {
        System.out.println("activating switch");
        this.active = true;
        if (this.switchGoal != null) {
            this.switchGoal.update();
        }
    }

    public void deactivate() {
        System.out.println("deactivating switch");
        this.active = false;
        if (this.switchGoal != null) {
            this.switchGoal.update();
        }
    }
}
