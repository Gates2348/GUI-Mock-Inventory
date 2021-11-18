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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for main screen, allows the user to navigate the GUI and perform certain actions such as delete, and search
 */
public class mainScreenController implements Initializable {

    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIDColumn2;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productStockColumn;

    @FXML
    private TableColumn<Product, Double> productCostColumn;

    @FXML
    private TextField productFilter;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> idColumn;

    @FXML
    private TableColumn<Part, String> nameColumn;

    @FXML
    private TableColumn<Part, Integer> stockColumn;

    @FXML
    private TableColumn<Part, Double> priceColumn;

    @FXML
    private TextField filterField;

    @FXML
    private Button Exit;

    @FXML
    private Button deletePartButton;

    /**
     * Deletes a selected row for a part
     */
    @FXML
    void deleteRow(ActionEvent event) {
        if(partTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setHeaderText("Warning!");
            alert1.setHeaderText("A part has not been selected, please select a part and try again");
            Optional<ButtonType> button = alert1.showAndWait();

        }else if(partTableView.getSelectionModel().getSelectedItem() != null) {
            if (Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning!");
                alert.setHeaderText("Are you sure you want to delete this part?");
                Optional<ButtonType> button = alert.showAndWait();
                if (button.get() == ButtonType.OK) {
                    partTableView.getItems().removeAll(partTableView.getSelectionModel().getSelectedItem());
                } else {

                }
            }
        }
    }

    /**
     * Deletes a selected row for a Product
     */
    @FXML
    void deleteProduct(ActionEvent event) {
        if(productTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setHeaderText("Warning!");
            alert1.setHeaderText("A product has not been selected, please select a product and try again");
            Optional<ButtonType> button = alert1.showAndWait();

        }else if(productTableView.getSelectionModel().getSelectedItem() != null) {
            if (!Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning!");
                alert.setHeaderText("Are you sure you want to delete this product?");
                Optional<ButtonType> button = alert.showAndWait();

                if (button.get() == ButtonType.OK) {
                    productTableView.getItems().removeAll(productTableView.getSelectionModel().getSelectedItem());
                }

            } else {
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Warning");
                alert2.setHeaderText("This product has associated part(s), please delete all associated parts and try again.");
                Optional<ButtonType> button1 = alert2.showAndWait();
            }
        }
    }

    /**
     * goes to the Modify Part screen to allow for the selected part to be modified
     */
    @FXML
    void toModifyPartForm(ActionEvent event) throws IOException {
        if(partTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setHeaderText("Warning!");
            alert1.setHeaderText("A part has not been selected, please select a part and try again");
            Optional<ButtonType> button = alert1.showAndWait();

        }else if(partTableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyPartForm.fxml"));
            loader.load();

            ModifyPartForm modifyPartController = loader.getController();
            modifyPartController.receiveParts(partTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * goes to the Modify Product screen to allow for the selected product to be modified
     */
    @FXML
    void toModifyProductForm(ActionEvent actionEvent) throws IOException {
        if(productTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setHeaderText("Warning!");
            alert1.setHeaderText("A product has not been selected, please select a product and try again");
            Optional<ButtonType> button = alert1.showAndWait();

        }else if(productTableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyProductForm.fxml"));
            loader.load();

            ModifyProductForm modifyProductController = loader.getController();
            modifyProductController.receiveProducts(productTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Searches by part name or ID
     */
    @FXML
    void lookUpPartName(ActionEvent event){
        if(!filterField.getText().trim().isEmpty()) {
        try {
            int h = Integer.parseInt(filterField.getText());
            for (Part parts : Inventory.getAllParts()) {
                if (parts.getId() == h) {
                    partTableView.getSelectionModel().select(parts);
                }
            }
        }catch(NumberFormatException e){
            String h = filterField.getText();
            for (Part parts : Inventory.getAllParts()) {
                if (parts.getName().contains(h)) {
                    if(!filteredParts.contains(parts)){
                        filteredParts.add(parts);
                    }
                }
            }partTableView.setItems(filteredParts);
            System.out.println(e);
        }
        }else{
            filteredParts.clear();
            partTableView.setItems(Inventory.getAllParts());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("No Results Found");
            alert.setHeaderText("Try your search again");
            Optional<ButtonType> button = alert.showAndWait();
        }
    }

    /**
     * Searches by product name or ID
     */
    @FXML
    void filterProducts(ActionEvent event) {
        if(!productFilter.getText().trim().isEmpty()) {
            try {
                int h = Integer.parseInt(productFilter.getText());
                for (Product product : Inventory.getAllProducts()) {
                    if (product.getId() == h) {
                        productTableView.getSelectionModel().select(product);
                    }
                }
            }catch(NumberFormatException e){
                String h = productFilter.getText();
                for (Product products : Inventory.getAllProducts()) {
                    if (products.getName().contains(h)) {
                        if(!filteredProducts.contains(products)){
                            filteredProducts.add(products);
                        }
                    }
                }productTableView.setItems(filteredProducts);
                System.out.println(e);
            }
        }else{
            filteredProducts.clear();
            productTableView.setItems(Inventory.getAllProducts());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("No Results Found");
            alert.setHeaderText("Try your search again");
            Optional<ButtonType> button = alert.showAndWait();
        }
    }

    /**
     * FILL IN
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Part TableView
        partTableView.setItems(Inventory.getAllParts());
        //Part TableView Cells
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Product TableView
        productTableView.setItems(Inventory.getAllProducts());
        //Product TableView Cells
        productIDColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));



    }

    /**
     * Goes to add part screen
     */
    public void toAddPartForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddPartForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("SecondScreen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Goes to add product screen
     */
    public void toAddProductForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("AddProductForm");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * exits the program entirely
     */
    public void toExit(ActionEvent actionEvent) throws IOException{
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setHeaderText("Warning!");
        alert1.setHeaderText("Are you sure you want to exit the program?");
        Optional<ButtonType> button = alert1.showAndWait();
        if(button.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
