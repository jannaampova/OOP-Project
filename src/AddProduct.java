import exeptions.LocationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddProduct implements Add {
    private ManageHistory addProduct;
    private ValidateDate dateValidation;


    public AddProduct(ManageHistory addProduct, ValidateDate dateValidation) {
        this.addProduct = addProduct;
        this.dateValidation = dateValidation;
    }

    @Override
    public void add(Map<String, List<Product>> listOfProducts, Product product) throws LocationException {
        boolean flag = true;
        addProduct.addNewChange(product.getArrivalDate(), product.getName(), product.getQuantity());
        for (Map.Entry<String, List<Product>> stringListEntry : listOfProducts.entrySet()) {
            if (stringListEntry.getKey().equals(product.getName())) {
                String expiryDateNew = product.getExpiryDate();


                for (Product p : stringListEntry.getValue()) {
                    String expiryDateCurr = p.getExpiryDate();

                    if (checkDateFormat(expiryDateNew, expiryDateCurr)) {

                        flag = false;
                        if (!addWithSameExpiryDate(product.getQuantity(), p)) {

                            double availableSpace = p.getLocation().getShelfCapacity() - p.getQuantity();
                            throw new LocationException("You can add only " + availableSpace + " " + product.getName() + " at location" + p.getLocation().toString());
                        }
                    }

                }
                if (flag) {
                    flag = false;
                    addWithDifferentExpiryDate(product, stringListEntry.getValue());
                }

            }// ako ne systestvuva s tova ime

        }
        if (flag) {
            Location location = new Location(product.getName(), product.getQuantity());

            product.setLocation(location);
            addNotExistingProduct(product, listOfProducts);

        }
    }


    public boolean checkDateFormat(String expiryDateNew, String expiryDateCurr) {

        if (expiryDateNew.equals(expiryDateCurr)) {
            return true;
        }
        return false;
    }

    public boolean addWithSameExpiryDate(double quantity, Product product) throws LocationException {
        double newQuantity = product.getQuantity() + quantity;
        product.setQuantity(newQuantity);
        product.getLocation().setQuantity(newQuantity);
        return true;
    }

    public void addWithDifferentExpiryDate(Product product, List<Product> productList) throws LocationException {

        Location location = new Location(product.getName(), product.getQuantity());
        product.setLocation(location);
        productList.add(product);


    }

    public void addNotExistingProduct(Product product, Map<String, List<Product>> stringListProduct) {
        stringListProduct.put(product.getName(), new ArrayList<>());
        stringListProduct.get(product.getName()).add(product);
    }

}