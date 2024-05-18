package bg.tu_varna.sit.warehouse;

import bg.tu_varna.sit.interfaces.Add;
import bg.tu_varna.sit.interfaces.Delete;
import bg.tu_varna.sit.interfaces.ManageHistory;
import bg.tu_varna.sit.interfaces.Print;
import bg.tu_varna.sit.models.*;
import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.menageHistory.AddedProductHistory;
import bg.tu_varna.sit.menageHistory.RemoveProductHistory;
import bg.tu_varna.sit.validateDate.ValidateDate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {
    private static Warehouse instance;
    private Map<String, List<Product>> products;
    private Add add;
    private Delete remove;
    private Clean cleaner;
    private Print print;

    private ManageHistory addProductHistory;
    private ManageHistory removeProductHistory;
    private String currentFilePath;

    private Warehouse() {
        this.products = new HashMap<>();
        this.addProductHistory = new AddedProductHistory();
        this.add = new AddProduct(addProductHistory, new ValidateDate(), this);
        this.removeProductHistory = new RemoveProductHistory();
        this.cleaner = new Clean(this);
        this.remove = new Remove(removeProductHistory, this);
        this.print = new PrintData(this);
    }

    public static Warehouse getInstance() {
        if (instance == null) {
            instance = new Warehouse();
        }
        return instance;
    }

    public void add(Product product) throws LocationException, NegativeNumberException, IOException {
        this.add.add(products, product);
    }

    public void remove(String name, int quantity) throws LocationException, NegativeNumberException, IOException {
        System.out.println(this.remove.remove(name, quantity, products));
    }

    public void showHistory(String fromDate, String toDate) throws IOException {

        addProductHistory.getHistoryInfo(fromDate, toDate);
        removeProductHistory.getHistoryInfo(fromDate, toDate);
    }

    public void clean(String date) {
        cleaner.cleanProducts(products, date);
    }

    public void loss(String fromDate, String toDate, String name, double price, String unity) {
        cleaner.calculateLoss(fromDate, toDate, name, price, unity);
    }

    public String printData() {
        return print.printData(products);
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    public  String toFile() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name,ExpiryDate,ArrivalDate,Manufacturer,Unity,Quantity,Comment,Sector,RealShelf").append(System.lineSeparator());

        for (Map.Entry<String, List<Product>> productEntry : products.entrySet()) {
            for (Product product : productEntry.getValue()) {
                sb.append(product.getName()).append(",")
                        .append(product.getExpiryDate()).append(",")
                        .append(product.getArrivalDate()).append(",")
                        .append(product.getManufacturer()).append(",")
                        .append(product.getUnity()).append(",")
                        .append(product.getQuantity()).append(",")
                        .append(product.getComment()).append(",")
                        .append(product.getLocation().getSector()).append(",")
                        .append(product.getLocation().getRealShelf())
                        .append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}