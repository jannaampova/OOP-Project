import exeptions.LocationException;

import java.util.List;
import java.util.Map;

public interface Add {
    void add(Map<String, List<Product>> listOfProducts,Product product) throws LocationException;
}
