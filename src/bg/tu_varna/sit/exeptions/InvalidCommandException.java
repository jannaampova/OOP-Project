package bg.tu_varna.sit.exeptions;

public class InvalidCommandException extends Exception{
    public InvalidCommandException(String message) {
        super(message);
    }
}
