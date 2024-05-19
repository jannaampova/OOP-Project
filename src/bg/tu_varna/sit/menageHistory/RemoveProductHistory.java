package bg.tu_varna.sit.menageHistory;

import bg.tu_varna.sit.interfaces.ManageHistory;
import bg.tu_varna.sit.validateDate.ValidateDate;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The RemoveProductHistory class tracks historical changes (removals) from the warehouse inventory.<br>
 * It maintains a map of removed products with dates as keys and values as new map representing the actual removed product and its quantity.<br>
 * This class utilizes date validation for managing dates effectively.
 */
public class RemoveProductHistory implements ManageHistory {
    private Map<String, Map<String, Double>> removedProducts;//DATE-NAME-QUANTITY
    private ValidateDate dateValidation;

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
     *                 Algorithm:
     *                 - Check if changes were made on the specified date.
     *                 - If yes, check if changes were made for the particular product.
     *                 - If yes, update the quantity by adding the new quantity to the existing quantity.
     *                 - If no, add a new entry for the product with the specified quantity on the same date.
     *                 - If no, create a new entry for the specified date and the product with the specified quantity.
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
                removedProducts.get(date).put(name, quantity);
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
     */
    @Override
    public void getHistoryInfo(String fromDate, String toDate) throws IOException {
        LocalDate fromDate1 = dateValidation.parseDate(fromDate);
        LocalDate toDate1 = dateValidation.parseDate(toDate);
        boolean flag=false;
        System.out.println("Removed products from the warehouse from date: " + fromDate + " to date: " + toDate);
        try (FileReader reader = new FileReader("menageHistory")) {
            Scanner scanner = new Scanner(reader);
            String line = scanner.nextLine();
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                String[] strings = line.split(",");
                String date = strings[2];
                LocalDate date1 = dateValidation.parseDate(date);
                if (date1.isBefore(toDate1) && date1.isAfter(fromDate1)) {
                    flag=true;
                    System.out.println("Product: " + strings[0] + " Quantity: " + strings[1] + " Date: " + strings[2]);
                }

            }
        } catch (IOException e) {
            System.out.println("Failed to save in file");
            throw (e);
        }
        if(!flag){
            System.out.println("No removed products in that specific date range!");
        }

    }

    /**
     * * Writes the history of removed products to a file named "menageHistory" in a specific format.<br>
     * This method is called every time a product is removed from the warehouse.<br>
     * I am  using file writer for that purpose and I am writing into a specific file<br>
     * The writing into file happens when I iterate trough each product from the map that holds all the removed products from the previous method remove().<br>
     * Also, I am using this map for later purpose such as reading from it and output the history of removing in given date range!
     *
     * @throws IOException if an error occurs while writing to the file
     * @throws IOException
     */
    @Override
    public void writeIntoFile() throws IOException {
        try (FileWriter writer = new FileWriter("menageHistory", true)) {
            for (Map.Entry<String, Map<String, Double>> stringMapEntry : removedProducts.entrySet()) {
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