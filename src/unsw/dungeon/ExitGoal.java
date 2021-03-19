package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ExitGoal implements Goal {
    
    private LevelGoal levelGoal;
    private Exit exit;
    private boolean isComplete;

    public ExitGoal(LevelGoal levelGoal) {
        this.levelGoal = levelGoal;
        this.exit = null;
        this.isComplete = false;
    }

    public String identifyGoal() {
        return "Reach the exit";
    }

    public void attachExit(Exit exit) {
        this.exit = exit;
    }

    public void openExit() {
        this.exit.open();
    }

    public void closeExit() {
        this.exit.close();
    }

    public void update() {
        System.out.println("entered update exit");

        this.isComplete = true;
    
        this.levelGoal.markComplete();
    }

    public BooleanProperty monitorCompletion() {
        return new SimpleBooleanProperty(true);
    }

    public void checkCompletion() {
        this.levelGoal.checkCompletion();
    }

    public boolean isComplete() {
        return this.isComplete;
    }

}