import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddedProductHistory implements ManageHistory {
    private Map<String, Map<String, Double>> addedProducts;//data ime na produkt i kolicestvo
   private ValidateDate dateValidation;

    public AddedProductHistory() {
        this.addedProducts = new HashMap<>();
        this.dateValidation = new ValidateDate();
    }

    @Override
    public void addNewChange(String date, String name, double quantity) {
        if (addedProducts.containsKey(date)) {
            if (addedProducts.get(date).containsKey(name)) {
                Map<String, Double> stringIntegerMap = addedProducts.get(date);
                stringIntegerMap.put(name, stringIntegerMap.get(name) + quantity);
                addedProducts.put(date, stringIntegerMap);
            } else {
                addedProducts.putIfAbsent(date, new HashMap<String, Double>());
                addedProducts.get(date).put(date, quantity);
            }

        } else {
            Map<String, Double> stringIntegerMap = new HashMap<>();
            addedProducts.putIfAbsent(date, new HashMap<String, Double>());
            stringIntegerMap.put(name, quantity);
            addedProducts.put(date, stringIntegerMap);

        }

    }

    public void getHistoryInfo(String fromDate, String toDate) {
        String fromDay = dateValidation.getDay(fromDate);
        String fromMonth = dateValidation.getMonth(fromDate);
        String fromYear = dateValidation.getYear(fromDate);
        String toDay = dateValidation.getDay(toDate);
        String toMonth = dateValidation.getMonth(toDate);
        String toYear = dateValidation.getYear(toDate);
        System.out.println("Added products in the warehouse from date " + fromDate + " to date" + toDate);

        for (Map.Entry<String, Map<String, Double>> stringMapEntry : addedProducts.entrySet()) {

            String date = stringMapEntry.getKey();
            String day = dateValidation.getDay(date);
            String month = dateValidation.getMonth(date);
            String year = dateValidation.getYear(date);
            LocalDate localDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            if (localDate.isBefore(LocalDate.of(Integer.parseInt(toYear), Integer.parseInt(toMonth), Integer.parseInt(toDay))) && localDate.isAfter(LocalDate.of(Integer.parseInt(fromYear), Integer.parseInt(fromMonth), Integer.parseInt(fromDay)))) {
                for (Map.Entry<String, Double> innerMap : stringMapEntry.getValue().entrySet()) {
                    System.out.println("Product: " + innerMap.getKey() + ", quantity: " + innerMap.getValue() + " DATE: " + date);
                }
            }
        }
    }

}

