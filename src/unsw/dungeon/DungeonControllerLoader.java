package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    //Images
    private Image playerImage;
    private Image wallImage;
    private Image portalImage;
    private Image boulderImage;
    private Image swordImage;
    private Image keyImage;
    private Image doorClosedImage;
    private Image doorOpenedImage;
    private Image potionImage;
    private Image exitImage;
    private Image treasureImage;
    private Image floorSwitchImage;
    private Image enemyImage;

    public static MediaPlayer keySound;
    public static MediaPlayer swordSound;
    public static MediaPlayer potionSound;
    public static MediaPlayer treasureSound;
    public static MediaPlayer doorSound;
    public static MediaPlayer enemySound;
    public static MediaPlayer boulderSound;
    private String filename;
    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        this.filename = filename;
        entities = new ArrayList<>();
        playerImage = new Image((new File("images/human_new.png")).toURI().toString());
        wallImage = new Image((new File("images/brick_brown_0.png")).toURI().toString());
        portalImage = new Image((new File("images/portal.png")).toURI().toString());
        boulderImage = new Image((new File("images/boulder.png")).toURI().toString());
        swordImage = new Image((new File("images/greatsword_1_new.png").toURI().toString()));
        keyImage = new Image((new File("images/key.png")).toURI().toString());
        doorOpenedImage = new Image((new File("images/open_door.png")).toURI().toString());
        doorClosedImage = new Image((new File("images/closed_door.png")).toURI().toString());
        potionImage = new Image((new File("images/brilliant_blue_new.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png")).toURI().toString());
        treasureImage = new Image((new File("images/gold_pile.png")).toURI().toString());
        floorSwitchImage = new Image((new File("images/pressure_plate.png")).toURI().toString());
        enemyImage = new Image((new File("images/deep_elf_master_archer.png")).toURI().toString());
        try {
            swordSound = new MediaPlayer(new Media(new File("audio/pick.mp3").toURI().toString()));
            potionSound = new MediaPlayer(new Media(new File("audio/pick.mp3").toURI().toString()));
            treasureSound = new MediaPlayer(new Media(new File("audio/pick.mp3").toURI().toString()));
            doorSound = new MediaPlayer(new Media(new File("audio/doorOpened.mp3").toURI().toString()));
            enemySound = new MediaPlayer(new Media(new File("audio/enemyDead.mp3").toURI().toString()));
            keySound = new MediaPlayer(new Media(new File("audio/pick.mp3").toURI().toString()));
            boulderSound = new MediaPlayer(new Media(new File("audio/boulderMove.mp3").toURI().toString()));
        } catch (MediaException e) {}
                
    }
    
    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view, null,null);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view, null,null);
    }
    
    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view, null,null);
    }
    
    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view, null, boulderSound);
    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword,view,null,swordSound);
    }

    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key,view,null,keySound);

    }

    @Override
    public void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImage);
        addEntity(potion,view,null,potionSound);

    }

    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorClosedImage);
        addEntity(door,view,doorOpenedImage,doorSound);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit,view,null,null);

    }

    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure,view,null,treasureSound);
    }

    @Override
    public void onLoad(FloorSwitch floorswitch) {
        ImageView view = new ImageView(floorSwitchImage);
        addEntity(floorswitch,view,null,null);

    }

    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy,view,null,enemySound);
    }
    
    private void addEntity(Entity entity, ImageView view, Image view2, MediaPlayer mp2) {
        trackPosition(entity, view,view2, mp2);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node, Image newImage, MediaPlayer mp2) {
        if (mp2 != null)mp2.setMute(MainMenuController.mp.isMute());
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
                if (entity instanceof Boulder) {
                    if (mp2 != null) {
                        mp2.seek(Duration.seconds(10));
                        mp2.play();
                    }
                }
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
                if (entity instanceof Boulder) {
                    if (mp2 != null) {
                        mp2.seek(Duration.seconds(10));
                        mp2.play();
                    }
                }
            }
        });

        entity.getExistence().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                    Boolean oldValue, Boolean newValue) {
                    if (newImage == null) {
                        node.setVisible(newValue);
                    }
                    else  {
                        ((ImageView)node).setImage(newImage);
                    }
                    if (mp2 != null) {
                        if (entity instanceof Enemy) mp2.seek(Duration.seconds(3));
                        else mp2.seek(Duration.seconds(0));
                        mp2.play();
                    }
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        return new DungeonController(load(), entities, filename);
    }
}
