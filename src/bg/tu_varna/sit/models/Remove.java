package bg.tu_varna.sit.models;

import bg.tu_varna.sit.interfaces.Delete;
import bg.tu_varna.sit.interfaces.ManageHistory;
import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.warehouse.Warehouse;

import java.io.IOException;
import java.util.*;
/**
 * The Remove class provides functionality for removing products from a warehouse.<br>
 * It implements the Delete and Command interfaces.
 */
public class Remove implements Delete, Command {
    private final ManageHistory removeProductsHistory;
    private final Warehouse warehouse;
    private final Scanner scanner;

    public Remove(ManageHistory removeProductsHistory, Warehouse warehouse) {
        this.removeProductsHistory = removeProductsHistory;
        this.warehouse = warehouse;
        this.scanner = new Scanner(System.in);
    }
    /**
     * Removes a specified quantity of a product from the given product list.<br>
     * If the total available quantity of the product is less than the specified quantity,<br>
     * the method will prompt the user to confirm the removal of all available quantity.<br>
     * The algorithm works as follows:<br>
     * Check if the product list contains the specified product name.<br>
     *    If the product exists, sort the products by expiry date in ascending order.<br>
     *    Accumulate quantities of products from the sorted list until the accumulated quantity is greater than or equal to the specified quantity to be removed.<br>
     *    If the accumulated quantity is less than the specified quantity, prompt the user to confirm the removal of the remaining available products.<br>
     *    If the user confirms, remove all available products and update the removal history.<br>
     * If the accumulated quantity is sufficient, iterate through the products and remove the required quantity, adjusting quantities as necessary.<br>
     * Update the removal history with the details of the removed products.<br>
     *
     * @param name        The name of the product to be removed.<br>
     * @param quantity    The quantity of the product to be removed.<br>
     * @param productList The map containing lists of products by name.<br>
     * @return A string indicating the names of the products removed.<br>
     * @throws NegativeNumberException if the quantity to be removed is negative.<br>
     * @throws IOException             if an I/O error occurs.<br>
     */
    @Override
    public String remove(String name, double quantity, Map<String, List<Product>> productList) throws NegativeNumberException, IOException {
        boolean flag = false;
        StringBuilder sb = new StringBuilder();
        if (productList.containsKey(name))   {
            List<Product> products = productList.get(name);
            List<Product> tempProducts = new ArrayList<>();
            assert products != null;
            products.sort(Comparator.comparing(Product::getExpiryDate));
            double sum = 0;
            for (Product product : products) {
                sum += product.getQuantity();
                tempProducts.add(product);
                if (sum >= quantity) {
                    break;
                }
            }
            if (sum < quantity) {
                System.out.println("There are " + sum + " " + name + " products, Do you want to remove them or not. YES OR NO");
                Scanner sc = new Scanner(System.in);
                String answer = sc.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    for (int i = 0; i < products.size(); i++) {
                        sb.append(products.get(i).getName()).append(System.lineSeparator());
                        removeProductsHistory.addNewChange(products.get(i).getArrivalDate(), products.get(i).getName(), products.get(i).getQuantity(),true);
                        productList.get(products.get(i).getName()).remove(products.get(i));
                        System.out.println("Product removed successfully");
                        System.out.println(tempProducts.get(i).getLocation().toString());

                    }
                }
            } else {
                Random random = new Random();
                int day = random.ints(1, 29).findFirst().getAsInt();
                int month = random.ints(1, 13).findFirst().getAsInt();
                int year = random.ints(2020, 2025).findFirst().getAsInt();
                double saveLastQuantity = 0;
                String removalDate = day + "/" + month + "/" + year;
                for (int i = 0; i < tempProducts.size(); i++) {
                    double tempQuantity = tempProducts.get(i).getQuantity();
                    double rest = quantity - tempQuantity;
                    saveLastQuantity = rest;
                    if (rest < 0) {
                        saveLastQuantity = 0.0;
                        productList.get(tempProducts.get(i).getName()).get(i).setQuantity(tempQuantity - quantity);
                        System.out.println("Products removed successfully");
                        System.out.println(tempProducts.get(i).getLocation().toString());
                        break;
                    }
                    sb.append(tempProducts.get(i).getName()).append(System.lineSeparator());
                    productList.get(tempProducts.get(i).getName()).remove(products.get(i));
                    System.out.println("Product removed successfully!");
                    System.out.println(tempProducts.get(i).getLocation().toString());

                }
                removeProductsHistory.addNewChange(removalDate, name, quantity - saveLastQuantity,true);

            }
        } else {
            System.out.println("No Products with that name!");
        }
        return sb.toString();
    }

    /**
     * Allows to the user to prompt the data for the product to be removed and the quantity of it
     * and executes the actual implementation of the command
     */
    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException, IOException {
        System.out.println("Which product you want to remove and how much of it?");
        String name = scanner.nextLine();
        int quantity = Integer.parseInt(scanner.nextLine());
        name = name.toUpperCase();
        warehouse.remove(name, quantity);

    }
}
