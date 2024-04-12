import exeptions.LocationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddProduct implements Add {
    @Override
    public void add(Map<String, List<Product>> listOfProducts, Product product) throws LocationException {
        // Location location = product.getLocation();// location on product that will be added
        boolean flag=true;

        for (Map.Entry<String, List<Product>> stringListEntry : listOfProducts.entrySet()) {
            if (stringListEntry.getKey().equals(product.getName())) {
                String expiryDateNew = product.getExpiryDate();


                for (Product p : stringListEntry.getValue()) {
                    String expiryDateCurr = p.getExpiryDate();

                    if (checkDateFormat(expiryDateNew, expiryDateCurr)) {

                        flag=false;
                        if (!addWithSameExpiryDate(product.getQuantity(), p)) {

                            int availableSpace = p.getLocation().getShelfCapacity() - p.getQuantity();
                            throw new LocationException("You can add only " + availableSpace + " " + product.getName() + " at location" + p.getLocation().toString());
                        }
                    }

                }
                if(flag){
                    flag=false;
                    addWithDifferentExpiryDate(product,  stringListEntry.getValue());
                }

            }// ako ne systestvuva s tova ime

        }
        if(flag){
            Location location=new Location(product.getName(), product.getQuantity());

                product.setLocation(location);
                addNotExistingProduct(product,listOfProducts);

        }
    }


    public boolean checkDateFormat(String expiryDateNew, String expiryDateCurr) {

        if (expiryDateNew.equals(expiryDateCurr)) {
            return true;
        }
        return false;
    }

    public boolean addWithSameExpiryDate(int quantity, Product product) throws LocationException {
        int newQuantity = product.getQuantity() + quantity;
            product.setQuantity(newQuantity);
            product.getLocation().setQuantity(newQuantity);
            return true;
    }
    public void addWithDifferentExpiryDate(Product product, List<Product> productList) throws LocationException {

            Location location=new Location(product.getName(), product.getQuantity());
            product.setLocation(location);
            productList.add(product);



    }

    public void addNotExistingProduct(Product product,Map<String,List<Product>> stringListProduct){
        stringListProduct.put(product.getName(),new ArrayList<>());
        stringListProduct.get(product.getName()).add(product);
    }

}