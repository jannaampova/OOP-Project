import java.util.ArrayList;
import java.util.List;

public class Shelf {
    private final int capacityOnShelf;

    private List<Product> products;

    public Shelf() {
        this.capacityOnShelf = 20;
        this.products = new ArrayList<>();
    }

    public int getCapacityOnShelf() {
        return capacityOnShelf;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProductWithSameExpiryDate(Product product) {
        if (product.getQuantity() < capacityOnShelf) {
            for (Product p : products) {
                if (p.getName().equals(product.getName())) {
                    if (p.getExpiryDate().equals(product.getExpiryDate())) {
                        if (p.getQuantity() + product.getQuantity() < capacityOnShelf) {
                            p.setQuantity(product.getQuantity() + p.getQuantity());
                        }
                    }
                }
            }
        }
    }

    public void add(Product product){
        if (product.getQuantity() < capacityOnShelf) {
            products.add(product);
        }
    }
}
