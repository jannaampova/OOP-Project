package bg.tu_varna.sit.interfaces;

import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;

import java.io.IOException;
/**
 * This interface defines a contract for classes representing commands in the application.
 * Classes implementing this interface should provide an `execute` method that encapsulates the logic
 * for a specific operation.
 */
public interface Command {
    public void execute(String[] data) throws NegativeNumberException, LocationException, IOException;

}
