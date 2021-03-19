package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TreasureGoal implements Goal {
    
    private LevelGoal levelGoal;
    private int totalTreasure;
    private int collectedTreasure;
    private boolean isComplete;
    private BooleanProperty completionObservable;

    public TreasureGoal(int totalTreasure, LevelGoal levelGoal) {
        this.totalTreasure = totalTreasure;
        this.collectedTreasure = 0;
        this.isComplete = false;
        this.completionObservable = new SimpleBooleanProperty(false);

        this.levelGoal = levelGoal;
    }

    public String identifyGoal() {
        return "Collect all treasures";
    }

    public void update() {
        System.out.println("entered update treasure");

        this.collectedTreasure++;
        if (this.collectedTreasure == this.totalTreasure) {
            System.out.println("setting treasure goal as COMPLETE");
            this.isComplete = true;
            this.completionObservable.setValue(true);
        }
    
        checkCompletion();
    }

    public BooleanProperty monitorCompletion() {
        return this.completionObservable;
    }

    public void checkCompletion() {
        this.levelGoal.checkCompletion();
    }

    public boolean isComplete() {
        return this.isComplete;
    }

}