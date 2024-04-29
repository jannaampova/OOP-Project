import java.util.Objects;

public  class Product {

        private String name;
        private String expiryDate;
        private String arrivalDate;
        private String manufacturer;
        private String unity;
        private int quantity;
        private Location location;
        private String comment;

    public Product(String name, String expiryDate, String arrivalDate, String manufacturer, String unity, int quantity, String comment) {
        this.name = name;
        setExpiryDate(expiryDate);
        setArrivalDate(arrivalDate);
        this.manufacturer = manufacturer;
        this.unity = unity;
        this.quantity = quantity;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ValidateDate date=new ValidateDate();
        this.expiryDate =date.validate(expiryDate); ;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        ValidateDate date=new ValidateDate();
        this.arrivalDate = date.validate(arrivalDate);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", unity='" + unity + '\'' +
                ", quantity=" + quantity +
                ", location=" + location.toString() +
                ", comment='" + comment + '\'' +
                '}';
    }
}


