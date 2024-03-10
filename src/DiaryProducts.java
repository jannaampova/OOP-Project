public class DiaryProducts extends Product{
    private DiaryProducts(ProductBuilder productBuilder) {
        super(productBuilder.name, productBuilder.expiryDate, productBuilder.arrivalDate, productBuilder.manufacturer, productBuilder.unity, productBuilder.quantity, productBuilder.location,productBuilder.comment);
    }

    public static class ProductBuilder{
        private String name;
        private String expiryDate;
        private String arrivalDate;
        private String manufacturer;
        private String unity;
        private int quantity;
        private Location location;
        private String comment;

        public ProductBuilder(String name, String expiryDate, String arrivalDate, String manufacturer, String unity, int quantity) {
            this.name = name;
            this.expiryDate = expiryDate;
            this.arrivalDate = arrivalDate;
            this.manufacturer = manufacturer;
            this.unity = unity;
            this.quantity = quantity;
        }
        public ProductBuilder withLocation(Location location){
            this.location=location;
            return this;
        }
        public ProductBuilder withComment(String comment){
            this.comment=comment;
            return this;
        }
        public Product  build(){
            return new DiaryProducts(this) ;
        }
    }
}
