package bg.tu_varna.sit.menu.commands;

import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.models.functions.Remove;

import java.io.IOException;

public class RemoveCommand implements Command {
    private Remove remove;

    public RemoveCommand(Remove remove) {
        this.remove = remove;

    }

    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException, IOException {
        remove.warehouseFunction();
    }
}
