package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main class that runs the application
 * @author Josh Gates
 * FUTURE ENHANCEMENT: add ability to sync to a database
 */
public class Main extends Application {

    /**
     * Sets main GUI screen/background and stage
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * this is the starting point for the program where the part and product objects are instantiated
     * @param args
     */
    public static void main(String[] args) {
        //Part tableview
        inHouse OverSizedTires = new inHouse(Inventory.getAutomatedPartID(), "OverSized Tires", 100.00, 1_000,1, 6, 101);
        Inventory.addPart(OverSizedTires);
        OutSourced MudTires = new OutSourced(Inventory.getAutomatedPartID(), "Mud Tires", 120.00, 1_000,1, 6, "GoodYear");
        Inventory.addPart(MudTires);
        inHouse GoldRims = new inHouse(Inventory.getAutomatedPartID(),"Gold Rims",220.00,100,1,5,102);
        Inventory.addPart(GoldRims);
        inHouse SilverRims = new inHouse(Inventory.getAutomatedPartID(),"Silver Rims",150.00,100,1,6,103);
        Inventory.addPart(SilverRims);
        OutSourced superCharger = new OutSourced(Inventory.getAutomatedPartID(),"Super Charger",550.00,10,1,2,"K&N");
        Inventory.addPart(superCharger);
        OutSourced twinTurbo = new OutSourced(Inventory.getAutomatedPartID(),"Twin Turbo",750.00,10,1,2,"MotorCraft");
        Inventory.addPart(twinTurbo);

        //Product TableView
        Product fordF150 = new Product(Inventory.getAutomatedProductID(),"F150",20000,200,1,20);
        Inventory.addProducts(fordF150);

        Product dodgeRam = new Product(Inventory.getAutomatedProductID(),"Ram 1500", 22000,100,1,10);
        Inventory.addProducts(dodgeRam);

        Product chevy = new Product(Inventory.getAutomatedProductID(),"Silverado", 21500,100,1,5);
        Inventory.addProducts(chevy);

        //Associated Parts
        dodgeRam.addAssociatedPart(MudTires);
        dodgeRam.addAssociatedPart(twinTurbo);
        fordF150.addAssociatedPart(OverSizedTires);
        fordF150.addAssociatedPart(GoldRims);
        chevy.addAssociatedPart(superCharger);
        chevy.addAssociatedPart(SilverRims);

        launch(args);

    }
}
