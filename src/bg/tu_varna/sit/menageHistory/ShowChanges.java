package bg.tu_varna.sit.menageHistory;

import bg.tu_varna.sit.warehouse.Warehouse;
import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class is used for executing the log command and it is calling a method that shows the history for removed and added products in given date range.
 * The command interface is implemented for the use of its instance in the map for the options from which the user can choose
 */
public class ShowChanges implements Command {
    private Warehouse warehouse;
    private Scanner scanner;

    public ShowChanges(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException, IOException {
        warehouse.showHistory(data[1],data[2]);
    }
}
