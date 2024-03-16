
import java.util.ArrayList;
import java.util.List;

public class Section {

    private int number;
    private List<Shelf> shelves = new ArrayList<>();
    private int shelfCapacityInSection;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public List<Shelf> getShelves() {
        return shelves;
    }


    public int getShelfCapacityInSection() {
        return shelfCapacityInSection;
    }

    public void setShelfCapacityInSection(int shelfCapacity) {
        this.shelfCapacityInSection = shelfCapacity;
    }

    public Section(int number, int shelfCapacityInSection) {
        this.number = number;
        this.shelfCapacityInSection = shelfCapacityInSection;

    }

    public void addProduct(Product product) {
        boolean flag = false;
        for (Shelf s : shelves) {
            for (Product p : s.getProducts()) {
                if (p.getName().equals(product.getName())) {
                    if (!p.getExpiryDate().equals(product.getExpiryDate())) {
                        break;
                    } else {
                        s.addProductWithSameExpiryDate(product);
                        flag = true;
                        break;
                    }
                }

            }
            if (flag) {
                break;
            }


        }
        if (!flag) {
            for (Shelf s : shelves) {
                boolean flag1 = false;
                for (Product p : s.getProducts()) {
                    if (p.getName().equals(product.getName())) {
                            flag1 = true;
                            break;
                    }
                }
                if(!flag1){
                    s.add(product);
                }
            }


        }
    }
}



