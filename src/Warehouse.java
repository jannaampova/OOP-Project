import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse implements ModificationOnProducts {
private Location location=new Location();
    private Map<String, List<Product>> productMap = new HashMap<>();//KLUCHA E IMETO NA PRODUKT I SE SVYRZVA SYS SAMITE DANNI
    private static int sectionNumber = 0;
    private final int sectionCapacityInWarehouse=30;


    public void add(Product product) {
/*
        if (productMap.containsKey(product.getName())) {
            for (Map.Entry<String, List<Product>> i : productMap.entrySet()) {
                for (Product p : i.getValue()) {
                    if (p.getExpiryDate().equals(product.getExpiryDate())) {
                        addProductWithSameExpiryDate(product);
                        return;
                    }
                }
            }
            addProductWithDifferentExpiryDate(product);
        }
     else
     {
         if(sectionNumber<=sectionCapacityInWarehouse) {
             Section section = new Section(++sectionNumber, 10);
             List<Section> sectionList1 = new ArrayList<>();
             sectionList1.add(section);
             Shelf shelf = new Shelf(product.getQuantity());
             sectionList1.get(sectionNumber - 1).addOnShelf(shelf);
             Location location1 = new Location(sectionList1, 10);
             product.setLocation(location1);
             productMap.put(product.getName(), product);
         }
    }

}

    public void addProductWithDifferentExpiryDate(Product product) {
        Location location = productMap.get(product.getName()).getLocation();
        for (int i = 0; i < location.getSection().size(); i++) {
            Section s = location.getSection().get(i);
            if (s.getShelves().size() < s.getShelfCapacityInSection()) {
                productMap.get(product.getName()).getLocation().getSection().get(i).addShelf(new Shelf(product.getQuantity()));

            }
        }
    }

    public void addProductWithSameExpiryDate(Product product) {
        Location location = productMap.get(product.getName()).getLocation();
        for (Section s : location.getSection()) {
            for (int i = 0; i < s.getShelves().size(); i++) {
                int currentQuantity = s.getShelves().get(i).getProductNum() + product.getQuantity();
                if (s.getShelves().get(i).getCapacityOnShelf() > currentQuantity) {
                    s.getShelves().get(i).setProductNum(currentQuantity);
                }//nedovyrsheb=no v sluchai che nyama myasto
            }
        }

    }

    public void printAllWithTheSameName(Product product) {
        if (productMap.containsKey(product.getName())) {
            int quantitySum = 0;
            for (Map.Entry<String, Product> products : productMap.entrySet()) {
                if (products.getKey().equals(product.getName())) {
                    for (Section s : products.getValue().getLocation().getSection()) {
                        for (Shelf shelf : s.getShelves()) {
                            quantitySum += shelf.getProductNum();
                            ;
                        }
                    }
                }
            }
            System.out.println("Има " + quantitySum + "бройки на продукт с име " + product.getName() + " ");
        }
        */
    }

    @Override
    public void remove(String name, int quantity) {

    }
}


