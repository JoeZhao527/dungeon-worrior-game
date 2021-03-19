package unsw.dungeon;

import java.util.ArrayList;

public class TreeAND implements GoalTree {

    private ArrayList<GoalTree> treeList;

    public TreeAND() {
        this.treeList = new ArrayList<>();
    }

    public ArrayList<GoalTree> getList() {
        return this.treeList;
    }
    
    public boolean checkCompletion() {
        boolean check = true;
        for (GoalTree t : this.treeList) {
            if (!t.checkCompletion()) {
                check = false;
            }
        }
        return check;
    }

    public void add(GoalTree tree) {
        this.treeList.add(tree);
    }

    public boolean canOpenExit() {
        for (GoalTree t : this.treeList) {
            if (!t.canOpenExit()) {
                return false;
            } 
        }
        return true;
    }    
}