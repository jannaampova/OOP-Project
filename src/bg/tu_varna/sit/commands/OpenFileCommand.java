package bg.tu_varna.sit.commands;

import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.models.Product;
import bg.tu_varna.sit.warehouse.Warehouse;
import bg.tu_varna.sit.exeptions.EmptyStringException;
import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements the Command interface and provides functionality<br>
 * to open a file containing product information and populate the warehouse with those products or create a new one.<br>
 */
public class OpenFileCommand implements Command {

    private final Warehouse warehouse;

    /**
     * Constructor that takes a reference to the warehouse object.<br>
     *
     * @param warehouse the warehouse instance to interact with
     */
    public OpenFileCommand(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * Executes the command to open a file and add products to the warehouse.<br>
     * If something goes wrong it notifies the user.<br>
     *
     * @param data an array of Strings containing command arguments. The first element should be the command name,<br>
     *             and the second element should be the file path.<br><br>
     * @throws IllegalArgumentException if the file path is not provided<br>
     * @throws IOException              if there's an error reading the file<br>
     * @throws NegativeNumberException  if a product quantity is negative<br>
     * @throws LocationException        if a product location is invalid<br>
     */
    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException, IOException {
        if (data.length < 2) {
            throw new IllegalArgumentException("File path not provided.");
        }

        File file = new File(data[1]);
        if (!file.exists()) {
            if (file.createNewFile()) {
                System.out.println(">open\nFile created successfully: " + data[1]);
            } else {
                System.out.println("Failed to create the file: " + data[1]);
            }
            return;
        }

        try (FileReader fileReader = new FileReader(file);
             Scanner scanner = new Scanner(fileReader)) {
            System.out.println(">open\nFile opened successfully: " + data[1]);
            warehouse.setCurrentFilePath(data[1]);
            String line = scanner.nextLine();

            while (scanner.hasNext()) {
                line = scanner.nextLine();
                //System.out.println(line); // Output the content of each line

                String[] oneProduct = line.split(",");
                try {
                    Product product = new Product(oneProduct[0], oneProduct[1], oneProduct[2], oneProduct[3], oneProduct[4], Double.parseDouble(oneProduct[5]), oneProduct[6]);
                    warehouse.add(product);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity format for product: " + line);
                } catch (NegativeNumberException | LocationException e) {
                    System.out.println("Custom exception encountered: " + e.getMessage());
                } catch (EmptyStringException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.out.println("File did not open successfully: " + e.getMessage());
        }
    }
}
