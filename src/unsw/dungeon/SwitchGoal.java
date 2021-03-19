package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class SwitchGoal implements Goal {
    
    private LevelGoal levelGoal;
    private ArrayList<FloorSwitch> activeSwitches;
    private boolean isComplete;
    private BooleanProperty completionObservable;

    public SwitchGoal(int totalSwitch, LevelGoal levelGoal) {
        this.activeSwitches = new ArrayList<>();
        this.isComplete = false;
        this.completionObservable = new SimpleBooleanProperty(false);

        this.levelGoal = levelGoal;
    }

    public String identifyGoal() {
        return "Activate all switches with boulders";
    }

    public void addSwitch(FloorSwitch floorSwitch) {
        this.activeSwitches.add(floorSwitch);
    }

    public void update() {
        for (FloorSwitch f : this.activeSwitches) {
            if (!f.isActive()) {
                System.out.println("setting switchgoal as UNCOMPLETE");
                this.isComplete = false;
                this.completionObservable.setValue(false);
                checkCompletion();
                return;
            }
        }
        
        this.isComplete = true;
        this.completionObservable.setValue(true);
        System.out.println("setting switchgoal as COMPLETE");
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