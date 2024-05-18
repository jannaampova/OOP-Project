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
    private ManageHistory addProduct;
    private ValidateDate dateValidation;
    private Warehouse warehouse;
    private Scanner scanner;

    public AddProduct(ManageHistory addProduct, ValidateDate dateValidation, Warehouse warehouse) {
        this.addProduct = addProduct;
        this.dateValidation = dateValidation;
        this.warehouse = warehouse;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Adds a new product to the warehouse inventory.
     *
     * It should follow the conditions:
     *  - if a product with the same name exists and the expiration day of the new product is equivalent to the already existing one the products should be stored on the same location if there is space,
     *  -if the product has the same day but different expiry day the product is stored on different location(shelf) same section
     * If the product does not exist in the inventory, it creates a new entry for the product and puts it on another location(section).
     *
     * @param listOfProducts The map containing the list of products in the warehouse, where the key is the product name and the value is a list of products with the same name.
     * @param productToBeAdded The product to be added to the warehouse inventory.
     * @throws LocationException if an error related to the product's location occurs.
     * @throws NegativeNumberException if a negative number is encountered.
     *
     * Algorithm:
     * - First no matter the product it records the addition of the product to the inventory tracking system using addProduct.addNewChange().
     * - Initialize a flag to true.
     * - Iterate through the existing products in the warehouse using a for-each loop.
     *   - Check if a product with the same name as the new product already exists.
     *     - If a product with the same name exists:
     *       - Retrieve the expiry date of the new product.
     *       - Iterate through the list(the values of the main map-) of products with the same name.
     *         - For each product, retrieve its expiry date.
     *         - Check if the expiry date of the current product matches the expiry date of the new product with boolean method checkEqualityDate()
     *           - If the expiry dates match:
     *              -flag turns false
     *             - Attempt to add the new quantity to the existing product using boolean addWithSameExpiryDate(), EXPLAINED LATER.
     *             - If there is not enough space in the location, the method returns false and prints a message indicating the available space.
     *           - If the expiry dates do not match the flag is still is not set to false:
     *             - Set the flag to false and call addWithDifferentExpiryDate() Explained later.
     *     - If no product with the same name has the same expiry date and flag is still true:
     *       - Set the flag to false and call addWithDifferentExpiryDate() to add the new product with a different expiry date.
     * - If the flag remains true after iterating through all existing products it means that there is no product with that name:
     *   - Create a new location for the product.
     *   - Call addNotExistingProduct() to add the new product as a new entry in the warehouse inventory.
     */
    /** za word dok
     1. **Efficient Inventory Management**: By checking for existing products with the same name and expiry date, this approach minimizes redundant entries and ensures efficient inventory management. It updates the quantity of existing products rather than creating new entries, which helps in maintaining a cleaner and more organized inventory system.
       *
       * 2. **Handling Multiple Expiry Dates**: The approach effectively handles products with multiple expiry dates. If a product with the same name but a different expiry date is found, it correctly adds a new entry for the product with the new expiry date. This is crucial for perishable goods where tracking different expiry dates is important.

     * 3. **Space Management**: By checking the available space before adding the new quantity, the method ensures that the product's location can accommodate the new quantity. If not, it provides feedback on the available space, preventing overstocking and optimizing space utilization.
     * 4. **Flexibility**: The use of flags and condition checks provides flexibility in handling various scenarios, such as updating existing products, adding products with different expiry dates, and creating new entries for completely new products. This flexibility makes the method robust and adaptable to different inventory situations.

     */

    public void add(Map<String, List<Product>> listOfProducts, Product productToBeAdded) throws LocationException, NegativeNumberException, IOException {
        boolean flag = true;
        addProduct.addNewChange(productToBeAdded.getArrivalDate(), productToBeAdded.getName(), productToBeAdded.getQuantity());
        for (Map.Entry<String, List<Product>> iterateThroughExistingProducts : listOfProducts.entrySet()) {
            if (iterateThroughExistingProducts.getKey().equals(productToBeAdded.getName())) {
                String expiryDateOnNewProduct = productToBeAdded.getExpiryDate();
                for (Product productDetailsFromMap : iterateThroughExistingProducts.getValue()) {
                    String expiryDateOnCurrProduct = productDetailsFromMap.getExpiryDate();
                    if (checkDateEquality(expiryDateOnNewProduct, expiryDateOnCurrProduct)) {
                        flag = false;
                        if (!addWithSameExpiryDate(productToBeAdded.getQuantity(), productDetailsFromMap)) {//?
                            double availableSpace = productDetailsFromMap.getLocation().getShelfCapacity() - productDetailsFromMap.getQuantity();
                            System.out.println(("You can add only " + availableSpace + " " + productToBeAdded.getName() + " at location sector: " + productDetailsFromMap.getLocation().getSector()+" shelf: "+productDetailsFromMap.getLocation().getRealShelf()));

                        }
                    }
                }
                if (flag) {
                    flag = false;
                    addWithDifferentExpiryDate(productToBeAdded, iterateThroughExistingProducts.getValue());
                }

            }

        }
        if (flag) {
            Location location = new Location(productToBeAdded.getName(), productToBeAdded.getQuantity());
            productToBeAdded.setLocation(location);
            addNotExistingProduct(productToBeAdded, listOfProducts);
        }
    }

    /**
     * Compares the expiration date on the product to be added and the existing product
     * @param expiryDateOnNewProduct
     * @param expiryDateOnCurrent
     * @return true if the dates match,otherwise false
     */

    public boolean checkDateEquality(String expiryDateOnNewProduct, String expiryDateOnCurrent) {
        if (expiryDateOnNewProduct.equals(expiryDateOnCurrent)) {
            return true;
        }
        return false;
    }

    /**
     * This method makes a decision weather to update the quantity of the existing products or return to the main logic to give an explanation why it isn't updating the quantity
     * @param quantityToBeAdded the quantity expected to be added(using it for sum up the current quantity and to check if the update should be achieved or not)
     * @param existingProduct instance of the current product and its details
     * @return true if there isn't enough space on the particular location and false is everything is alright and the quantity is updated
     * @throws LocationException
     * @throws NegativeNumberException
     */
    public boolean addWithSameExpiryDate(double quantityToBeAdded, Product existingProduct) throws LocationException, NegativeNumberException {
        double newQuantity = existingProduct.getQuantity() + quantityToBeAdded;
        if(newQuantity>existingProduct.getLocation().getShelfCapacity())
        {
            return true;
        }
        existingProduct.setQuantity(newQuantity);
        existingProduct.getLocation().setQuantity(newQuantity);
        return false;
    }

    /**
     * This method is for adding an existing product (by name) but different expiration date;
     * -first I am creating a new location instance than, setting the new location as the location of the new product
     * and finally adding the product to the list of products
     * @param product product to be added
     * @param productList  the list indicating the values of the main map for the products in the warehouse
     * @throws LocationException
     */
    public void addWithDifferentExpiryDate(Product product, List<Product> productList) throws LocationException {
        Location location = new Location(product.getName(), product.getQuantity());
        product.setLocation(location);
        productList.add(product);//?
    }
    /**
     * Adds a new product to a map of existing products, ensuring the product doesn't already exist.
     * If the product doesn't exist yet, it creates a new empty list for it
     * Add the product to the newly created list for its name
     * @param productToBeAdded the product to be added to the map
     * @param mainMapForExistingProducts a map where the key is the product name and the value is a list of products with that name
     *
     * @throws IllegalArgumentException if the product already exists in the map (by name)
     */
    public void addNotExistingProduct(Product productToBeAdded, Map<String, List<Product>> mainMapForExistingProducts) {
        mainMapForExistingProducts.put(productToBeAdded.getName(), new ArrayList<>());
        mainMapForExistingProducts.get(productToBeAdded.getName()).add(productToBeAdded);
    }

    /**
     * This method is implemented from the command interface(all possible commands for the user)
     * It creates a new instance of the class product and allows
     * @param data represents the user input

     */
    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException, IOException {
            Product product = new Product();
            product.setProductDetailsFromUserInput();
            warehouse.add(product);

    }

}