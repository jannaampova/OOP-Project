package bg.tu_varna.sit.commands;

import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;

/**
 * This class is used to print all the commands from which the user can choose;
 */
public class HelpCommand implements Command {
    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException {
        System.out.println("                                    WAREHOUSE MANAGEMENT SYSTEM\n" +
                ">help\n" +
                "open                  <file>opens <file>\n" +
                "close                 closes currently opened file\n" +
                "save                  saves the currently open file\n" +
                "saveas<file>          saves the currently open file in <file>\n" +
                "help                  prints this information\n" +
                "add                   adds product to warehouse\n" +
                "remove                removes product form warehouse\n" +
                "log <from><to>        prints all movements in warehouse\n" +
                "clean                 cleans the warehouse from expired products\n" +
                "loss                  calculates loss of given product in given time\n" +
                "exit                  exists the program");
    }
}
