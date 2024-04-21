import exeptions.LocationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {
private Map<String,List<Product>> products;
private Add add;
private Delete remove;

    public Warehouse() {
        this.products = new HashMap<>();
        this.add=new AddProduct();
        this.remove=new Remove();
    }
    public void add(Product product) throws LocationException {
        this.add.add(products,product);
    }
    public void remove(String name,int quantity) throws LocationException {
        System.out.println(this.remove.remove(name, quantity, products));
    }

}


