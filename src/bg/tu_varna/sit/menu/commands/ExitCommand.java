package bg.tu_varna.sit.menu.commands;

import bg.tu_varna.sit.interfaces.Command;

/**
 *   The  class implements the {@code Command} interface<br>
 *   and provides functionality to exit the program.<br>
 *   This command, when executed, prints a message indicating that the<br>
 *   program is exiting and then terminates the JVM using {@code System.exit(0)}.</p>
 */
public class ExitCommand implements Command {
    @Override
    public void execute(String[] data)  {
        System.out.println(">exit\n" +
                "exiting the program...");
        System.exit(0);
    }

}
