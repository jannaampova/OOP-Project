package bg.tu_varna.sit.menageHistory;

import bg.tu_varna.sit.interfaces.ManageHistory;
import bg.tu_varna.sit.validateDate.ValidateDate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * The RemoveProductHistory class tracks historical changes (removals) from the warehouse inventory.
 * It maintains a map of removed products with dates as keys and values as new map representing the actual removed product and its quantity.
 * This class utilizes date validation for managing dates effectively.
 */
public class RemoveProductHistory implements ManageHistory {
    private Map<String, Map<String, Double>> removedProducts;//data ime na produkt i kolicestvo
   private  ValidateDate dateValidation;

    public RemoveProductHistory() {
        this.removedProducts = new HashMap<>();
        this.dateValidation = new ValidateDate();
    }


    /**
     * Adds a new record to the removed products map.
     * If changes were made on the specified date and for the particular product,
     * the quantity is updated accordingly by adding the new quantity to the existing quantity.
     * If no changes were made on the specified date, a new entry is created for the product.
     * If no changes were made for the particular product on the specified date,
     * a new entry is created for the product with the specified quantity.
     *
     * @param date     The date the removal occurred in "DD/MM/YYYY" format.
     * @param name     The name of the removed product.
     * @param quantity The quantity of the product that was removed.
     *  Algorithm:
     *   - Check if changes were made on the specified date.
     *   - If yes, check if changes were made for the particular product.
     *   - If yes, update the quantity by adding the new quantity to the existing quantity.
     *   - If no, add a new entry for the product with the specified quantity on the same date.
     *   - If no, create a new entry for the specified date and the product with the specified quantity.
     */
    @Override
    public void addNewChange(String date, String name, double quantity) throws IOException {
        if (removedProducts.containsKey(date)) {
            if (removedProducts.get(date).containsKey(name)) {
                Map<String, Double> stringIntegerMap = removedProducts.get(date);
                stringIntegerMap.put(name, stringIntegerMap.get(name) + quantity);
                removedProducts.put(date, stringIntegerMap);
                writeIntoFile();
            } else {
                removedProducts.putIfAbsent(date, new HashMap<String, Double>());
                removedProducts.get(date).put(date, quantity);//?
                writeIntoFile();
            }

        } else {
            Map<String, Double> stringIntegerMap = new HashMap<>();
            removedProducts.putIfAbsent(date, new HashMap<String, Double>());
            stringIntegerMap.put(name, quantity);
            removedProducts.put(date, stringIntegerMap);
            writeIntoFile();
        }

    }

    /**
     * Retrieves information about removed products in given period of time
     *
     * @param fromDate
     * @param toDate
     * @throws IOException
     */
    @Override
    public void getHistoryInfo(String fromDate, String toDate) throws IOException {
        LocalDate fromDate1 = dateValidation.parseDate(fromDate);
        LocalDate toDate1 = dateValidation.parseDate(toDate);
        System.out.println("Removed products in the warehouse from date " + fromDate + " to date" + toDate);

        for (Map.Entry<String, Map<String, Double>> stringMapEntry : removedProducts.entrySet()) {
            String date = stringMapEntry.getKey();
            LocalDate localDate = dateValidation.parseDate(date);
            if (localDate.isBefore(toDate1) && localDate.isAfter(fromDate1)) {
                for (Map.Entry<String, Double> innerMap : stringMapEntry.getValue().entrySet()) {
                    System.out.println("Product: " + innerMap.getKey() + ", quantity: " + innerMap.getValue() + " DATE: " + date);
                }
            }
        }
    }

    /**
     * * Writes the history of removed products to a file named "menageHistory" in a specific format.
     *  This method is called every time a product is removed from the warehouse.
     *  I am  using file writer for that purpose and I am writing into a specific file
     *  The writing into file happens when I iterate trough each product from the map that holds all the removed products from the previous method remove().
     * @throws IOException if an error occurs while writing to the file
     * @throws IOException
     */
    @Override
    public void writeIntoFile() throws IOException {

        try (FileWriter writer = new FileWriter("menageHistory")) {
            writer.write("Removed Product,Quantity,Date \n");
            for (Map.Entry<String, Map<String, Double>> stringMapEntry : removedProducts.entrySet()) {
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

