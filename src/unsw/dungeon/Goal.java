package unsw.dungeon;

import javafx.beans.property.BooleanProperty;

public interface Goal {

    public void update();
    public boolean isComplete();
    public void checkCompletion();
    public String identifyGoal();
    public BooleanProperty monitorCompletion();

}