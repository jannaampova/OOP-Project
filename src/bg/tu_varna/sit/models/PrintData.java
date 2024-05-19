package bg.tu_varna.sit.models;

import bg.tu_varna.sit.interfaces.Print;
import bg.tu_varna.sit.warehouse.Warehouse;
import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;

import java.util.List;
import java.util.Map;

/**
 * This class purpose is to output the information about the products in the warehouse in the current moment<br>
 * It iterates through  the main map(Product name-List of products with that name and their details) and gets the information out of it
 */
public class PrintData implements Print, Command {


    private Warehouse warehouse;

    public PrintData(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public String printData(Map<String, List<Product>> listOfProducts) {
        StringBuilder sb = new StringBuilder();
        sb.append("PRODUCTS: ");
        int i = 0;
        for (Map.Entry<String, List<Product>> productEntry : listOfProducts.entrySet()) {
            String productName = productEntry.getKey();
            List<Product> products = productEntry.getValue();
            for (Product product : products) {
                i++;
                sb.append(i).append(". ").append(productName).append(System.lineSeparator())
                        .append(" quantity: ").append(product.getQuantity())
                        .append("  manufacturer: ").append(product.getManufacturer())
                        .append(" arrival date: ").append(product.getArrivalDate())
                        .append(" expiry date: ").append(product.getExpiryDate())
                        .append(" location:").append(System.lineSeparator())
                        .append(" Sector: ").append(product.getLocation().getSector())
                        .append("\t\t Shelf number: ")
                        .append(product.getLocation().getRealShelf())
                        .append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException {
        System.out.println(warehouse.printData());
    }
}
