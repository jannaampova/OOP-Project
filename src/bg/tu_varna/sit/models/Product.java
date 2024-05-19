package bg.tu_varna.sit.models;

import bg.tu_varna.sit.validateDate.ValidateDate;
import bg.tu_varna.sit.exeptions.EmptyStringException;
import bg.tu_varna.sit.exeptions.NegativeNumberException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

/**
 * The Product class represents a product in a warehouse, with attributes such as name, expiry date,<br>
 * arrival date, manufacturer, unit, quantity, location, and comment. It implements the Comparable interface<br>
 * to allow sorting based on the expiry date.
 */
public class Product implements Comparable<Product> {

    private String name;
    private String expiryDate;
    private String arrivalDate;
    private String manufacturer;
    private String unity;
    private double quantity;
    private Location location;
    private String comment;

    public Product() {

    }

    public Product(String name, String expiryDate, String arrivalDate, String manufacturer, String unity, double quantity, String comment) throws EmptyStringException, NegativeNumberException {
        setName(name);
        setExpiryDate(expiryDate);
        setArrivalDate(arrivalDate);
        setManufacturer(manufacturer);
        setUnity(unity);
        setQuantity(quantity);
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws EmptyStringException {
        if (name.isEmpty()) {
            throw new EmptyStringException("The name CAN NOT be empty, please enter the name!");
        } else {
            this.name = name;
        }
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ValidateDate date = new ValidateDate();
        this.expiryDate = date.validate(expiryDate);

    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        ValidateDate date = new ValidateDate();
        this.arrivalDate = date.validate(arrivalDate);
    }

    public String getManufacturer() {

        return manufacturer;
    }

    public void setManufacturer(String manufacturer) throws EmptyStringException {

        if (manufacturer.isEmpty()) {
            throw new EmptyStringException("Please enter the manufacturers name!");
        } else {
            this.manufacturer = manufacturer;
        }
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) throws EmptyStringException {
        if (unity.isEmpty()) {
            throw new EmptyStringException("Please enter the unity of the product!");
        } else {
            this.unity = unity;
        }
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) throws NegativeNumberException {
        if (quantity < 0) {
            throw new NegativeNumberException("Quantity can not be negative number!");
        }
        this.quantity = quantity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return quantity == product.quantity && Objects.equals(name, product.name) && Objects.equals(expiryDate, product.expiryDate) && Objects.equals(arrivalDate, product.arrivalDate) && Objects.equals(manufacturer, product.manufacturer) && Objects.equals(unity, product.unity) && Objects.equals(location, product.location) && Objects.equals(comment, product.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, expiryDate, arrivalDate, manufacturer, unity, quantity, location, comment);
    }
    /**
     * Prompts the user to enter product details from the console and sets the product attributes accordingly.
     */
    public void setProductDetailsFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter product details:");
        System.out.print("Name: ");
        try {
            setName(scanner.nextLine().toUpperCase());
            System.out.print("Expiry Date (dd/mm/yyyy): ");
            setExpiryDate(scanner.nextLine());
            System.out.print("Arrival Date (dd/mm/yyyy): ");
            String s = scanner.nextLine();
            s = getArrivalDate(s, scanner);
            setArrivalDate(s);
            System.out.print("Manufacturer: ");
            setManufacturer(scanner.nextLine());
            System.out.print("Unity: ");
            setUnity(scanner.nextLine());
            System.out.print("Quantity: ");
            setQuantity(Double.parseDouble(scanner.nextLine()));
            System.out.print("Comment: ");
            setComment(scanner.nextLine());
        } catch (EmptyStringException | NegativeNumberException | NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    /**
     * Validates and returns the arrival date ensuring it is not after the expiry date.
     *
     * @param s       The arrival date as a string.
     * @param scanner The scanner to read input if re-entry is needed.
     * @return The validated arrival date.
     */
    private String getArrivalDate(String s, Scanner scanner) {
        ValidateDate date = new ValidateDate();
        LocalDate expiryDateCheck = date.parseDate(getExpiryDate());
        LocalDate arrivalDateCheck = date.parseDate(s);
        while (expiryDateCheck.isBefore(arrivalDateCheck)) {
            System.out.println("The arrival date can not be before the expiry date Enter date again: ");
            s = scanner.nextLine();
            arrivalDateCheck = date.parseDate(s);
        }
        return s;
    }

    /**
     * Compares this product to another product based on their expiry dates.
     * I am using this method to sort the products according the dates when removing them
     * @param o The other product to compare with.
     * @return 1 if this product's expiry date is before the other product's expiry date,
     *         0 if they are equal,
     *        -1 if this product's expiry date is after the other
     */
    @Override
    public int compareTo(Product o) {
        ValidateDate date = new ValidateDate();
        LocalDate localDate = date.parseDate(this.getExpiryDate());
        LocalDate localDateO = date.parseDate(o.getExpiryDate());
        if (localDate.isBefore(localDateO)) return 1;
        else if (localDate.isEqual(localDateO)) return 0;
        return -2;
    }
}


