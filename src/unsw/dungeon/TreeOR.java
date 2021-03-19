package unsw.dungeon;

import java.util.ArrayList;

public class TreeOR implements GoalTree {

    private ArrayList<GoalTree> treeList;

    public TreeOR() {
        this.treeList = new ArrayList<>();
    }

    public ArrayList<GoalTree> getList() {
        return this.treeList;
    }
    
    public boolean checkCompletion() {
        boolean check = false;
        for (GoalTree t : this.treeList) {
            if (t.checkCompletion()) {
                check = true;
            }
        }
        return check;
    }

    public void add(GoalTree tree) {
        this.treeList.add(tree);
    }

    public boolean canOpenExit() {
        for (GoalTree t : this.treeList) {
            if (t.canOpenExit()) {
                return true;
            }
        }
        return false;
    }    
}