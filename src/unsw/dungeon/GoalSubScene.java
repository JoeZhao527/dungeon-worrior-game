package unsw.dungeon;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GoalSubScene extends SubScene {

    private VBox vbox;
    private ArrayList<Text> exitText;
    private ArrayList<Text> enemyText;
    private ArrayList<Text> treasureText;
    private ArrayList<Text> switchText;
    
    public GoalSubScene() {
        super(new AnchorPane(), 350, 150);

        System.out.println("entered GoalSubScene constructor");

        AnchorPane root = (AnchorPane) this.getRoot();

        this.vbox = new VBox();

        root.getChildren().add(vbox);
        AnchorPane.setLeftAnchor(vbox, (double) 0);
        AnchorPane.setTopAnchor(vbox, (double) 0);

        this.setFill(Color.TRANSPARENT);

        root.setBackground(Background.EMPTY);

        this.exitText = new ArrayList<>();
        this.enemyText = new ArrayList<>();
        this.treasureText = new ArrayList<>();
        this.switchText = new ArrayList<>();
    }

    public void updateGoal(boolean bool, Goal goal) {
        if (goal instanceof EnemyGoal) {
            for (Text t : this.enemyText) {
                t.setStrikethrough(true);
                t.setFill(Color.BLACK);
            }
        } else if (goal instanceof TreasureGoal) {
            for (Text t : this.treasureText) {
                t.setStrikethrough(true);
                t.setFill(Color.BLACK);
            }
        } else if (goal instanceof SwitchGoal) {
            for (Text t : this.switchText) {
                if (bool == true) {
                    t.setStrikethrough(true);
                    t.setFill(Color.BLACK);
                } else {
                    t.setStrikethrough(false);
                    t.setFill(Color.DARKRED);
                }
            }
        }
    }

    public void processGoals(Dungeon dungeon) {
        System.out.println("entered processGoals");
        GoalTree tree = dungeon.getTree();
        constructGoalMessages(tree, 0);

    }

    private void constructGoalMessages(GoalTree tree, int padding) {
        Text goal = new Text();
        String toPrint = "";

        if (tree instanceof TreeLeaf) {
            TreeLeaf treeLeaf = (TreeLeaf) tree;
            toPrint = treeLeaf.identifyGoal();

            goal.setFill(Color.DARKRED);
            this.vbox.getChildren().add(goal);

            if (treeLeaf.getGoal() instanceof ExitGoal) {
                this.exitText.add(goal);
            } else if (treeLeaf.getGoal() instanceof EnemyGoal) {
                this.enemyText.add(goal);
            } else if (treeLeaf.getGoal() instanceof TreasureGoal) {
                this.treasureText.add(goal);
            } else if (treeLeaf.getGoal() instanceof SwitchGoal) {
                this.switchText.add(goal);
            }

        } else if (tree instanceof TreeAND) {
            TreeAND treeAnd = (TreeAND) tree;

            toPrint = "Complete ALL of the following:";
            this.vbox.getChildren().add(goal);

            for (GoalTree g : treeAnd.getList()) {
                constructGoalMessages(g, padding+30);
            }
            
        } else if (tree instanceof TreeOR) {
            TreeOR treeOr = (TreeOR) tree;

            toPrint = "Complete ONE of the following:";
            this.vbox.getChildren().add(goal);

            for (GoalTree g : treeOr.getList()) {
                constructGoalMessages(g, padding+30);
            }
        }
        goal.setText(toPrint);
        goal.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        Insets inset = new Insets(0, 0, 0, padding);
        VBox.setMargin(goal, inset);
    }
}