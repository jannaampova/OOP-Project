package bg.tu_varna.sit.commands;

import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.warehouse.Warehouse;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The purpose of this class is to save the current data of the warehouse .
 */
public class SaveCommand implements Command {
    private final Warehouse warehouse;


    public SaveCommand(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * First it checks if a file path for the warehouse is set(with the openFileCommand)
     * if yes writes data into file using the help of method toFile()
     * else gives a message to the user that there is no file opened or there is error wile saving
     */
    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException {
        if (warehouse.getCurrentFilePath() == null) {
            System.out.println("No file currently opened");
            return;
        }
        try(FileWriter writer=new FileWriter(warehouse.getCurrentFilePath())) {
            writer.write(warehouse.toFile());
            System.out.println("File saved successfully to current opened file"+warehouse.getCurrentFilePath());
        } catch (IOException e) {
            System.out.println("Failed to save in file");
            throw new RuntimeException(e);
        }

    }
}
