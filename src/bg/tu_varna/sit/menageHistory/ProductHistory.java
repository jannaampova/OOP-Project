package bg.tu_varna.sit.menageHistory;

import bg.tu_varna.sit.interfaces.ManageHistory;
import bg.tu_varna.sit.validateDate.ValidateDate;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * The ProductHistory class implements the ManageHistory interface.
 * It manages the history of added and removed products, allowing for the recording and retrieval of changes.
 */

public class ProductHistory implements ManageHistory {
    private Map<String, Map<String, Double>> removedProducts;//DATE-NAME-QUANTITY
    private Map<String, Map<String, Double>> addedProducts;
    private ValidateDate dateValidation;
    private WriteIntoFIle fileWriter;

    public ProductHistory() {
        this.removedProducts = new HashMap<>();
        this.addedProducts = new HashMap<>();
        this.dateValidation = new ValidateDate();
        this.fileWriter=new WriteIntoFIle();
    }


    /**
     * Adds a new record to the removed or added products map. <br>
     * If changes were made on the specified date and for the particular product,<br>
     * the quantity is updated accordingly by adding the new quantity to the existing quantity.<br>
     * If no changes were made for the particular product on the specified date,<br>
     * a new entry is created for the product with the specified quantity.<br>
     * If no changes were made on the specified date, a new entry is created for the product.<br>

     *
     * @param date      The date the change occurred in "DD/MM/YYYY" format.
     * @param name      The name of the product.
     * @param quantity  The quantity of the product.
     * @param isRemoval To decide which map should be used.
     * @throws IOException If an I/O error occurs.
     */
@Override
    public void addNewChange(String date, String name, double quantity, boolean isRemoval) throws IOException {
        Map<String, Map<String, Double>> targetMap = isRemoval ? removedProducts : addedProducts;

        if (targetMap.containsKey(date)) {
            if (targetMap.get(date).containsKey(name)) {
                Map<String, Double> productMap = targetMap.get(date);
                productMap.put(name, productMap.get(name) + quantity);
                targetMap.put(date, productMap);
            } else {
                targetMap.putIfAbsent(date, new HashMap<String, Double>());
                targetMap.get(date).put(name, quantity);
            }
        } else {
            Map<String, Double> productMap = new HashMap<>();
            targetMap.putIfAbsent(date, new HashMap<String, Double>());
            productMap.put(name, quantity);
            targetMap.put(date, productMap);
        }

        String fileName = isRemoval ? "menageHistory" : "menageHistoryAdded";
        fileWriter.writeIntoFile(fileName, targetMap);
    }

    /**
     * Retrieves information about removed and added products within a given period of time.
     */
    @Override
    public void getHistoryInfo(String fromDate, String toDate) throws IOException {
        LocalDate fromDate1 = dateValidation.parseDate(fromDate);
        LocalDate toDate1 = dateValidation.parseDate(toDate);

        System.out.println("History of Added Products:");
        printHistoryInfo(fromDate1, toDate1, "menageHistoryAdded");

        System.out.println("\nHistory of Removed Products:");
        printHistoryInfo(fromDate1, toDate1, "menageHistory");
    }
    /**
     * Prints the history information for the specified date range and file name.<br>
     *
     * @param fromDate The start date of the period.
     * @param toDate   The end date of the period.
     * @param fileName The name of the file to read the history from.
     */
    private void printHistoryInfo(LocalDate fromDate, LocalDate toDate, String fileName) {
        boolean flag = false;
        try (FileReader reader = new FileReader(fileName)) {
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] strings = line.split(",");
                String date = strings[2];
                LocalDate date1 = dateValidation.parseDate(date);
                if ((date1.isEqual(fromDate) || date1.isAfter(fromDate)) && (date1.isEqual(toDate) || date1.isBefore(toDate))) {
                    flag = true;
                    System.out.println("Product: " + strings[0] + " Quantity: " + strings[1] + " Date: " + strings[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read from file: " + fileName);
        }
        if (!flag) {
            System.out.println("No products in that specific date range!");
        }
    }
}
