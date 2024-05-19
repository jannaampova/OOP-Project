package bg.tu_varna.sit.menageHistory;

import bg.tu_varna.sit.interfaces.ManageHistory;
import bg.tu_varna.sit.validateDate.ValidateDate;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * The AddedProductHistory class implements the ManageHistory interface to track historical changes (additions) to the warehouse inventory.<br>
 * It maintains a map called addedProducts, where the key is the date of the change and the value is another map containing the product names and their quantities.<br>
 * This class also utilizes a ValidateDate instance for date validation.<br>
 * The addNewChange method adds a new record to the addedProducts map.<br>
 * The getHistoryInfo method retrieves and prints information about added products within a specified date range.<br>
 * This class provides tracking history  system only for added products;<br>
 */


public class AddedProductHistory implements ManageHistory {
    private Map<String, Map<String, Double>> addedProducts;//data ime na produkt i kolicestvo
    private ValidateDate dateValidation;

    public AddedProductHistory() {
        this.addedProducts = new HashMap<>();
        this.dateValidation = new ValidateDate();
    }


    /**
     * This method adds a new record to the addedProducts map(tracking System), which tracks historical changes (additions) to the warehouse inventory.<br>
     * It takes three parameters:<br>
     * date (String): The date the change occurred in the format "DD/MM/YYYY".<br>
     * name (String): The name of the product that was added.<br>
     * quantity (double): The quantity of the product that was added.<br>
     * Functionality:<br>
     * -Checks if changes were made on the specified date.<br>
     * - If yes, checks if changes were made for the particular product.<br>
     * - If yes, updates the quantity by adding the new quantity to the existing quantity.<br>
     * - If no, adds a new entry for the product with the specified quantity.<br>
     * - If no(the  given date doesn't appear as key until now in the tracking system), creates a new entry for the specified date and the product with the specified quantity.<br>
     */

    @Override
    public void addNewChange(String date, String name, double quantity) throws IOException {
        if (addedProducts.containsKey(date)) {
            if (addedProducts.get(date).containsKey(name)) {
                Map<String, Double> stringIntegerMap = addedProducts.get(date);
                stringIntegerMap.put(name, stringIntegerMap.get(name) + quantity);
                addedProducts.put(date, stringIntegerMap);
                writeIntoFile();
            } else {
                addedProducts.putIfAbsent(date, new HashMap<String, Double>());
                addedProducts.get(date).put(date, quantity);
                writeIntoFile();
            }

        } else {
            Map<String, Double> stringIntegerMap = new HashMap<>();
            addedProducts.putIfAbsent(date, new HashMap<String, Double>());
            stringIntegerMap.put(name, quantity);
            addedProducts.put(date, stringIntegerMap);
            writeIntoFile();

        }

    }


    /**
     * Retrieves and prints information about added products within a specified date range.<br>
     *
     * @param fromDate the starting date of the range in the format "DD/MM/YYYY".<br>
     * @param toDate   the ending date of the range in the format "DD/MM/YYYY".<br>
     *                 This method iterates over the entries in the addedProducts map(the map used in the addNewChange method) and filters the entries based on the specified date range.<br>
     *                 It then prints the name and quantity of each product added within the specified range along with the date of addition.<br>
     */
    @Override
    public void getHistoryInfo(String fromDate, String toDate) throws IOException {
        LocalDate fromDate1 = dateValidation.parseDate(fromDate);
        LocalDate toDate1 = dateValidation.parseDate(toDate);
        boolean flag=false;
        System.out.println("Added products in the warehouse from date: " + fromDate + " to date: " + toDate);
        for (Map.Entry<String, Map<String, Double>> stringMapEntry : addedProducts.entrySet()) {
            String date = stringMapEntry.getKey();
            LocalDate currDate = dateValidation.parseDate(date);
            if (currDate.isBefore(toDate1) && currDate.isAfter(fromDate1)) {
                flag=true;
                for (Map.Entry<String, Double> innerMap : stringMapEntry.getValue().entrySet()) {
                    System.out.println("Product: " + innerMap.getKey() + ", quantity: " + innerMap.getValue() + " DATE: " + date);
                }
            }
        }
        if(!flag){
            System.out.println("No added products in the specific date range");
        }
    }

    /**
     * Writes the history of added products to a file named "menageHistory" in a specific format.<br>
     * This method is called every time a product is added to the system<br>
     * It iterates the map created while the products were added and get the information from there<br>
     *
     * @throws IOException if an error occurs while writing to the file<br>
     */
    @Override
    public void writeIntoFile() throws IOException {
        try (FileWriter writer = new FileWriter("menageHistoryAdded")) {
            writer.write("Added Product,Quantity,Date \n");
            for (Map.Entry<String, Map<String, Double>> stringMapEntry : addedProducts.entrySet()) {
                for (Map.Entry<String, Double> innerMap : stringMapEntry.getValue().entrySet()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(innerMap.getKey()).append(",").append(innerMap.getValue()).append(",").append(stringMapEntry.getKey()).append(System.lineSeparator());
                    writer.write(stringBuilder.toString());

                }
            }
        } catch (IOException e) {
            System.out.println("Failed to save in file");
            throw new RuntimeException(e);
        }
    }
}


