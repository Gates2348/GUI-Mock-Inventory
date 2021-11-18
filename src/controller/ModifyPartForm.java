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
 * this screen allows the user to modify a previously selected part
 */
public class ModifyPartForm implements Initializable{

        Part partToModify;

        @FXML
        private Label partIDLabel;

        @FXML
        private Label partNameLabel;

        @FXML
        private Label partInvLabel;

        @FXML
        private Label partCostLabel;

        @FXML
        private Label partMaxLabel;

        @FXML
        private Label partMinLabel;

        @FXML
        private Label partMachineIDLabel;

        @FXML
        private TextField id;

        @FXML
        private TextField name;

        @FXML
        private TextField stock;

        @FXML
        private TextField price;

        @FXML
        private TextField max;

        @FXML
        private TextField machineID;

        @FXML
        private TextField min;

        @FXML
        private RadioButton inHouse;

        @FXML
        private ToggleGroup modifyPartRB;

        @FXML
        private RadioButton outSourced;

         /**
        * changes label text if Inhouse radio button is selected
        */
        @FXML
        void inHouseSelected(ActionEvent event) {
            partMachineIDLabel.setText("Machine ID");
        }

        /**
        *  changes label text if Outsourced radio button is selected
        */
        @FXML
        void outSourceSelected(ActionEvent event) {
            partMachineIDLabel.setText("Company Name");
        }

        /**
        * goes to main menu
        */
        @FXML
        void toMain(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("MainScreen");
            stage.setScene(scene);
            stage.show();
        }

        /**
        * receives parts from selected part on main screen
        * @param part is an object of type part that was selected at the main screen by the user
        */
        public void receiveParts(Part part){
            partToModify = part;
            id.setText(String.valueOf(part.getId()));
            name.setText(part.getName());
            price.setText(String.valueOf(part.getPrice()));
            stock.setText(String.valueOf(part.getStock()));
            max.setText(String.valueOf(part.getMax()));
            min.setText(String.valueOf(part.getMin()));
            //if outsourced get ID if in house get part MachineID
            if (part instanceof inHouse){
                machineID.setText(String.valueOf(((inHouse) part).getMachineID()));
                inHouse.setSelected(true);
            }else if(part instanceof OutSourced){
                partMachineIDLabel.setText("Company Name");
                machineID.setText(String.valueOf(((OutSourced) part).getCompanyName()));
                outSourced.setSelected(true);
            }

        }

        /**
        * saves a part that has been modified
        * @param actionEvent this occurs when the user clicks on the save button in the GUI
        * @throws IOException throws an exception if improper data is entered
        */
        public void saveModifiedPart(ActionEvent actionEvent) throws IOException {
            try {
                int index = Inventory.getAllParts().indexOf(partToModify);
                int partID = Inventory.verifyID(id.getText());
                String partName = Inventory.verifyName(name.getText());
                double partPrice = Inventory.verifyPrice(price.getText());
                int partStock = Inventory.verifyStock(stock.getText());
                int partMin = Inventory.verifyMin(min.getText());
                int partMax = Inventory.verifyMax(max.getText());


                /**
                 * LOGICAL ERROR: I put the wrong symbol beside partMin causing a logical error
                 */
                if(partMin > partMax){
                    Alert alert1 = new Alert(Alert.AlertType.WARNING);
                    alert1.setTitle("Warning");
                    alert1.setHeaderText("Minimum must be less than Maximum");
                    alert1.showAndWait();
                }else if(partStock < partMin){
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Warning");
                    alert2.setHeaderText("Inventory must be greater than minimum");
                    alert2.showAndWait();
                }else if(partMax < partStock){
                    Alert alert3 = new Alert(Alert.AlertType.WARNING);
                    alert3.setTitle("Warning");
                    alert3.setHeaderText("Maximum must be greater than stock");
                    alert3.showAndWait();
                }else if(partStock == 0){
                    Alert alert4 = new Alert(Alert.AlertType.WARNING);
                    alert4.setTitle("Warning");
                    alert4.setHeaderText("Stock must be greater than zero");
                    alert4.showAndWait();
                }else{

                for (Part scanParts : Inventory.getAllParts()) {
                    if (partID == scanParts.getId() && inHouse.isSelected()) {
                        int machineid = Inventory.verifyMachineID(machineID.getText());
                        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                        alert2.setTitle("Warning");
                        alert2.setContentText("Are you sure you want to add this part?");
                        Optional<ButtonType> button2 = alert2.showAndWait();
                        if(button2.get() == ButtonType.OK){
                            inHouse newinHouse = new inHouse(partID, partName, partPrice, partStock, partMin, partMax, machineid);
                            Inventory.updatePart(index, newinHouse);
                            toMain(actionEvent);
                        }else{

                        }

                    }if (outSourced.isSelected()) {
                        String companyname = Inventory.verifyCompanyName(machineID.getText());
                        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                        alert2.setTitle("Warning");
                        alert2.setContentText("Are you sure you want to add this part?");
                        Optional<ButtonType> button2 = alert2.showAndWait();
                        if(button2.get() == ButtonType.OK){
                            OutSourced newoutSourcedPart = new OutSourced(partID, partName, partPrice, partStock, partMin, partMax, companyname);
                            Inventory.updatePart(index, newoutSourcedPart);
                            toMain(actionEvent);
                            }

                        }
                    }
                }

            }catch(Exception e){
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Data entry error");
                alert3.setContentText(e.getMessage());
                alert3.show();
            }
            }

        /**
        * Intializes resources for JavaFX
        */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
}

