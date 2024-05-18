package bg.tu_varna.sit.interfaces;

import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.models.Product;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Another layer of abstraction for the actual implementation of the add method
 */
public interface Add {
    void add(Map<String, List<Product>> listOfProducts, Product product) throws LocationException, NegativeNumberException, IOException;
}
