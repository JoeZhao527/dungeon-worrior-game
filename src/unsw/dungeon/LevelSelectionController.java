package unsw.dungeon;

import javafx.event.EventHandler;
import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LevelSelectionController {
    @FXML
    private GridPane grid;
    
    @FXML
    private AnchorPane mainPane;

    private ArrayList<Button> levelButtons;

    @FXML
    public void initialize() {
        Text t1 = new Text(130, 170, "LEVEL");
        Text t2 = new Text(80, 220, "SELECTION");
        t1.setStyle("-fx-font: 50px Tahoma");
        t2.setStyle("-fx-font: 50px Tahoma");
        t2.setFill(Color.GOLDENROD);
        t1.setFill(Color.GOLD);
        mainPane.getChildren().addAll(t1,t2);

        BackgroundImage myBI= new BackgroundImage(new Image((new File("images/backGround1.png")).toURI().toString()),
        null,null,BackgroundPosition.CENTER,new BackgroundSize(400, 470, false, false, false, false));
        mainPane.setBackground(new Background(myBI));

        Button back = new Button("Home");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    DungeonApplication dungeonApplication = new DungeonApplication();
                    dungeonApplication.switchScene("MainMenu.fxml", null);
                    Stage stage = (Stage) back.getScene().getWindow();
                    stage.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        back.setLayoutX(45);
        back.setLayoutY(30);
        back.setStyle("-fx-border-color: black;");
        this.mainPane.getChildren().add(back);

        this.grid = new GridPane();
        grid.setMinSize(200, 140);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        this.mainPane.getChildren().add(grid);

        // AnchorPane.setTopAnchor(grid, (double) 200);
        AnchorPane.setBottomAnchor(grid, (double) 10);
        AnchorPane.setLeftAnchor(grid, (double) 10);
        AnchorPane.setRightAnchor(grid, (double) 10);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33);
        grid.getColumnConstraints().addAll(col1, col2, col3);

        this.levelButtons = new ArrayList<>();

        addLevel("maze", "Easy 1", 0, 0);
        addLevel("treasure", "Easy 2", 0, 1);
        addLevel("boulders", "Medium 1", 1, 0);
        addLevel("portal", "Medium 2", 1, 1);
        addLevel("advanced", "Hard 1", 2, 0);
        addLevel("test", "Hard 2", 2, 1);
        addLevel("biglevel", "Showcase", 2, 2);
        //if (MainMenuController.mp.isMute() == true) MainMenuController.mp.setMute(false);
    }

    private void addLevel(String level, String name, int column, int row) {
        Button b = new Button(name);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                toLevelSelection(b, level);
            }
        });
        this.grid.add(b, column, row);
        b.setStyle("-fx-border-color: black;");
        GridPane.setHalignment(b, HPos.CENTER);

        this.levelButtons.add(b);
    }

    private void toLevelSelection(Button start, String level) {
        try {
            DungeonApplication dungeonApplication = new DungeonApplication();
            String dungeon = level + ".json";
            dungeonApplication.switchScene("DungeonView.fxml", dungeon);
            Stage stage = (Stage) start.getScene().getWindow();
            stage.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}