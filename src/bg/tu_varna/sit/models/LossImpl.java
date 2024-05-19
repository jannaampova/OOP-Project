package bg.tu_varna.sit.models;

import bg.tu_varna.sit.warehouse.Warehouse;
import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;

import java.util.Scanner;

/**
 * This class purpose is to  get the user input(name,price,unity of the product) for the wanted loss calculation, pass that input to the<br>
 * particular  method which connects the actual implementations in class clean, the  connections are through the warehouse<br>
 * and then retrieves info to the user. This class is used as an instance in the map which purpose is calling the user options.<br>
 * This class represents an instance in one of the values of the map managing all the commands<br>
 */
public class LossImpl implements Command {
    private Warehouse warehouse;
    private Scanner scanner=new Scanner(System.in);

    public LossImpl(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException {
        System.out.println("Enter the product name: ");
        String name=scanner.nextLine();
        name=name.toUpperCase();
        System.out.println("Enter the lower bound date: d/m/yyyy");
        String fromdate=scanner.nextLine();
        System.out.println("Enter the upper bound date: d/m/yyyy");
        String toDate=scanner.nextLine();
        System.out.println("Enter the price of the product");
        double price=scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter the unity of th product KG or PIECE: ");
        String unity=scanner.nextLine();
        warehouse.loss(fromdate,toDate,name,price,unity);
    }
}
