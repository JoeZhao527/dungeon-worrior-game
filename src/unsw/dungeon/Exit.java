package unsw.dungeon;

public class Exit extends Entity {

    private ExitGoal exitGoal;
    private boolean isOpened;

    public Exit(int x, int y, Dungeon dungeon) {
        super(x, y);
        this.isOpened = false;
    }

    public void attachToGoal(LevelGoal levelGoal) {
        ExitGoal eGoal = (ExitGoal) levelGoal.getExitGoal();
        eGoal.attachExit(this);
        this.exitGoal = eGoal;
        return;
    }

    public void open() {
        this.isOpened = true;
    }

    public void close() {
        this.isOpened = false;
    }

    public boolean getState() {
        return isOpened;
    }
    
    public void handleCollision(Player player) {

        System.out.println("entered exit");
        this.exitGoal.update();
    }

}
