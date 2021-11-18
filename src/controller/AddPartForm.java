package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class allows the user to add parts from the GUI
 */
public class AddPartForm implements Initializable{

        @FXML
        private TextField idText;

        @FXML
        private TextField nameText;

        @FXML
        private TextField stockText;

        @FXML
        private TextField priceText;

        @FXML
        private TextField maxText;

        @FXML
        private TextField minText;

        @FXML
        private Label MachineIDLabel;

        @FXML
        private TextField machineIDText;

        @FXML
        private RadioButton inHouse;

        @FXML
        private ToggleGroup addPartRB;

        @FXML
        private RadioButton outSourced;

        /**
        * Changes label options if OutSourced radio button is selected
        */
        @FXML
        void outSourcedSelected(ActionEvent event) {
            MachineIDLabel.setText("Company Name");
        }

        /**
        * Changes label options if inHouse radio button is selected
        */
        @FXML
        void inHouseSelected(ActionEvent event) {
            MachineIDLabel.setText("Machine ID");
        }

        /**
        * Goes back to main menu
        */
        @FXML
        void toMain(ActionEvent event) throws IOException {
            Parent parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

        /**
        * Saves an added part to the allParts array
        */
        @FXML
        void toSave(ActionEvent event) throws IOException {
            try {
                String name = Inventory.verifyName(nameText.getText());
                int stock = Inventory.verifyStock(stockText.getText());
                double price = Inventory.verifyPrice(priceText.getText());
                int min = Inventory.verifyMin(minText.getText());
                int max = Inventory.verifyMax(maxText.getText());


                if(min > max){
                    Alert alert1 = new Alert(Alert.AlertType.WARNING);
                    alert1.setTitle("Warning");
                    alert1.setHeaderText("Minimum must be less than Maximum");
                    alert1.showAndWait();
                }else if(stock < min){
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Warning");
                    alert2.setHeaderText("Inventory must be greater than minimum");
                    alert2.showAndWait();
                }else if(max < stock){
                    Alert alert3 = new Alert(Alert.AlertType.WARNING);
                    alert3.setTitle("Warning");
                    alert3.setHeaderText("Maximum must be greater than stock");
                    alert3.showAndWait();
                }else if(stock == 0){
                    Alert alert4 = new Alert(Alert.AlertType.WARNING);
                    alert4.setTitle("Warning");
                    alert4.setHeaderText("Stock must be greater than zero");
                    alert4.showAndWait();
                }else {


                    if (inHouse.isSelected()) {
                        int machineID = Inventory.verifyMachineID(machineIDText.getText());
                        inHouse newInHousePart = new inHouse(Inventory.getAutomatedPartID(), name, price, stock, min, max, machineID);
                        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                        alert2.setTitle("Warning");
                        alert2.setContentText("Are you sure you want to add this part?");
                        Optional<ButtonType> button2 = alert2.showAndWait();
                        if (button2.get() == ButtonType.OK) {
                            Inventory.addPart(newInHousePart);
                            toMain(event);
                        } else {

                        }
                    } else {
                        String companyName = Inventory.verifyCompanyName(machineIDText.getText());
                        OutSourced newOutSourcedPart = new OutSourced(Inventory.getAutomatedPartID(), name, price, stock, min, max, companyName);
                        Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
                        alert3.setTitle("Warning");
                        alert3.setContentText("Are you sure you want to add this part?");
                        Optional<ButtonType> button2 = alert3.showAndWait();
                        if (button2.get() == ButtonType.OK) {
                            Inventory.addPart(newOutSourcedPart);
                            toMain(event);
                        } else {

                        }
                    }
                }

                }catch (Exception e){
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Data entry error");
                alert3.setContentText(e.getMessage());
                alert3.show();
            }



        }

        /**
        * @param url
        * @param resourceBundle imports controls for JavaFX
        */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

         }
    }


