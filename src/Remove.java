import sun.security.pkcs11.wrapper.CK_SSL3_RANDOM_DATA;

import java.time.LocalDate;
import java.util.*;

public class Remove implements Delete {
    private ManageHistory removeProductsHistory;


    public Remove(ManageHistory removeProductsHistory) {
        this.removeProductsHistory = removeProductsHistory;
    }

    @Override
    public String remove(String name, int quantity, Map<String, List<Product>> productList) {
        List<Product> products = productList.get(name);
        List<Product> tempProducts = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        products.sort(Comparator.comparing(Product::getExpiryDate));
        int sum = 0;
        for (Product product : products) {

            sum += product.getQuantity();
            tempProducts.add(product);

            if (sum >= quantity) {
                break;
            }

        }
        if (sum < quantity) {
            System.out.println("There are " + sum + " " + name + " products, Do you want to remove them or not. YES OR NO");
            Scanner sc = new Scanner(System.in);
            String answer = sc.nextLine();
            if (answer.equalsIgnoreCase("yes")) {
                for (int i = 0; i < products.size(); i++) {
                    sb.append(products.get(i).getName()).append(System.lineSeparator());
                    //   int tempQuantity = products.get(i).getQuantity();
                    removeProductsHistory.addNewChange(products.get(i).getArrivalDate(), products.get(i).getName(), products.get(i).getQuantity());

                    productList.get(products.get(i).getName()).remove(products.get(i));
                }
            }
        } else {
            Random random = new Random();
            int day = random.ints(1, 29).findFirst().getAsInt();
            int month = random.ints(1, 13).findFirst().getAsInt();
            int year = random.ints(2020, 2024).findFirst().getAsInt();
            int saveLastQuantity=0;
            StringBuilder removalDate = new StringBuilder();
            removalDate.append(day).append("/").append(month).append("/").append(year);
            for (int i = 0; i < tempProducts.size(); i++) {
                int tempQuantity = tempProducts.get(i).getQuantity();
                int rest = quantity - tempQuantity;
                saveLastQuantity=rest;

                if (rest < 0) {
                    saveLastQuantity=0;
                    productList.get(tempProducts.get(i).getName()).get(i).setQuantity(tempQuantity - quantity);
                    break;
                }
                sb.append(tempProducts.get(i).getName()).append(System.lineSeparator());

                productList.get(tempProducts.get(i).getName()).remove(products.get(i));
            }
            removeProductsHistory.addNewChange(removalDate.toString(),  name,quantity-saveLastQuantity);

        }
        return sb.toString();
    }

}
