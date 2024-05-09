import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Clean {
    private ValidateDate dateValidation;
    private Map<String, Map<String, Double>> menageLossOfProducts;

    public Clean() {
        this.dateValidation = new ValidateDate();
        this.menageLossOfProducts = new HashMap<>();
    }

    public void cleanProducts(Map<String, List<Product>> products, String date) {
        String day = dateValidation.getDay(date);
        String month = dateValidation.getMonth(date);
        String year = dateValidation.getYear(date);
        LocalDate givenDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        System.out.println("Products with expired date or near expiry date");
        for (Map.Entry<String, List<Product>> product : products.entrySet()) {

            for (int i = 0; i < product.getValue().size(); i++) {
                String expiryDate = product.getValue().get(i).getExpiryDate();
                String curDay = dateValidation.getDay(expiryDate);
                String curMonth = dateValidation.getMonth(expiryDate);
                String curYear = dateValidation.getYear(expiryDate);
                LocalDate curDate = LocalDate.of(Integer.parseInt(curYear), Integer.parseInt(curMonth), Integer.parseInt(curDay));
                String keyOfTheInnerMap = product.getValue().get(i).getName();
                if (curDate.isBefore(givenDate)) {
                    menageLossOfProducts.putIfAbsent(expiryDate, new HashMap<>());
                    menageLossOfProducts.get(expiryDate).putIfAbsent(keyOfTheInnerMap, 0.0);
                    menageLossOfProducts.get(expiryDate).put(keyOfTheInnerMap, product.getValue().get(i).getQuantity() + menageLossOfProducts.get(expiryDate).get(keyOfTheInnerMap));
                    System.out.printf("Name: %s, Expiry date: %s, Quantity: %.2f EXPIRED %n", keyOfTheInnerMap, expiryDate, product.getValue().get(i).getQuantity());
                    product.getValue().remove(product.getValue().get(i));
                    i--;
                } else if (curDate.getYear() == givenDate.getYear()) {
                    if (curDate.getMonthValue() == givenDate.getMonthValue()) {
                        int res = givenDate.getDayOfMonth() - curDate.getDayOfMonth();
                        if (res <= 3 && res > 0) {
                            menageLossOfProducts.putIfAbsent(expiryDate, new HashMap<>());
                            menageLossOfProducts.get(expiryDate).putIfAbsent(keyOfTheInnerMap, 0.0);
                            menageLossOfProducts.get(expiryDate).put(keyOfTheInnerMap, product.getValue().get(i).getQuantity() + menageLossOfProducts.get(expiryDate).get(keyOfTheInnerMap));
                            System.out.printf("Name: %s, Expiry date: %s, Quantity: %.2f DAYS TO EXPIRY %d %n", keyOfTheInnerMap, expiryDate, product.getValue().get(i).getQuantity(), res);
                            product.getValue().remove(product.getValue().get(i));
                            i--;
                        }

                    }
                }
            }
        }
    }


    public void calculateLoss(String fromDate, String toDate, String nameOfProduct, double price, String unity) {
        String fromDay = dateValidation.getDay(fromDate);
        String fromMonth = dateValidation.getMonth(fromDate);
        String fromYear = dateValidation.getYear(fromDate);
        String toDay = dateValidation.getDay(toDate);
        String toMonth = dateValidation.getMonth(toDate);
        String toYear = dateValidation.getYear(toDate);
        LocalDate fromDate1 = LocalDate.of(Integer.parseInt(fromYear), Integer.parseInt(fromMonth), Integer.parseInt(fromDay));
        LocalDate toDate1 = LocalDate.of(Integer.parseInt(toYear), Integer.parseInt(toMonth), Integer.parseInt(toDay));

        double result=0.0;

        for (Map.Entry<String, Map<String, Double>> stringMapEntry : menageLossOfProducts.entrySet()) {
            String key = stringMapEntry.getKey();
            String year = dateValidation.getYear(key);
            String day = dateValidation.getDay(key);
            String month = dateValidation.getMonth(key);
            LocalDate curDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            if (!curDate.isBefore(fromDate1) && !curDate.isAfter(toDate1)) {
                Map<String, Double> value = stringMapEntry.getValue();
                if (value.containsKey(nameOfProduct)) {
                    double quantityOfProduct = value.get(nameOfProduct);
                    if (unity.equalsIgnoreCase("kg")) {
                         result = quantityOfProduct * price;
                    } else if (unity.equalsIgnoreCase("pieces")) {
                        result = Math.floor(quantityOfProduct);
                        result*=price;

                    }
                }

            }
        }
     if(result==0){
            System.out.println("No products with that name");;
        }else System.out.printf("The loss of the product %s in the period %s-%s is %.2f %n",nameOfProduct,fromDate,toDate,result);
    }

}


