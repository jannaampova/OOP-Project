public abstract class Product {

        private String name;
        private String expiryDate;
        private String arrivalDate;
        private String manufacturer;
        private String unity;
        private int quantity;
        private Location location;
        private String comment;

        public String getName() {
            return name;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public String getArrivalDate() {
            return arrivalDate;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public String getUnity() {
            return unity;
        }

        public int getQuantity() {
            return quantity;
        }

        public Location getLocation() {
            return location;
        }

        public String getComment() {
            return comment;
        }

        public void setName(String name) {
            this.name = name;
        }


        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public Product(String name, String expiryDate, String arrivalDate, String manufacturer, String unity, int quantity, Location location, String comment) {
            this.name = name;
            this.expiryDate = expiryDate;
            this.arrivalDate = arrivalDate;
            this.manufacturer = manufacturer;
            this.unity = unity;
            this.quantity = quantity;
            this.location = location;
            this.comment = comment;
        }
    }


