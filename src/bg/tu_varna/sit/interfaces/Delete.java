package bg.tu_varna.sit.interfaces;

import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.models.Product;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Another layer of abstraction for the actual implementation of the remove method
 */
public interface Delete {
    public String remove(String name, double quantity, Map<String, List<Product>> productList) throws NegativeNumberException, IOException;
}
