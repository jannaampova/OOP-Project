import exeptions.LocationException;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws LocationException {
        Product product1=new Product("MILK","30/06/2024","15/01/2024","ВЕРЕЯ","КГ",15,"Older");
        Product product2=new Product("MILK","10/05/2024","18/01/2024","ВЕРЕЯ","КГ",16, "Newer");
        Product product3=new Product("MILK","10/05/2024","20/01/2024","ВЕРЕЯ","КГ",4, "Newer");
        Product product4=new Product("MILK","10/05/2024","26/01/2024","ВЕРЕЯ","КГ",235, "Newer");
        Product product5=new Product("PROBA","12/05/2024","22/02/2024","ВЕРЕЯ","КГ",235, "Newer");

        Warehouse warehouse=new Warehouse();
        warehouse.add(product1);
        warehouse.add(product2);
        warehouse.add(product3);
        warehouse.add(product5);
        warehouse.remove("MILK",21);
        warehouse.remove("MILK",20);
        System.out.println();

        //ValidateDate date=new ValidateDate();
        //Scanner sc=new Scanner(System.in);
        //String inputDate=sc.next();
        //date.validate(inputDate);
        warehouse.showHistory("10/01/2024","19/01/2024");


    }
}