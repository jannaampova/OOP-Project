package bg.tu_varna.sit.menu;

import bg.tu_varna.sit.commands.*;
import bg.tu_varna.sit.exeptions.EmptyStringException;
import bg.tu_varna.sit.exeptions.InvalidCommandException;
import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.interfaces.ManageHistory;
import bg.tu_varna.sit.menageHistory.ProductHistory;
import bg.tu_varna.sit.menageHistory.ShowChanges;
import bg.tu_varna.sit.menu.MenuCommands;
import bg.tu_varna.sit.models.*;
import bg.tu_varna.sit.validateDate.ValidateDate;
import bg.tu_varna.sit.warehouse.Warehouse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The  Menu class is responsible for managing the user interface of the warehouse management system.
 * It initializes the menu options, processes user commands, and executes the corresponding actions.
 */
public class Menu {
    private final Map<MenuCommands, Command> menu = new HashMap<>();
    private final Scanner sc = new Scanner(System.in);
    private final Warehouse warehouse;
    private final ValidateDate validateDate;
    private final ManageHistory manageHistory;

    public Menu() {
        this.warehouse = Warehouse.getInstance();
        this.validateDate = new ValidateDate();
        this.manageHistory = new ProductHistory();
        initializeMenu();
    }

    /**
     * Initializes the menu options by mapping  MenuCommands to their corresponding Command implementations.
     * This method is called in the constructor to set up the available commands in the menu.
     */
    private void initializeMenu() {
        menu.put(MenuCommands.ADD, new AddProduct(manageHistory, warehouse));
        menu.put(MenuCommands.REMOVE, new Remove(manageHistory, warehouse));
        menu.put(MenuCommands.CLEAN, new Clean(warehouse));
        menu.put(MenuCommands.LOG, new ShowChanges(warehouse));
        menu.put(MenuCommands.HELP, new HelpCommand());
        menu.put(MenuCommands.LOSS, new LossImpl(warehouse));
        menu.put(MenuCommands.CLOSE, new CloseFileCommand(warehouse));
        menu.put(MenuCommands.OPEN, new OpenFileCommand(warehouse));
        menu.put(MenuCommands.SAVE, new SaveCommand(warehouse));
        menu.put(MenuCommands.SAVEAS, new SaveAsCommand(warehouse));
        menu.put(MenuCommands.EXIT, new ExitCommand());
        menu.put(MenuCommands.PRINT, new PrintData(warehouse));
    }

    /**
     * Processes user commands in a loop. It prompts the user for input, validates the command,
     * and executes the corresponding action. If the command is invalid, it prints an error message
     * and prompts the user to try again.
     */
    public void processCommands() throws LocationException, EmptyStringException, NegativeNumberException, IOException, InvalidCommandException {
        Command helpCommand = new HelpCommand();
        helpCommand.execute(null);

        while (true) {
            System.out.println("Enter command:");
            String s = sc.nextLine().trim();

            try {
                if (s.isEmpty()) {
                    throw new InvalidCommandException("Command can not be empty!");

                }
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
            }

            String[] userInput = s.split("\\s+");
            userInput[0] = userInput[0].toUpperCase();

            try {
                MenuCommands command = MenuCommands.valueOf(userInput[0]);

                if (menu.containsKey(command)) {
                    menu.get(command).execute(userInput);
                    System.out.println("Enter new option:");
                } else {
                    throw new InvalidCommandException("Command Error, try again");
                }
            } catch (NegativeNumberException | IllegalArgumentException | InvalidCommandException e) {
                System.out.println("Command Error, try again");
            }
        }
    }
}

