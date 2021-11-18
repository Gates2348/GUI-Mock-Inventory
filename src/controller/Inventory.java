package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class controls the Inventory of parts & products and the methods & variables they utilize
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private static int automatedPartID = 1;
    private static int automatedProductID = 1;

    /**
     * Adds Parts to an Observable List
     * @param part is an object of type Part
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * Adds Products to an Observable List
     * @param product is an object of type Product
     */
    public static void addProducts(Product product) {
        allProducts.add(product);
    }

    /**
     * Returns a list with all Parts
     * @return returns a list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Returns list with associated Parts
     * @return returns an array of associated parts
     */
    public static ObservableList<Part> getAllassociatedParts() {
        return associatedParts;
    }

    /**
     * Return list with any new added Products
     * @return returns an array of allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Getter for autogenerated part ID
     * @return the automatically generated ID of a part
     */
    public static int getAutomatedPartID() {
        return automatedPartID++;
    }

    /**
     * getter for autogenerated product ID
     * @return the automatically generated ID for a product
     */
    public static int getAutomatedProductID() {
        return automatedProductID++;
    }

    /**
     * deletes a selected part row
     * @param selectedPart is the part the user selects for deletion
     * @return if the part exists return true otherwise return false
     */
    public static Boolean deletePart(Part selectedPart){
        if(Inventory.getAllParts().contains(selectedPart)){
            return true;
        }
        return false;
    }

    /**
     * deletes a selected product row
     * @param selectedProduct is the product the user selects for deletion
     * @return if the product exists return true otherwise return false
     */
    public static Boolean deleteProduct(Product selectedProduct){
        if(Inventory.getAllProducts().contains(selectedProduct) && selectedProduct.getAssociatedParts().isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * updates the selected part after modifications
     * @param index the index of where that part is located within the allParts array
     * @param newPart the part the user made changes to
     */
    public static void updatePart(int index, Part newPart) {
        allParts.set(index, newPart);
    }

    /**
     * updates the selected product after modification
     * @param index the index of where that product is located within the allProducts array
     * @param newProduct the product the user made changes to
     */
    public static void updateProduct(int index, Product newProduct) {
            allProducts.set(index, newProduct);
    }

    /**
     * This method verifies a number is entered by the user
     * @param value is a string from user input
     * @return returns a string or an error
     * @throws NumberFormatException if a non-numerical is entered this is returned
     */
    public static double verifyPrice(String value) throws NumberFormatException{
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Incorrect price, please enter a decimal number");
        }
    }

    /**
     * This method verifies a string is entered by the user
     * @param value is a string from user input
     * @return returns a string or an error
     * @throws Exception if a non-numerical is entered this is returned
     */
    public static String verifyName(String value) throws Exception{
        try{
            if(value.isEmpty()){
                throw new Exception();
            }else{
                return value;
            }
        }catch (Exception e){
            throw new Exception("Name is required");
        }
    }

    /**
     * verifies a string is entered for companyName
     * @param value a string from user input
     * @return returns the companuName or an error
     * @throws Exception throws an exception if the field is empty
     */
    public static String verifyCompanyName(String value) throws Exception{
        try{
            if(value.isEmpty()){
                throw new Exception();
            }else{
                return value;
            }
        }catch (Exception e){
            throw new Exception("Company name is required");
        }
    }

    /**
     * verifies an integer is entered for stock
     * @param value an int from user input
     * @return an int or an error
     * @throws NumberFormatException if the user enters nothing or something non-numerical an error is thrown
     */
    public static int verifyStock(String value) throws NumberFormatException{
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Incorrect stock, please enter a number");
        }
    }

    /**
     * verifies an integer is entered for minimum amount
     * @param value an int from user input
     * @return an int or an error
     * @throws NumberFormatException if the user enters nothing or something non-numerical an error is thrown
     */
    public static int verifyMin(String value) throws NumberFormatException{
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Incorrect min, please enter a number");
        }
    }

    /**
     * verifies an integer is entered for maximum amount
     * @param value an int from user input
     * @return an int or an error
     * @throws NumberFormatException if the user enters nothing or something non-numerical an error is thrown
     */
    public static int verifyMax(String value) throws NumberFormatException{
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Incorrect max, please enter a number");
        }
    }

    /**
     * verifies an int is entered for MachineID field
     * @param value an int from user input
     * @return an integer or an error
     * @throws NumberFormatException if the user enters nothing or something non-numerical an error is thrown
     */
    public static int verifyMachineID(String value) throws NumberFormatException{
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Incorrect machineID, please enter a number");
        }
    }

    /**
     * verifies an int is entered for ID field
     * @param value an int from user input
     * @return an integer or an error
     * @throws NumberFormatException if the user enters nothing or something non-numerical an error is thrown
     */
    public static int verifyID(String value) throws NumberFormatException{
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Incorrect ID, please enter a number");
        }
    }

}