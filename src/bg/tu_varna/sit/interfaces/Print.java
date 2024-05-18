package bg.tu_varna.sit.interfaces;

import bg.tu_varna.sit.models.Product;

import java.util.List;
import java.util.Map;

public interface Print {
    String printData(Map<String, List<Product>> listOfProducts);
}
