import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RemoveProductHistory implements ManageHistory {
    Map<String, Map<String, Integer>> removedProducts;//data ime na produkt i kolicestvo
    ValidateDate dateValidation;

    public RemoveProductHistory() {
        this.removedProducts = new HashMap<>();
        this.dateValidation = new ValidateDate();
    }

    @Override
    public void addNewChange(String date, String name, int quantity) {
        if (removedProducts.containsKey(date)) {
            if (removedProducts.get(date).containsKey(name)) {
                Map<String, Integer> stringIntegerMap = removedProducts.get(date);
                stringIntegerMap.put(name, stringIntegerMap.get(name) + quantity);
                removedProducts.put(date, stringIntegerMap);
            } else {
                removedProducts.putIfAbsent(date, new HashMap<String, Integer>());
                removedProducts.get(date).put(date, quantity);
            }

        } else {
            Map<String, Integer> stringIntegerMap = new HashMap<>();
            removedProducts.putIfAbsent(date, new HashMap<String, Integer>());
            stringIntegerMap.put(name, quantity);

            removedProducts.put(date, stringIntegerMap);


        }

    }

    @Override
    public void getHistoryInfo(String fromDate, String toDate) {
        String fromDay = dateValidation.getDay(fromDate);
        String fromMonth = dateValidation.getMonth(fromDate);
        String fromYear = dateValidation.getYear(fromDate);
        String toDay = dateValidation.getDay(toDate);
        String toMonth = dateValidation.getMonth(toDate);
        String toYear = dateValidation.getYear(toDate);
        System.out.println("Removed products in the warehouse from date " + fromDate + " to date" + toDate);

        for (Map.Entry<String, Map<String, Integer>> stringMapEntry : removedProducts.entrySet()) {
            String date = stringMapEntry.getKey();
            String day = dateValidation.getDay(date);
            String month = dateValidation.getMonth(date);
            String year = dateValidation.getYear(date);

            LocalDate localDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            if (localDate.isBefore(LocalDate.of(Integer.parseInt(toYear), Integer.parseInt(toMonth), Integer.parseInt(toDay))) && localDate.isAfter(LocalDate.of(Integer.parseInt(fromYear), Integer.parseInt(fromMonth), Integer.parseInt(fromDay)))) {
                for (Map.Entry<String, Integer> innerMap : stringMapEntry.getValue().entrySet()) {
                    System.out.println("Product: " + innerMap.getKey() + ", quantity: " + innerMap.getValue() + " DATE: " + date);
                }
            }
        }
    }
}
