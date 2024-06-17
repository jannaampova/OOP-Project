package bg.tu_varna.sit.menu.commands;

import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.models.functions.PrintData;

import java.io.IOException;

public class PrintCommand implements Command {
    private PrintData printData;

    public PrintCommand(PrintData printData) {
        this.printData = printData;
    }

    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException, IOException {
        printData.warehouseFunction();
    }
}
