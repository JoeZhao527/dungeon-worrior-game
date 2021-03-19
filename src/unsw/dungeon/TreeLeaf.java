package unsw.dungeon;

public class TreeLeaf implements GoalTree {

    private Goal goal;
    private boolean isComplete;

    public TreeLeaf(Goal goal) {
        this.goal = goal;
        this.isComplete = false;
    }

    public String identifyGoal() {
        return this.goal.identifyGoal();
    }

    public Goal getGoal() {
        return this.goal;
    }
    
    public boolean checkCompletion() {
        if (this.goal.isComplete()) {
            this.isComplete = true;
            return true;
        }
        this.isComplete = false;
        return false;
    }

    public void add(GoalTree goalTree) {
        return;
    }

    public boolean isExit() {
        if (this.goal instanceof ExitGoal) {
            return true;
        }
        return false;
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    public boolean canOpenExit() {
        
        if (isExit()) {
            return true;
        }

        return isComplete();
    }

}