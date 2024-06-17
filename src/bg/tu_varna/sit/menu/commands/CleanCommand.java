package bg.tu_varna.sit.menu.commands;

import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.models.functions.Clean;

import java.io.IOException;

public class CleanCommand implements Command {
    private Clean clean;

    public CleanCommand(Clean clean) {
        this.clean = clean;
    }

    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException, IOException {
        clean.clean();
    }
}
