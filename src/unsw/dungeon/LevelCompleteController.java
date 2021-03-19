package unsw.dungeon;

import javafx.fxml.FXML;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;

public class LevelCompleteController {
    @FXML
    private Pane pane;

    private MediaPlayer mpComplete;
    @FXML
    public void initialize() {
        Text t = new Text (50, 60, "LEVEL CLEAR");
        t.setStyle("-fx-font: 50px Tahoma");
        t.setFill(Color.WHITE);
        pane.getChildren().add(t);

        BackgroundImage myBI= new BackgroundImage(new Image((new File("images/backGround3.png")).toURI().toString()),
        null,null,BackgroundPosition.CENTER,new BackgroundSize(400, 400, false, false, false, false));
        pane.setBackground(new Background(myBI));

        //MainMenuController.mp.setMute(true);
        try {
            Media media = new Media(new File("audio/complete.mp3").toURI().toString());
            mpComplete = new MediaPlayer(media);
            mpComplete.setVolume(1);
            if (MainMenuController.mp != null) mpComplete.setMute(MainMenuController.mp.isMute());
            mpComplete.play();
        }catch(MediaException e){}
    }

    @FXML
    public void handleButtonPress(ActionEvent e) {
        Button choice = (Button)e.getSource();
        switch(choice.getId()) {
            case "Main":
                toPage(choice, "MainMenu.fxml");
                break;
            case "Level":
                toPage(choice, "LevelSelection.fxml");
                break;
            default:
                break;
        }
    }

    private void toPage(Button start, String path) {
        try {
            DungeonApplication dungeonApplication = new DungeonApplication();
            dungeonApplication.switchScene(path, null);
            Stage stage = (Stage) start.getScene().getWindow();
            stage.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}