package bg.tu_varna.sit.menu.commands;

import bg.tu_varna.sit.exeptions.LocationException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;
import bg.tu_varna.sit.interfaces.Command;
import bg.tu_varna.sit.models.functions.Add;

import java.io.IOException;

public class AddProductCommand implements Command {
    private Add addProduct;
    public AddProductCommand( Add addProduct) {
        this.addProduct = addProduct;
    }

    @Override
    public void execute(String[] data) throws NegativeNumberException, LocationException, IOException {
        addProduct.warehouseFunction();
    }
}
