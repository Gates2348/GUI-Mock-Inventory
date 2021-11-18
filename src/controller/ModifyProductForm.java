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
 * This screen allows the user to modify a previously selected Product
 */
public class ModifyProductForm implements Initializable{

        private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        Product productToModify;

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
        private TableView<Part> partListTableView;

        @FXML
        private TableColumn<Part, Integer> partIDCol;

        @FXML
        private TableColumn<Part, String> partNameCol;

        @FXML
        private TableColumn<Part, Integer> partStockCol;

        @FXML
        private TableColumn<Part, Double> partPriceCol;

        @FXML
        private TableView<Part> associatedPartsTableView;

        @FXML
        private TableColumn<Part, Integer> associatePartIDCol;

        @FXML
        private TableColumn<Part, String> associatedNameCol;

        @FXML
        private TableColumn<Part, Integer> associatedStockCol;

        @FXML
        private TableColumn<Part, Double> associatedPriceCol;

        @FXML
        private Button addToAssociatedProduct;

        @FXML
        private TextField searchField;

        /**
        * Filters parts based on search field input
        */
        @FXML
        void filterParts(ActionEvent event) {
            if(!searchField.getText().trim().isEmpty()) {
                try {
                    int h = Integer.parseInt(searchField.getText());
                    for (Part parts : Inventory.getAllParts()) {
                        if (parts.getId() == h) {
                            partListTableView.getSelectionModel().select(parts);
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
                    partListTableView.setItems(filteredParts);
                    System.out.println(e);
                }
            }else{
                filteredParts.clear();
                partListTableView.setItems(Inventory.getAllParts());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("No Results Found");
                alert.setHeaderText("Try your search again");
                Optional<ButtonType> button = alert.showAndWait();
            }
        }

        /**
        * Adds part to a products associated parts array
        */
        @FXML
        void addToAssociatedProduct(ActionEvent event) {
            Part part = partListTableView.getSelectionModel().getSelectedItem();
            if(part == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Please select a part");
                alert.show();
                return;
            }

            Inventory.getAllassociatedParts().add(part);
        }

        /**
         * Receives information for Product selected at main menu
         */
        @FXML
        void receiveProducts(Product product){
            productID.setText(String.valueOf(product.getId()));
            productName.setText(product.getName());
            productPrice.setText(String.valueOf(product.getPrice()));
            productStock.setText(String.valueOf(product.getStock()));
            productMin.setText(String.valueOf(product.getMin()));
            productMax.setText(String.valueOf(product.getMax()));

            productToModify = product;
            associatedPartsTableView.setItems(product.getAssociatedParts());
        }

        /**
        * Saves updated changes for selected product in the allProducts array
        */
        @FXML
        public void saveModifiedProduct(ActionEvent actionEvent) throws IOException {
            try {
                int index = Inventory.getAllProducts().indexOf(productToModify);
                int id = Inventory.verifyID(productID.getText());
                String name = Inventory.verifyName(productName.getText());
                Double price = Inventory.verifyPrice(productPrice.getText());
                int stock = Inventory.verifyStock(productStock.getText());
                int min = Inventory.verifyMin(productMin.getText());
                int max = Inventory.verifyMax(productMax.getText());


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

                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Warning");
                    alert2.setContentText("Are you sure you want to save these changes?");
                    Optional<ButtonType> button2 = alert2.showAndWait();
                    if (button2.get() == ButtonType.OK) {
                        Product newProduct = new Product(id, name, price, stock, min, max);
                        Inventory.updateProduct(index, newProduct);
                        toMain(actionEvent);
                    } else {

                    }

                }

                }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("An incorrect value was entered, please try again");
                alert.showAndWait();
                System.out.println(e);

            }
        }

        /**
        *  Deletes selected product row
        */
        @FXML
        void deleteRow(ActionEvent event)throws IOException {
            if (Inventory.deletePart(associatedPartsTableView.getSelectionModel().getSelectedItem())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning!");
                alert.setHeaderText("Are you sure you want to delete this associated part?");
                Optional<ButtonType> button = alert.showAndWait();
                    if (button.get() == ButtonType.OK) {
                    associatedPartsTableView.getItems().removeAll(associatedPartsTableView.getSelectionModel().getSelectedItem());
                    } else {

                }
            }
        }

        /**
        *   Goes back to the main menu
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
        *   Initializes resources for JavaFX
         *   RUNTIME ERROR: Fixed runtime error for nullpointerexception trying to populate the associatedPartsTableView when the
        */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            partListTableView.setItems(Inventory.getAllParts());
            //Part TableView Cells
            partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            //Associated parts Cells
            associatePartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            associatedNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            associatedStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        }

}
