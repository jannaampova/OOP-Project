import exeptions.LocationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {
private Map<String,List<Product>> products;
private Add add;

    public Warehouse() {
        this.products = new HashMap<>();
        this.add=new AddProduct();
    }
    public void add(Product product) throws LocationException {
        this.add.add(products,product);
    }

}


