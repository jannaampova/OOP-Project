import sun.font.CreatedFontTracker;

import java.util.*;

public class Remove implements Delete{
    @Override
    public String remove(String name, int quantity, Map<String, List<Product>> productList) {
        List<Product> products = productList.get(name);
        List<Product> tempProducts = new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        products.sort(Comparator.comparing(Product::getExpiryDate));
        int sum=0;
        for (Product product : products) {
            sum+=product.getQuantity();
            tempProducts.add(product) ;
            if(sum>=quantity){
                break;
            }

        }
        if(sum<quantity){
            System.out.println("There are "+sum+" "+name+" products, Do you want to remove them or not. YES OR NO");
            Scanner sc=new Scanner(System.in);
            String answer = sc.nextLine();
            if(answer.equalsIgnoreCase("yes")){
                for(int i=0;i< products.size();i++){
                    sb.append(products.get(i).getName()).append(System.lineSeparator());
                    int tempQuantity = products.get(i).getQuantity();
                    productList.get(products.get(i).getName()).remove(products.get(i));
                }
            }
        }else{
            for(int i=0;i< products.size();i++){
                int tempQuantity = products.get(i).getQuantity();
                int rest=quantity-tempQuantity;
                if(rest<0){
                    productList.get(products.get(i).getName()).get(i).setQuantity(tempQuantity-quantity);
                    break;
                }
                sb.append(products.get(i).getName()).append(System.lineSeparator());

                productList.get(products.get(i).getName()).remove(products.get(i));
            }
        }
       return sb.toString();
    }

}
