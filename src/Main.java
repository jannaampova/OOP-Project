import exeptions.LocationException;
import sun.util.resources.LocaleData;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws LocationException {
        Product product1 = new Product("MILK", "30/6/2024", "15/1/2024", "ВЕРЕЯ", "КГ", 15, "Older");
        Product product2 = new Product("MILK", "10/5/2024", "18/1/2024", "ВЕРЕЯ", "КГ", 20, "Newer");
        Product product3 = new Product("MILK", "10/5/2024", "20/1/2024", "ВЕРЕЯ", "КГ", 4, "Newer");
        Product product4 = new Product("MILK", "10/5/2024", "26/1/2024", "ВЕРЕЯ", "КГ", 235, "Newer");
        Product product5 = new Product("PROBA", "15/5/2024", "22/2/2024", "ВЕРЕЯ", "КГ", 235, "Newer");

        Warehouse warehouse = new Warehouse();
        warehouse.add(product1);
        warehouse.add(product2);
        warehouse.add(product3);
        warehouse.add(product5);
        warehouse.remove("MILK", 13);
        warehouse.remove("MILK", 5);
        System.out.println();

        //ValidateDate date=new ValidateDate();
        //Scanner sc=new Scanner(System.in);
        //String inputDate=sc.next();
        //date.validate(inputDate);
        warehouse.showHistory("1/9/2022", "19/1/2024");
        warehouse.clean("11/5/2024");
warehouse.loss("1/5/2024", "30/5/2024","MILK",3.14,"KG");
    }
}