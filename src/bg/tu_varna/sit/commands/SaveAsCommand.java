package bg.tu_varna.sit.commands;

import bg.tu_varna.sit.warehouse.Warehouse;
import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The SaveAsCommand class implements the Command interface and provides functionality<br>
 * to save the state of a Warehouse object to a specified file.
 */
public class SaveAsCommand implements Command {
    private Warehouse warehouse;

    public SaveAsCommand(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * This method purpose is to save data from the warehouse to specific file<br>
     * First it checks if the user input consists of a second parameter representing the file name<br>
     * If this condition is satisfied it checks if the file exists if not it notifies the use, if yes<br>
     * with fileWriter it saves the data into that particular file with the hel of toFile method that formats<br>
     * the data in a way readable for file<br>
     *
     * @param data An array of strings where the second element is the file path to save the warehouse state.<br>
     * @throws NegativeNumberException  If a negative number is encountered during the execution.<br>
     * @throws LocationException        If there is an issue with the location data during the execution.<br>
     * @throws IllegalArgumentException If the file path is not provided in the data array.<br>
     */
    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException {
        if (data.length < 2) {
            throw new IllegalArgumentException("File path not provided.");
        }
        File file = new File(data[1]);
        if (!file.exists()) {
            System.out.println("No such file!");
        }
        warehouse.setCurrentFilePath(data[1]);
        try (FileWriter writer = new FileWriter(warehouse.getCurrentFilePath())) {
            writer.write(warehouse.toFile());
            System.out.println("File saved successfully to current opened file: " + warehouse.getCurrentFilePath());
        } catch (IOException e) {
            System.out.println("Failed to save in file");
            throw new RuntimeException(e);
        }
    }
}
