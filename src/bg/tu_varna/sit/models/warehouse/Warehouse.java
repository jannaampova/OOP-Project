package bg.tu_varna.sit.models.warehouse;

import bg.tu_varna.sit.models.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {
    private static Warehouse instance;
    private final Map<String, List<Product>> products;
    private String currentFilePath;

    private Warehouse() {
        this.products = new HashMap<>();
    }

    public static Warehouse getInstance() {
        if (instance == null) {
            instance = new Warehouse();
        }
        return instance;
    }

    public Map<String, List<Product>> getProducts() {
        return products;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }


    public String toFile() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name,ExpiryDate,ArrivalDate,Manufacturer,Unity,Quantity,Comment,Sector,RealShelf").append(System.lineSeparator());
        for (Map.Entry<String, List<Product>> productEntry : products.entrySet()) {
            for (Product product : productEntry.getValue()) {
                sb.append(product.getName()).append(",").append(product.getExpiryDate()).append(",").append(product.getArrivalDate()).append(",").append(product.getManufacturer()).append(",").append(product.getUnity()).append(",").append(product.getQuantity()).append(",").append(product.getComment()).append(",").append(product.getLocation().getSector()).append(",").append(product.getLocation().getRealShelf()).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
