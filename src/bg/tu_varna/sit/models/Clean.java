package bg.tu_varna.sit.models;

import bg.tu_varna.sit.warehouse.Warehouse;
import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.validateDate.ValidateDate;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The {@code Clean} class implements the {@code Command} interface and is responsible for managing the cleaning
 * process of products in a warehouse. It checks for expired products or products that are near their expiry date
 * and calculates the potential loss for a given period.
 */
public class Clean implements Command {
    private ValidateDate dateValidation;
    private Map<String, Map<String, Double>> menageLossOfProducts;
    private Warehouse warehouse;
    private Scanner scanner;

    /**
     * Constructs a new  instance with the specified warehouse.
     * menageLossOfProducts is a nested Map that tracks the quantity of products that are either expired or near their expiry date.
     * The key is the expiry date of the products in String format.
     *
     * @param warehouse the warehouse instance to be used by this class
     */
    public Clean(Warehouse warehouse) {
        this.warehouse = warehouse;
        this.dateValidation = new ValidateDate();
        this.menageLossOfProducts = new HashMap<>();
        this.scanner = new Scanner(System.in);

    }

    /**
     * Cleans the products in the warehouse by checking their expiry dates. Products that are expired or near
     * expiry (within 3 days) are identified and managed accordingly.
     * First it extracts the reference date.
     * For each product in the warehouse, this method:
     * 1. Extracts the expiry date of the  current product.
     * 2. Compares the expiry date with the given reference date.
     * 3. If the product is expired (expiry date is before the reference date), it:
     * - Adds the product to the `menageLossOfProducts` map with its expiry date and quantity.
     * - Removes the product from the warehouse.
     * - Prints details of the expired product.
     * 4. If the product is near expiry (within 3 days of the reference date), it:
     * - Adds the product to the `menageLossOfProducts` map with its expiry date and quantity.
     * - Removes the product from the warehouse.
     * - Prints details of the near-expiry product.
     *
     * @param products the map of product lists categorized by their type
     * @param date     the reference date used to check for expired or near-expiry products
     */
    public void cleanProducts(Map<String, List<Product>> products, String date) {
        LocalDate givenDate = dateValidation.parseDate(date);
        System.out.println("Products with expired date or near expiry date");
        for (Map.Entry<String, List<Product>> product : products.entrySet()) {
            for (int i = 0; i < product.getValue().size(); i++) {

                String expiryDate = product.getValue().get(i).getExpiryDate();
                LocalDate curDate = dateValidation.parseDate(expiryDate);
                Period period = Period.between(givenDate, curDate);

                String keyOfTheInnerMap = product.getValue().get(i).getName();
                if (curDate.isBefore(givenDate)) {
                    menageLossOfProducts.putIfAbsent(expiryDate, new HashMap<>());
                    menageLossOfProducts.get(expiryDate).putIfAbsent(keyOfTheInnerMap, 0.0);
                    menageLossOfProducts.get(expiryDate).put(keyOfTheInnerMap, product.getValue().get(i).getQuantity() + menageLossOfProducts.get(expiryDate).get(keyOfTheInnerMap));
                    System.out.printf("Name: %s, Expiry date: %s, Quantity: %.2f EXPIRED %n", keyOfTheInnerMap, expiryDate, product.getValue().get(i).getQuantity());
                    product.getValue().remove(product.getValue().get(i));
                    i--;
                } else if (period.getYears() == 0) {
                    if (period.getMonths() == 0) {
                        if (period.getDays() <= 3 && period.getDays() >= 0) {
                            menageLossOfProducts.putIfAbsent(expiryDate, new HashMap<>());
                            menageLossOfProducts.get(expiryDate).putIfAbsent(keyOfTheInnerMap, 0.0);
                            menageLossOfProducts.get(expiryDate).put(keyOfTheInnerMap, product.getValue().get(i).getQuantity() + menageLossOfProducts.get(expiryDate).get(keyOfTheInnerMap));
                            System.out.printf("Name: %s, Expiry date: %s, Quantity: %.2f DAYS TO EXPIRY %d %n", keyOfTheInnerMap, expiryDate, product.getValue().get(i).getQuantity(), period.getDays());
                            product.getValue().remove(product.getValue().get(i));
                            i--;
                        }
                    }
                }
            }
        }
    }

    /**
     * This method calculates the loss of particular given product(if the products actually exists) in particular range of time
     * By given price and unity of the product
     * The method iterates over a map (menageLossOfProducts) to find entries that fall within the specified date range.
     * For each entry, if the product is found in the inner map(the map of product name and its quantity ), it calculates the loss based on the provided price and unit.
     * Finally, it prints the total loss if any loss is calculated, otherwise, it informs that no product with the specified name was found.
     *
     * @param fromDate      the lower bound
     * @param toDate        the upper bound
     * @param nameOfProduct on which the calculation should be made
     * @param price         of the actual product
     * @param unity         of the actual product
     */

    public void calculateLoss(String fromDate, String toDate, String nameOfProduct, double price, String unity) {
        LocalDate fromDate1 = dateValidation.parseDate(fromDate);
        LocalDate toDate1 = dateValidation.parseDate(toDate);
        double result = 0.0;

        for (Map.Entry<String, Map<String, Double>> stringMapEntry : menageLossOfProducts.entrySet()) {
            String key = stringMapEntry.getKey();
            LocalDate curDate = dateValidation.parseDate(key);
            if (!curDate.isBefore(fromDate1) && !curDate.isAfter(toDate1)) {
                Map<String, Double> innerMap = stringMapEntry.getValue();
                if (innerMap.containsKey(nameOfProduct)) {
                    double quantityOfProduct = innerMap.get(nameOfProduct);//gets the value of the inner map which is the actual quantity
                    if (unity.equalsIgnoreCase("kg")) {
                        result = quantityOfProduct * price;
                    } else if (unity.equalsIgnoreCase("piece")) {
                        result = Math.floor(quantityOfProduct);
                        result *= price;
                    }
                }

            }
        }
        if (result != 0) {
            System.out.printf("The loss of the product %s in the period %s-%s is %.2f levs, as the price of the product is %f.2 for one %s %n", nameOfProduct, fromDate, toDate, result, price, unity);
        } else {
            System.out.println("No products with that name! Or the warehouse is up to date!");
        }
    }

    /**
     * It prompts the user to enter a date and then
     * checks for expired or soon-to-expire products in the warehouse up to the given date.
     *
     * @param data user input
     */
    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException {
        System.out.println("Enter the date to which you want to check expired or soon to expire products!\n");
        String date = scanner.nextLine();
        warehouse.clean(date);
    }


}


