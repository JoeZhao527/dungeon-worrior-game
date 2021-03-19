package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DungeonApplication extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Dungeon");

        MainMenuController controller = new MainMenuController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    public void switchScene(String fmxlFile, String dungeon) throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Dungeon");

        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fmxlFile));

        switch (fmxlFile) {
        case "DungeonView.fxml":
            DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(dungeon);
            DungeonController D_controller = dungeonLoader.loadController();
            loader.setController(D_controller);
            root = loader.load();
            Scene view = new Scene(root);
            Pane mainPane = (Pane) root;
            mainPane.requestFocus();
            stage.setScene(view);
            stage.show();
            return;

        case "MainMenu.fxml":
            MainMenuController M_controller = new MainMenuController();
            loader.setController(M_controller);
            root = loader.load();
            break;
        
        case "LevelSelection.fxml":
            LevelSelectionController L_controller = new LevelSelectionController();
            loader.setController(L_controller);
            root = loader.load();
            break;

        case "LevelComplete.fxml":
            LevelCompleteController C_controller = new LevelCompleteController();
            loader.setController(C_controller);
            root = loader.load();
            break;

        case "LevelFail.fxml":
            LevelFailController F_controller = new LevelFailController();
            loader.setController(F_controller);
            root = loader.load();
            break;
        case "Help.fxml":
            HelpController H_controller = new HelpController();
            loader.setController(H_controller);
            root = loader.load();
            break;
        default:
            break;
        }
        if (root != null) {
            Scene view = new Scene(root);
            root.requestFocus();
            stage.setScene(view);
            stage.show();
            
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
