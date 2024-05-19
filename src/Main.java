
import bg.tu_varna.sit.commands.*;
import bg.tu_varna.sit.models.*;

import bg.tu_varna.sit.exeptions.EmptyStringException;
import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.ManageHistory;
import bg.tu_varna.sit.menageHistory.AddedProductHistory;
import bg.tu_varna.sit.menageHistory.ShowChanges;
import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.menu.MenuCommands;
import bg.tu_varna.sit.validateDate.ValidateDate;
import bg.tu_varna.sit.warehouse.Warehouse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws LocationException, EmptyStringException, NegativeNumberException, IOException {
        Scanner sc = new Scanner(System.in);
        Warehouse warehouse = Warehouse.getInstance();
        ValidateDate validateDate = new ValidateDate();
        Map<MenuCommands, Command> menu = new HashMap<>();
        AddedProductHistory addedProductHistory = new AddedProductHistory();
        ManageHistory manageHistory = new AddedProductHistory();
        Command helpCommand = new HelpCommand();
        helpCommand.execute(null);
        menuOptions(menu, manageHistory, validateDate, warehouse);
        while (true) {
            System.out.println("Enter command:");
            String s = sc.nextLine().trim();
            if (s.isEmpty()) {
                System.out.println("Command Error, try again");
                continue;
            }

            String[] userInput = s.split("\\s+");
            userInput[0] = userInput[0].toUpperCase();

            try {
                MenuCommands command = MenuCommands.valueOf(userInput[0]);


                if (menu.containsKey(command)) {
                    menu.get(command).execute(userInput);
                    System.out.println("Enter new option:");
                } else {
                    System.out.println("Command Error, try again");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Command Error, try again");
            }
        }

    }

    /**
     * Map containing all possible options from which the user can choose<br>
     *
     * @param menu          the map of enum as key and interface command as value <br>
     * @param manageHistory instance of menageHistory interface<br>
     * @param validateDate  instance of validateDate class <br>
     * @param warehouse     instance of Warehouse class(the one and only instance)<br>
     */
    private static void menuOptions(Map<MenuCommands, Command> menu, ManageHistory manageHistory, ValidateDate validateDate, Warehouse warehouse) {
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
}