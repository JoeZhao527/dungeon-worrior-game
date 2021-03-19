package unsw.dungeon;

import javafx.event.EventHandler;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HelpController {
    @FXML
    Pane pane;

    @FXML
    public void initialize() {
        BackgroundImage myBI= new BackgroundImage(new Image((new File("images/help.png")).toURI().toString()),
        null,null,BackgroundPosition.CENTER,new BackgroundSize(400, 430, false, false, false, false));
        pane.setBackground(new Background(myBI));

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
        back.setLayoutX(15);
        back.setLayoutY(30);
        back.setStyle("-fx-border-color: black;");
        this.pane.getChildren().add(back);
    }
}