package bg.tu_varna.sit.menageHistory;

import bg.tu_varna.sit.interfaces.ManageHistory;
import bg.tu_varna.sit.validateDate.ValidateDate;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * The AddedProductHistory class implements the ManageHistory interface to track historical changes (additions) to the warehouse inventory.
 * It maintains a map called addedProducts, where the key is the date of the change and the value is another map containing the product names and their quantities.
 * This class also utilizes a ValidateDate instance for date validation.
 * The addNewChange method adds a new record to the addedProducts map.
 * The getHistoryInfo method retrieves and prints information about added products within a specified date range.
 * This class provides tracking history  system only for added products;
 */


public class AddedProductHistory implements ManageHistory {
    private Map<String, Map<String, Double>> addedProducts;//key as a date value as a map of name and quantity of product
    private ValidateDate dateValidation;

    public AddedProductHistory() {
        this.addedProducts = new HashMap<>();
        this.dateValidation = new ValidateDate();

    }

    public Map<String, Map<String, Double>> getAddedProducts() {
        return addedProducts;
    }

    public ValidateDate getDateValidation() {
        return dateValidation;
    }

    /**
     * This method adds a new record to the addedProducts map(tracking System), which tracks historical changes (additions) to the warehouse inventory.
     * It takes three parameters:
     * date (String): The date the change occurred in the format "DD/MM/YYYY".
     * name (String): The name of the product that was added.
     * quantity (double): The quantity of the product that was added.
     * Functionality:
     * -Checks if changes were made on the specified date.
     * - If yes, checks if changes were made for the particular product.
     * - If yes, updates the quantity by adding the new quantity to the existing quantity.
     * - If no, adds a new entry for the product with the specified quantity.
     * - If no(the  given date doesn't appear as key until now in the tracking system), creates a new entry for the specified date and the product with the specified quantity.
     */

    @Override
    public void addNewChange(String date, String name, double quantity) throws IOException {
        if (addedProducts.containsKey(date)) {
            if (addedProducts.get(date).containsKey(name)) {//if changes were made on this date and on the particular product
                Map<String, Double> innerMap = addedProducts.get(date);//get the inner map which is the actual product
                innerMap.put(name, innerMap.get(name) + quantity);//add the product to the map if it is already there it overrides the quantity with the new change
                addedProducts.put(date, innerMap);//puts the new added product to the current date in the main map of tracking changes
                writeIntoFile();
            } else {//if that date doesn't contain the particular product (the key of the inner map-name of product)
                addedProducts.putIfAbsent(date, new HashMap<String, Double>());//puts the changes in map on given date - product (name,quantity)
                addedProducts.get(date).put(date, quantity);//?
                writeIntoFile();
            }

        } else {//if that date doesn't exist in the history of changes
            Map<String, Double> innerMap = new HashMap<>();
            addedProducts.putIfAbsent(date, new HashMap<String, Double>());
            innerMap.put(name, quantity);
            addedProducts.put(date, innerMap);//?
            writeIntoFile();

        }

    }


    /**
     * Retrieves and prints information about added products within a specified date range.
     *
     * @param fromDate the starting date of the range in the format "DD/MM/YYYY".
     * @param toDate   the ending date of the range in the format "DD/MM/YYYY".
     *                 This method iterates over the entries in the addedProducts map(the map used in the addNewChange method) and filters the entries based on the specified date range.
     *                 It then prints the name and quantity of each product added within the specified range along with the date of addition.
     */
    @Override
    public void getHistoryInfo(String fromDate, String toDate) {
        LocalDate fromDay1 = dateValidation.parseDate(fromDate);
        LocalDate toDay1 = dateValidation.parseDate(fromDate);
        System.out.println("Added products in the warehouse from date " + fromDate + " to date" + toDate);
        for (Map.Entry<String, Map<String, Double>> stringMapEntry : addedProducts.entrySet()) {
            String date = stringMapEntry.getKey();

            LocalDate currDate = dateValidation.parseDate(date);
            if (currDate.isBefore(toDay1) && currDate.isAfter(fromDay1)) {
                for (Map.Entry<String, Double> innerMap : stringMapEntry.getValue().entrySet()) {
                    System.out.println("Product: " + innerMap.getKey() + ", quantity: " + innerMap.getValue() + " DATE: " + date);
                }
            }
        }
    }

    /**
     * Writes the history of added products to a file named "menageHistory" in a specific format.
     * This method is called every time a product is added to the system
     * It iterates the map created while the products were added and get the information from there
     * @throws IOException if an error occurs while writing to the file
     */
    @Override
    public void writeIntoFile() throws IOException {
        try (FileWriter writer = new FileWriter("menageHistory", true)) {
            writer.write("Added Product,Quantity,Date \n");
            for (Map.Entry<String, Map<String, Double>> stringMapEntry : addedProducts.entrySet()) {
                for (Map.Entry<String, Double> innerMap : stringMapEntry.getValue().entrySet()) {
                    writer.write(innerMap.getKey() + "," + innerMap.getValue() + "," + stringMapEntry.getKey());
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to save in file");
            throw new RuntimeException(e);
        }
    }
}


