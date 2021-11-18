package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class allows the user to add products from the GUI
 */
public class AddProductForm implements Initializable{

        private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        private ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        @FXML
        private TextField productID;

        @FXML
        private TextField productName;

        @FXML
        private TextField productStock;

        @FXML
        private TextField productPrice;

        @FXML
        private TextField productMax;

        @FXML
        private TextField productMin;

        @FXML
        private TableView<Part> partsListTableView;

        @FXML
        private TableColumn<Part, Integer> partsIDColumn;

        @FXML
        private TableColumn<Part, String> addPartNameColumn;

        @FXML
        private TableColumn<Part, Integer> addPartStockColumn;

        @FXML
        private TableColumn<Part, Double> addPartPriceColumn;

        @FXML
        private TableView<Part> associatedPartTableView;

        @FXML
        private TableColumn<Part, Integer> associatedPartsID;

        @FXML
        private TableColumn<Part, String> associatedNameColumn;

        @FXML
        private TableColumn<Part, Integer> associatedStockColumn;

        @FXML
        private TableColumn<Part, Double> associatedPriceColumn;

        @FXML
        private Button AddPartButton;

        @FXML
        private TextField searchField;

        /**
         * Filter Products based on search field
         */
        @FXML
        void filterParts(ActionEvent event){
                if(!searchField.getText().trim().isEmpty()) {
                        try {
                                int h = Integer.parseInt(searchField.getText());
                                for (Part parts : Inventory.getAllParts()) {
                                        if (parts.getId() == h) {
                                                partsListTableView.getSelectionModel().select(parts);
                                        }
                                }
                        }catch(NumberFormatException e){
                                String h = searchField.getText().toLowerCase(Locale.ROOT);
                                for (Part parts : Inventory.getAllParts()) {
                                        if (parts.getName().toLowerCase(Locale.ROOT).contains(h)) {
                                                if(!filteredParts.contains(parts)){
                                                        filteredParts.add(parts);
                                                }
                                        }
                                }
                                partsListTableView.setItems(filteredParts);
                                System.out.println(e);
                        }
                }else{
                        filteredParts.clear();
                        partsListTableView.setItems(Inventory.getAllParts());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("No Results Found");
                        alert.setHeaderText("Try your search again");
                        Optional<ButtonType> button = alert.showAndWait();
                }
        }

        /**
         * Adds part to the products associated parts
         */
        @FXML
        void addPartToAssociatedParts(ActionEvent event) {

                Part part = partsListTableView.getSelectionModel().getSelectedItem();
                if(part == null){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Please select a part");
                        alert.show();
                        return;
                }
                Inventory.getAllassociatedParts().add(part);
                associatedParts.add(part);

        }

        /**
         * Deletes a selected associated part
         */
        @FXML
        void deleteRow(ActionEvent event)throws IOException {
                        if (Inventory.deletePart(associatedPartTableView.getSelectionModel().getSelectedItem())) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Warning!");
                                alert.setHeaderText("Are you sure you want to delete this associated part?");
                                Optional<ButtonType> button = alert.showAndWait();
                                if (button.get() == ButtonType.OK) {
                                        associatedPartTableView.getItems().removeAll(associatedPartTableView.getSelectionModel().getSelectedItem());
                                } else {

                                }
                        }
        }

        /**
         * Goes back to the main menu
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
         * Saves an added product to the allProducts array
         */
        @FXML
        public void saveProduct(ActionEvent actionEvent) throws IOException {
                try {
                        int id = Inventory.getAutomatedProductID();
                        String name = Inventory.verifyName(productName.getText());
                        int stock = Inventory.verifyStock(productStock.getText());
                        double price = Inventory.verifyStock(productPrice.getText());
                        int min = Inventory.verifyMin(productMin.getText());
                        int max = Inventory.verifyMax(productMax.getText());

                        if (min > max || stock < min || stock > max || stock == 0) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setHeaderText("Minimum must be less than Maximum and stock must be greater than minimum");
                                alert.showAndWait();
                        } else {

                                Product newProduct = new Product(id, name, price, stock, min, max);
                                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                                alert2.setTitle("Warning");
                                alert2.setContentText("Are you sure you want to save?");
                                Optional<ButtonType> button2 = alert2.showAndWait();
                                if (button2.get() == ButtonType.OK) {
                                        Inventory.addProducts(newProduct);
                                        newProduct.setAssociatedParts(associatedPartTableView.getItems());
                                        toMain(actionEvent);
                                } else {

                                }
                        }
                        }catch(Exception e){
                        Alert alert3 = new Alert(Alert.AlertType.ERROR);
                        alert3.setTitle("Data entry error");
                        alert3.setContentText(e.getMessage());
                        alert3.show();
                        }
                }


        @Override
        /**
         * Initializes FXML field values
         */
        public void initialize(URL url, ResourceBundle resourceBundle) {
                partsListTableView.setItems(Inventory.getAllParts());
                //Part TableView Cells
                partsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                addPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                addPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


                associatedPartTableView.setItems(associatedParts);

                associatedPartsID.setCellValueFactory(new PropertyValueFactory<>("id"));
                associatedNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                associatedStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                associatedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        }

}

