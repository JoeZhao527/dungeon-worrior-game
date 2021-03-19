package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class EnemyGoal implements Goal {

    private LevelGoal levelGoal;
    private int totalEnemy;
    private int defeatedEnemy;
    private boolean isComplete;
    private BooleanProperty completionObservable;

    public EnemyGoal(int totalEnemy, LevelGoal levelGoal) {
        this.totalEnemy = totalEnemy;
        this.defeatedEnemy = 0;
        this.isComplete = false;
        this.completionObservable = new SimpleBooleanProperty(false);

        this.levelGoal = levelGoal;
    }

    public String identifyGoal() {
        return "Defeat all enemies";
    }
 
    public void update() {
        System.out.println("entered update enemy");
        this.defeatedEnemy++;
        if (this.defeatedEnemy == this.totalEnemy) {
            System.out.println("setting enemy goal as COMPLETE");
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