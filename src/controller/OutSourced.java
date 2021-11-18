package controller;

/**
 * This class all the addition of outSourced part objects with a unique companyName attribute
 */
public class OutSourced extends Part{

    private String companyName;

    /**
     * This is the constructor for an outSourced part
     * @param id defines an id for the outSourced part
     * @param name defines a name for the outSourced part
     * @param price defines a price for the outSourced part
     * @param stock defines an inventory level for the outSourced part
     * @param min defines a minimum for the outSourced part
     * @param max defines a maximum for the outSourced part
     * @param companyName defines a unique companyName for the outSourced part
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * returns the companyName so it is available to view in other classes
     * @return companyName a string value
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * sets the companyName so it is available to view in other classes
     * @param companyName sets companyName value
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
