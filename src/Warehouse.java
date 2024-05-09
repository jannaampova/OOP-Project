import exeptions.LocationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {
    private Map<String, List<Product>> products;
    private Add add;
    private Delete remove;
    private Clean cleaner;

    private ManageHistory addProductHistory;
    private ManageHistory removeProductHistory;

    public Warehouse() {
        this.products = new HashMap<>();
        this.addProductHistory = new AddedProductHistory();
        this.add = new AddProduct(addProductHistory, new ValidateDate());
        this.removeProductHistory = new RemoveProductHistory();
        this.cleaner = new Clean();
        this.remove = new Remove(removeProductHistory);
    }

    public void add(Product product) throws LocationException {
        this.add.add(products, product);
    }

    public void remove(String name, int quantity) throws LocationException {
        System.out.println(this.remove.remove(name, quantity, products));
    }

    public void showHistory(String fromDate, String toDate) {
        addProductHistory.getHistoryInfo(fromDate, toDate);
        removeProductHistory.getHistoryInfo(fromDate, toDate);

    }
    public void clean(String date){
        cleaner.cleanProducts(products,date);
    }
    public void loss(String fromDate,String toDate,String name,double price,String unity){
        cleaner.calculateLoss(fromDate,toDate,name,price,unity);
    }


}


