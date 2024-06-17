package bg.tu_varna.sit.menu.commands;

import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.menageHistory.ProductHistory;

import java.io.IOException;

/**
 * This class is used for executing the log command<br>
 * It is calling a method that shows the history for removed and added products in given date range.<br>
 * The command interface is implemented for the use of its instance in the map for the options from which the user can choose<br>
 */
public class LogHistoryCommand implements Command {
private ProductHistory productHistory;
    public LogHistoryCommand(ProductHistory productHistory) {
        this.productHistory = productHistory;
    }

    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException, IOException {
        if ( data.length < 3 || data[1].equals("\n")) {
            throw  new NegativeNumberException("Invalid parameters for this command");
        }
      productHistory.getHistoryInfo(data[1], data[2]);
    }
}
