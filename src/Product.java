public abstract class Product {

        private String name;
        private String expiryDate;
        private String arrivalDate;
        private String manufacturer;
        private String unity;
        private int quantity;
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


        public void setComment(String comment) {
            this.comment = comment;
        }

    protected Product(String name, String expiryDate, String arrivalDate, String manufacturer, String unity, int quantity, String comment) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.arrivalDate = arrivalDate;
        this.manufacturer = manufacturer;
        this.unity = unity;
        this.quantity = quantity;
        this.comment = comment;
    }
}


