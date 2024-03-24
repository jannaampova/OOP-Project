import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse implements ModificationOnProducts {
private Location location=new Location();
    private Map<String, List<Location>> productMap = new HashMap<>();//KLUCHA E IMETO NA PRODUKT I SE SVYRZVA SYS SAMITE DANNI
    private static int sectionNumber = 0;
    private final int sectionCapacityInWarehouse=30;
    public void add(Product product) {
        if (productMap.containsKey(product.getName())) {
            for (List<Location> locations : productMap.values()) {
                for (Location location : locations) {
                    for (Section section : location.getSection()) {
                        Shelf shelf = findShelfWithMatchingProduct(section, product);
                        if (shelf != null) {
                            shelf.addProductWithSameExpiryDate(product);
                            return;
                        } else {
                            // If no matching product, try to find an empty shelf
                            Shelf emptyShelf = findEmptyShelf(section);
                            if (emptyShelf != null) {
                                emptyShelf.add(product);
                                return;
                            }
                        }
                    }
                }
            }
        }else{  for (List<Location> locations : productMap.values()) {
            for (Location location : locations) {
                for (Section section : location.getSection()) {
                    Shelf emptyShelf = findEmptyShelf(section);
                    if (emptyShelf != null) {
                        emptyShelf.add(product);
                        return;
                    }
                }
            }
        }}

    }
    private Shelf findShelfWithMatchingProduct(Section section, Product product) {
        for (Shelf shelf : section.getShelves()) {
            if (!shelf.getProducts().isEmpty() && shelf.getProducts().get(0).getName().equals(product.getName())) {
                for (Product existingProduct : shelf.getProducts()) {
                    if (existingProduct.getExpiryDate().equals(product.getExpiryDate())) {
                        return shelf;
                    }
                }
            }
        }
        return null;
    }

    private Shelf findEmptyShelf(Section section) {
        for (Shelf shelf : section.getShelves()) {
            if (shelf.getProducts().isEmpty()) {
                return shelf;
            }
        }
        return null;
   /* public void add(Product product) {
        int counter=0;
        if (productMap.containsKey(product.getName())) {
            for(Map.Entry<String,List<Location>> i:productMap.entrySet()){
                for (Location l : i.getValue()) {
                    ++counter;
                    if (l.getSection().get(counter).getShelves().get(counter).getProducts().get(counter).getExpiryDate().equals(product.getExpiryDate())) {
                        l.getSection().get(counter).getShelves().get(counter).addProductWithSameExpiryDate(product);
                    }
                    else   l.getSection().get(counter).addProduct(product);

                }
            }}

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


