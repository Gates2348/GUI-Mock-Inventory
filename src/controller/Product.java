package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * This class holds the constructor, variables, and methods for products
 */
public class Product{
        public int id;
        public String name;
        public double price;
        public int stock;
        public int min;
        public int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /**
     * this is the constructor for all products
     * @param id defines an id for a product
     * @param name defines a name for a product
     * @param price defines a price for a product
     * @param stock defines an inventory level for a product
     * @param min defines a minimum amount for a product
     * @param max defines a maximum amount for a product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.min = min;
            this.max = max;
        }

    /**
     * adds a part to the associasted parts array
     * @param part is a part selected by the user to be added to the associated parts array
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * getter for associated parts array
     * @return returns an arraylist of a products associated parts
     */
    public ObservableList<Part> getAssociatedParts(){
        return associatedParts;
    }

    /**
     * getter for id variable
     * @return returns id
     */
    public int getId() {
        return id;
    }

    /**
     * setter for id variable
     * @param id is a variable that holds an integer that uniquely identifies a part
     */
    public void setProductID(int id) {
        this.id = id;
    }

    /**
     * getter for name variable
     * @return returns name variable which is a string value
     */
    public String getName() {
        return name;
    }

    /**
     * setter for name variable
     * @param name variable containg a string value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for price variable
     * @return returns price variable
     */
    public double getPrice() {
        return price;
    }

    /**
     * setter for price variable
     * @param price variable that holds an integer
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * getter for stock variable
     * @return stock which is a variable containing an integer
     */
    public int getStock() {
        return stock;
    }

    /**
     * setter for the stock variable
     * @param stock variable that contains an integer for inventory level
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * getter for minimum value
     * @return returns variable min which contains minimum value
     */
    public int getMin() {
        return min;
    }

    /**
     * setter for minimum value
     * @param min minimum value as variable min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * getter for maximum value
     * @return returns maximum value in variable max
     */
    public int getMax() {
        return max;
    }

    /**
     * setter for maximum value
     * @param max maximum value variable
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * setter for associated parts array
     * @param associatedPart is an array of a product's associated parts
     */
    public void setAssociatedParts(ObservableList<Part> associatedPart){
        this.associatedParts.setAll(associatedPart);
    }
}

