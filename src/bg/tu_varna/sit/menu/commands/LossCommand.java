package bg.tu_varna.sit.menu.commands;

import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.models.functions.Clean;

import java.util.Scanner;

/**
 * This class purpose is to  get the user input(name,price,unity of the product) for the wanted loss calculation, pass that input to the<br>
 * particular  method which connects the actual implementations in class clean, the  connections are through the warehouse<br>
 * and then retrieves info to the user. This class is used as an instance in the map which purpose is calling the user options.<br>
 * This class represents an instance in one of the values of the map managing all the commands<br>
 */
public class LossCommand implements Command {
    private final Clean clean;
    private final Scanner scanner = new Scanner(System.in);

    public LossCommand(Clean clean) {
        this.clean = clean;
    }

    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException {
        clean.checkLoss();
    }
}
