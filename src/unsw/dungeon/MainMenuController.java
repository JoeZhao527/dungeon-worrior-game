package unsw.dungeon;

import java.io.File;

import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class MainMenuController {
    @FXML
    private Pane pane;

    public static MediaPlayer mp;
    @FXML
    public void initialize() {
        Text t1 = new Text(80, 170, "DUNGEON");
        Text t2 = new Text(80, 220, "WARRIOR");
        t1.setStyle("-fx-font: 50px Tahoma");
        t2.setStyle("-fx-font: 50px Tahoma");
        t2.setFill(Color.GOLDENROD);
        t1.setFill(Color.GOLD);
        pane.getChildren().addAll(t1, t2);

        BackgroundImage myBI = new BackgroundImage(new Image((new File("images/backGround1.png")).toURI().toString()),
                null, null, BackgroundPosition.CENTER, new BackgroundSize(400, 470, false, false, false, false));
        pane.setBackground(new Background(myBI));
        if (mp == null) {
            try{
                Media media = new Media(new File("audio/dungeonMusic.mp3").toURI().toString());
                mp = new MediaPlayer(media);
                mp.setVolume(0.5);
                mp.setOnEndOfMedia(new Runnable() {
                    public void run() {
                    mp.seek(Duration.ZERO);
                    }
                });
                mp.play();
            } catch (MediaException e) {}
        }
    }

    @FXML
    public void handleButtonPress(ActionEvent e) {
        Button start = (Button)e.getSource();
        switch(start.getId()) {
            case "Start":
                toPage(start, "LevelSelection.fxml");
                break;
            case "Mute":
                MainMenuController.mp.setMute(!mp.isMute());
                break;
            case "Help":
                toPage(start, "Help.fxml");
            default:
                break;
        }
    }
    private void toPage(Button start,String page) {
        try {
            DungeonApplication d = new DungeonApplication();
            d.switchScene(page, null);
            Stage stage = (Stage) start.getScene().getWindow();
            stage.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}