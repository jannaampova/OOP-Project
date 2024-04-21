import java.util.List;
import java.util.Map;

public interface Delete {
    public String remove(String name, int quantity, Map<String, List<Product>> productList);
}
