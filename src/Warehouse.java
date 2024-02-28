import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse implements ModificationOnProducts{

    Map<String, Product> productMap = new HashMap<>();

    @Override
    public void Add(Product product) {
        Location location = productMap.get(product.getName()).getLocation();
        List<Integer> shelfList = location.getSection().getShelves();
        if (productMap.containsKey(product.getName())) {
            if (!product.getExpiryDate().equals(productMap.get(product.getName()).getExpiryDate())) {
                for (int i = 1; i < location.getSection().getShelfCapacity(); i++) {
                    if (!shelfList.contains(i)) {
                        productMap.get(product.getName()).getLocation().getSection().addShelf(i);
                        break;
                    }
                }
            } else if (product.getExpiryDate().equals(productMap.get(product.getName()).getExpiryDate())) {
                productMap.get(product.getName()).setQuantity(productMap.get(product.getName()).getQuantity() + product.getQuantity());
            }
        }
    }


    @Override
    public void Remove(String name, int quantity) {

    }
}


