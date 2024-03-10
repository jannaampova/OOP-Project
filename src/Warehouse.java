import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse implements ModificationOnProducts {

    private Map<String, Product> productMap = new HashMap<>();//KLUCHA E IMETO NA PRODUKT I SE SVYRZVA SYS SAMITE DANNI
private static int sectionNumber=0;
    @Override
    public void add(Product product) {

        if (productMap.containsKey(product.getName())) {

            if (!product.getExpiryDate().equals(productMap.get(product.getName()).getExpiryDate())) {
                addProductWithDifferentExpiryDate(product);

            } else if (product.getExpiryDate().equals(productMap.get(product.getName()).getExpiryDate())) {
                addProductWithSameExpiryDate(product);
            }
        }
        else{
            //kogato e nov produkt??
            Section section=new Section(++sectionNumber,10);
            List<Section> sectionList1=new ArrayList<>();
            sectionList1.add(section);
            Shelf shelf=new Shelf(product.getQuantity());
            sectionList1.get(sectionNumber-1).addShelf(shelf);
            Location location1=new Location(sectionList1,10);
            product.setLocation(location1);
            productMap.put(product.getName(),product);
        }
    }

    public void addProductWithDifferentExpiryDate(Product product) {
        Location location = productMap.get(product.getName()).getLocation();
        for (Section s : location.getSection()) {
            for (int i = 1; i < s.getShelves().size(); i++) {
                if (!s.getShelves().contains(i)) {
                    productMap.get(product.getName()).getLocation().getSection().get(i).addShelf(new Shelf(product.getQuantity()));
                    break;
                }
            }
        }
    }
    public void addProductWithSameExpiryDate(Product product) {
        Location location = productMap.get(product.getName()).getLocation();
        for (Section s : location.getSection()) {
            for (int i = 1; i < s.getShelves().size(); i++) {
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
                    for(Section s:products.getValue().getLocation().getSection()){
                        for(Shelf shelf:s.getShelves()){
                            quantitySum += shelf.getProductNum();;
                        }
                    }
                }
            }
            System.out.println("Има "+quantitySum+"бройки на продукт с име "+product.getName()+" ");
        }
    }

    @Override
    public void remove(String name, int quantity) {

    }
}


