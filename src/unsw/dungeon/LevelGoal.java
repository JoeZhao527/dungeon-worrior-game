package unsw.dungeon;

import java.util.ArrayList;

public class LevelGoal {
    
    private Dungeon dungeon;
    private ArrayList<Goal> goalList;
    private ExitGoal exitGoal;
    private GoalTree tree;

    public LevelGoal(Dungeon dungeon) {

        this.dungeon = dungeon;
        this.goalList = new ArrayList<>();
        this.exitGoal = null;
        this.tree = null;

        dungeon.addGoals(this);

    }

    public void addExitGoal() {
        if (this.exitGoal != null) {
            return;
        }
        ExitGoal exitGoal = new ExitGoal(this);
        this.exitGoal = exitGoal;
    }

    public void addSwitchGoal(int switchCount) {
        SwitchGoal switchGoal = new SwitchGoal(switchCount, this);
        if (alreadyHasGoal(switchGoal)) {
            return;
        }
        this.goalList.add(switchGoal);
    }

    public void addTreasureGoal(int treasureCount) {
        TreasureGoal treasureGoal = new TreasureGoal(treasureCount, this);
        if (alreadyHasGoal(treasureGoal)) {
            return;
        }
        this.goalList.add(treasureGoal);
    }

    public void addEnemyGoal(int enemyCount) {
        EnemyGoal enemyGoal = new EnemyGoal(enemyCount, this);
        if (alreadyHasGoal(enemyGoal)) {
            return;
        }
        this.goalList.add(enemyGoal);
    }

    private boolean alreadyHasGoal(Goal goal) {
        System.out.println("goal type to check");
        System.out.println(goal.getClass().toString());
        for (Goal g : this.goalList) {
            System.out.println("looping through goalList");
            System.out.println(g.getClass().toString());
            if (g.getClass() == goal.getClass()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Goal> getGoals() {
        return this.goalList;
    }

    public Goal getExitGoal() {
        return this.exitGoal;
    }

    public Goal getSwitchGoal() {
        for (Goal g : this.goalList) {
            if (g instanceof SwitchGoal) {
                return g;
            }
        }
        return null;
    }

    public Goal getTreasureGoal() {
        for (Goal g : this.goalList) {
            if (g instanceof TreasureGoal) {
                return g;
            }
        }
        return null;
    }

    public Goal getEnemyGoal() {
        for (Goal g : this.goalList) {
            if (g instanceof EnemyGoal) {
                return g;
            }
        }
        return null;
    }

    public void addTree(GoalTree tree) {
        this.tree = tree;
    }

    public GoalTree getTree() {
        return this.tree;
    }

    public void checkCompletion() {

        if (this.tree.checkCompletion()) {
            markComplete();
        }
        attemptOpenExit();
    }

    public void openExit() {
        System.out.println("opening exit");
        this.exitGoal.openExit();
    }

    public void closeExit() {

        if (this.exitGoal == null) {
            return;
        }
        System.out.println("closing exit");
        this.exitGoal.closeExit();
    }

    public void attemptOpenExit() {
        // if there is no exit, do nothing
        if (exitGoal == null) {
            return;
        }

        // scan through tree to see if opening it is valid
        if (this.tree.canOpenExit()) {
            openExit();
            return;
        }
        closeExit();
    }

    public void markComplete() {
        this.dungeon.completeDungeon();
    }

}