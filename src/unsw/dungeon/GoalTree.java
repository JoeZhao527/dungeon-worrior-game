package unsw.dungeon;

public interface GoalTree {
    
    public boolean checkCompletion();
    public void add(GoalTree goalTree);
    public boolean canOpenExit();
    
}