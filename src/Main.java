import bg.tu_varna.sit.exeptions.EmptyStringException;
import bg.tu_varna.sit.exeptions.InvalidCommandException;
import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.menu.Menu;

import java.io.IOException;

/**
 * The Main class serves as the entry point for the warehouse management application.
 * It initializes the {@code Menu} class and starts processing user commands.
 */
public class Main {
    public static void main(String[] args) throws LocationException, EmptyStringException, NegativeNumberException, IOException, InvalidCommandException {
        Menu menuHandler = new Menu();
        menuHandler.processCommands();
    }
}