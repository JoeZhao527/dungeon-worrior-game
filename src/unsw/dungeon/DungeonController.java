package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.event.EventHandler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;

import java.io.File;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private GridPane squares;

    private List<ImageView> initialEntities;
    private Player player;
    private Dungeon dungeon;
    private ArrayList<Goal> goalList;
    private InventorySubScene inventory;
    private GoalSubScene goalSubScene;
    private String dungeonName;
    
    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities,String dungeonName) {
        this.dungeon = dungeon;
        this.goalList = dungeon.getGoals();
        this.dungeonName = dungeonName;

        trackCompletion(dungeon);
        this.player = dungeon.getPlayer();
        trackPlayerAlive(player);
        this.initialEntities = new ArrayList<>(initialEntities);
        trackSword(player);
        trackPotion(player);
        trackKey(player);
        trackGoal(goalList);



        /*
        double centerX = squares.getWidth()/2 - 50;
        double centerY = squares.getHeight()/2 - 50;
        this.sub.setLayoutX(centerX);
        this.sub.setLayoutY(centerY);*/
    }

    @FXML
    public void initialize() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());
        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
        
        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);

        addButton("home", "MainMenu", null, 10, this.dungeon);
        addButton("back", "LevelSelection",null, 80,this.dungeon);
        addButton("retry", "DungeonView", this.dungeonName, 150,this.dungeon);
        BackgroundImage myBI = new BackgroundImage(new Image((new File("images/granite.jpg")).toURI().toString()),
                null, null, null, null);
        mainPane.setBackground(new Background(myBI));

        AnchorPane.setTopAnchor(squares, (double) 0);
        AnchorPane.setBottomAnchor(squares, (double) 0);
        AnchorPane.setLeftAnchor(squares, (double) 350);
        AnchorPane.setRightAnchor(squares, (double) 0);

        this.inventory = new InventorySubScene();
        mainPane.getChildren().add(inventory);
        AnchorPane.setTopAnchor(inventory, (double) 200);
        AnchorPane.setLeftAnchor(inventory, (double) 50);

        this.goalSubScene = new GoalSubScene();
        mainPane.getChildren().add(goalSubScene);
        AnchorPane.setLeftAnchor(goalSubScene, (double) 10);
        AnchorPane.setTopAnchor(goalSubScene, (double) 50);

        this.goalSubScene.processGoals(dungeon);

    }

    private void trackGoal(ArrayList<Goal> goalList) {
        for (Goal g : goalList) {
            g.monitorCompletion().addListener(new ChangeListener<Boolean>() {
                private Goal goal = g;
                @Override
                public void changed(ObservableValue<? extends Boolean> observable,
                        Boolean oldValue, Boolean newValue) {
                    if (newValue.booleanValue() == true) {
                        updateGoal(true, goal);
                    } else {
                        updateGoal(false, goal);
                    }
                }
            });
        }
    }

    private void updateGoal(boolean bool, Goal goal) {
        this.goalSubScene.updateGoal(bool, goal);
    }

    private void trackKey(Player player) {
        player.hasKey().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                if (newValue.booleanValue() == true) {
                    updateKey(true);
                } else {
                    updateKey(false);
                }
            }
        });
    }

    private void updateKey(boolean bool) {
        this.inventory.updateKey(bool);
    }

    private void trackPotion(Player player) {
        player.hasPotion().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                if (newValue.booleanValue() == true) {
                    updatePotion(true);
                } else {
                    updatePotion(false);
                }
            }
        });
    }

    private void updatePotion(boolean bool) {
        this.inventory.updatePotion(bool);
    }

    private void trackSword(Player player) {
        player.hasSword().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                if (newValue.booleanValue() == true) {
                    updateSword(true);
                } else {
                    updateSword(false);
                }
            }
        });
    }

    private void updateSword(boolean bool) {
        this.inventory.updateSword(bool);
    }

    private void trackPlayerAlive(Player player) {
        player.getAlive().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                try {
                    DungeonApplication d = new DungeonApplication();
                    d.switchScene("LevelFail.fxml", null);
                    Stage stage = (Stage) squares.getScene().getWindow();
                    stage.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void trackCompletion(Dungeon dungeon) {
        dungeon.getCompleteProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                try {
                    DungeonApplication d = new DungeonApplication();
                    d.switchScene("LevelComplete.fxml", null);
                    Stage stage = (Stage) squares.getScene().getWindow();
                    stage.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void addButton(String name,String sceneName, String dungeonName, double xAnchor, Dungeon dun) {
        Button btn = new Button(name);
        String scene = sceneName+".fxml";
        
        String dungeon = this.dungeonName;
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    DungeonApplication d = new DungeonApplication();
                    d.switchScene(scene, dungeon);
                    
                    Stage stage = (Stage) btn.getScene().getWindow();
                    stage.close();
                    for (Entity entity : dun.getAllEntities()) {
                        if (entity instanceof Enemy) {
                            ((Enemy)entity).stop();
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        btn.setMaxSize(Double.MAX_VALUE, Double.MIN_VALUE);
        mainPane.getChildren().add(1, btn);
        AnchorPane.setTopAnchor(btn, (double) 10);
        AnchorPane.setLeftAnchor(btn, (double) xAnchor);
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case W:
        case UP:
            player.moveUp();
            checkCollision(player);
            break;
        case S:
        case DOWN:
            player.moveDown();
            checkCollision(player);
            break;
        case A:
        case LEFT:
            player.moveLeft();
            checkCollision(player);
            break;
        case D:
        case RIGHT:
            player.moveRight();
            checkCollision(player);
            break;
        case SPACE:
            player.useSword();
            break;
        default:
            break;
        }
    }

    public void checkCollision(Entity eMoved) {
        if (eMoved instanceof Player) {
            for (Entity e : dungeon.getEntity(eMoved.getX(), eMoved.getY())) {
                player.handleCollision(e);
            }
        }
    }
}

