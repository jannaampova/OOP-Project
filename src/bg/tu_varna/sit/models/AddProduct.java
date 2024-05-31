package bg.tu_varna.sit.models;


import bg.tu_varna.sit.interfaces.Add;
import bg.tu_varna.sit.interfaces.ManageHistory;
import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.validateDate.ValidateDate;
import bg.tu_varna.sit.warehouse.Warehouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AddProduct implements Add, Command {
    private  ManageHistory addProduct;
    private final Warehouse warehouse;
    private final Scanner scanner;

    public AddProduct(ManageHistory addProduct, Warehouse warehouse) {
        this.addProduct =addProduct;
        this.warehouse = warehouse;
        this.scanner = new Scanner(System.in);
    }


    /**
     * <p> Adds a new product to the warehouse inventory.
     * This method efficiently manages the addition of products while considering existing products with the same name and expiry dates.
     *<br>
     * @param listOfProducts The map containing the list of products in the warehouse, where the key is the product name and the value is a list of products with the same name.<br>
     * @throws LocationException if an error related to the product's location occurs.<br>
     * @throws NegativeNumberException if a negative number is encountered.<br>
          * ## Algorithm
     * 1. **Track Inventory Change:** Regardless of existing products, the method records the addition using `addProduct.addNewChange()`.<br>
     * 2. **Initialize Flag:** A flag is set to `true` to indicate potential placement with existing products.<br>
     * 3. **Iterate Through Existing Products:**<br>
     *    - Check if a product with the same name exists.<br>
     *      - If a product with the same name exists:<br>
     *        - Retrieve the expiry date of the new product.<br>
     *        - Iterate through the list of existing products with the same name.<br>
     *          - For each existing product, compare expiry dates using `checkEqualityDate()`.<br>
     *            - If expiry dates match:
     *              - Set the flag to `false`.
     *              - Attempt to add the new quantity to the existing product using `addWithSameExpiryDate()`. If space is insufficient, an informative message is printed.<br>
     *            - If expiry dates differ (flag remains `true`):<br>
     *              - Set the flag to `false` and call `addWithDifferentExpiryDate()` to handle products with different expiry dates.<br>
     *      - If no product with the same name has the same expiry date (and flag is still `true`):<br>
     *        - Set the flag to `false` and call `addWithDifferentExpiryDate()` to add the new product with a different expiry date.<br>
     * 4. **New Product Case (Flag Remains True):**<br>
     *    - If no existing product matches, create a new location for the product.<br>
     *    - Call `addNotExistingProduct()` to add the new product as a new entry in the warehouse inventory.<br>
     */


    @Override
    public void add(Map<String, List<Product>> listOfProducts, Product product) throws LocationException, NegativeNumberException, IOException {
        boolean flag = true;
        addProduct.addNewChange(product.getArrivalDate(), product.getName(), product.getQuantity(),false);
        for (Map.Entry<String, List<Product>> outerMapProductName : listOfProducts.entrySet())
        {
            if (outerMapProductName.getKey().equals(product.getName())) {
                String expiryDateNew = product.getExpiryDate();


                for (Product p : outerMapProductName.getValue()) {
                    String expiryDateCurr = p.getExpiryDate();

                    if (checkDateEquality(expiryDateNew, expiryDateCurr)) {

                        flag = false;
                        if (!addWithSameExpiryDate(product.getQuantity(), p)) {
                            double availableSpace = p.getLocation().getShelfCapacity() - p.getQuantity();
                            throw new LocationException("You can add only " + availableSpace + " " + product.getName() + " at location" + p.getLocation().toString());
                        }
                    }

                }
                if (flag) {
                    flag = false;
                    addWithDifferentExpiryDate(product, outerMapProductName.getValue());
                }

            }// ako ne systestvuva s tova ime

        }
        if (flag) {
            Location location = new Location(product.getName(), product.getQuantity());

            product.setLocation(location);
            addNotExistingProduct(product, listOfProducts);

        }
    }

    /**
     * Compares the expiration date on the product to be added and the existing product.<br>
     * @return true if the dates match,otherwise false
     */

    public boolean checkDateEquality(String expiryDateNew, String expiryDateCurr) {

        if (expiryDateNew.equals(expiryDateCurr)) {
            return true;
        }
        return false;
    }

    /**
     * This method makes a decision weather to update the quantity of the existing products or return to the main logic to give an explanation why it isn't updating the quantity.<br>
     * @param quantityToBeAdded the quantity expected to be added(using it for sum up the current quantity and to check if the update should be achieved or not)<br>
     * @param existingProduct instance of the current product and its details<br>
     * @return false if there isn't enough space on the particular location and true is everything is alright and the quantity is updated<br>
     */
    public boolean addWithSameExpiryDate(double quantityToBeAdded, Product existingProduct) throws LocationException, NegativeNumberException {
        double newQuantity = existingProduct.getQuantity() + quantityToBeAdded;
        if(newQuantity>existingProduct.getLocation().getShelfCapacity())
        {
            return false;
        }
        existingProduct.setQuantity(newQuantity);
        existingProduct.getLocation().setQuantity(newQuantity);
        return true;
    }


    /**
     * This method is for adding an existing product (by name) but different expiration date;<br>
     * -first I am creating a new location instance than, setting the new location as the location of the new product.<br>
     * and finally adding the product to the list of products<br>
     * @param product product to be added<br>
     * @param productList  the list indicating the values of the main map for the products in the warehouse<br>
     */
    public void addWithDifferentExpiryDate(Product product, List<Product> productList) throws LocationException {

        Location location = new Location(product.getName(), product.getQuantity());
        product.setLocation(location);
        productList.add(product);


    }
    /**
     * Adds a new product to a map of existing products, ensuring the product doesn't already exist.<br>
     * If the product doesn't exist yet, it creates a new empty list for it<br>
     * Add the product to the newly created list for its name<br>
     * @param product the product to be added to the map<br>
     * @param listOfProducts a map where the key is the product name and the value is a list of products with that name<br>
     *
     * @throws IllegalArgumentException if the product already exists in the map (by name)<br>
     */
    public void addNotExistingProduct(Product product, Map<String, List<Product>> listOfProducts) {
        listOfProducts.put(product.getName(), new ArrayList<>());
        listOfProducts.get(product.getName()).add(product);
    }



    /**
     * This method is implemented from the command interface(all possible commands for the user)<br>
     * It creates a new instance of the class product and allows
    */
    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException, IOException {
            Product product = new Product();
            product.setProductDetailsFromUserInput();
            warehouse.add(product);

    }

}