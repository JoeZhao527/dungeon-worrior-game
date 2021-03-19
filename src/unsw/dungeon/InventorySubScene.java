package unsw.dungeon;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class InventorySubScene extends SubScene {

    private ArrayList<ImageView> images;
    private ImageView keyImage;
    private ImageView potionImage;
    private ImageView swordImage;

    public InventorySubScene() {
        super(new VBox(), 50, 200);
        prefWidth(50);
        prefHeight(100);

        System.out.println("entered InventorySubScene constructor");

        this.images = new ArrayList<>();

        VBox root = (VBox) this.getRoot();

        Image key = new Image((new File("images/key.png")).toURI().toString());
        ImageView keyImage = new ImageView();
        keyImage.setImage(key);
        this.images.add(keyImage);
        this.keyImage = keyImage;

        Image potion = new Image((new File("images/brilliant_blue_new.png")).toURI().toString());
        ImageView potionImage = new ImageView();
        potionImage.setImage(potion);
        this.images.add(potionImage);
        this.potionImage = potionImage;

        Image sword = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        ImageView swordImage = new ImageView();
        swordImage.setImage(sword);
        this.images.add(swordImage);
        this.swordImage = swordImage;

        for (ImageView view : this.images) {
            view.setFitWidth(50);
            view.setPreserveRatio(true);
            view.setSmooth(true);
            view.setCache(true);
            view.setOpacity(0);
        }

        root.getChildren().add(keyImage);
        root.getChildren().add(potionImage);
        root.getChildren().add(swordImage);

        this.setFill(Color.TRANSPARENT);
        root.setBackground(Background.EMPTY);
    }

    public void updateSword(boolean bool) {
        System.out.println("entered inventory updateSword");
        if (bool == true) {
            this.swordImage.setOpacity(100);
        } else {
            this.swordImage.setOpacity(0);
        }
    }

    public void updatePotion(boolean bool) {
        System.out.println("entered inventory updatePotion");
        if (bool == true) {
            this.potionImage.setOpacity(100);
        } else {
            this.potionImage.setOpacity(0);
        }
    }

    public void updateKey(boolean bool) {
        System.out.println("entered inventory updateKey");
        if (bool == true) {
            this.keyImage.setOpacity(100);
        } else {
            this.keyImage.setOpacity(0);
        }
    }
    
}