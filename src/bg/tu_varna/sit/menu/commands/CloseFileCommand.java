package bg.tu_varna.sit.menu.commands;

import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.models.warehouse.Warehouse;

/**
 * This class represents a command to close the currently opened file as it sets the path to null
 */
public class CloseFileCommand implements Command {
    /**
     * Executes the close command. If no file is currently opened, an error message is displayed.<br>
     *
     * @param args the arguments provided to the command, not used in this command.<br>
     */
    private final Warehouse warehouse;

    public CloseFileCommand(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * Executes the close command, closing the currently opened file.<br>
     * If no file is currently opened, an error message is displayed.<br>
     *
     * @param data the arguments provided to the command, not used in this command.<br>
     * @throws NegativeNumberException if a negative number is encountered.<br>
     * @throws LocationException       if an error related to location occurs.<br>
     */
    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException {
        String currentFilePath = warehouse.getCurrentFilePath(); // Get the current file path
        if (currentFilePath == null) {
            System.out.println("Error: no file opened!");
        }

        warehouse.setCurrentFilePath(null);
        System.out.println("File closed successfully: " + currentFilePath);

    }
}

