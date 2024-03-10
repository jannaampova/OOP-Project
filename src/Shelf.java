public class Shelf {
    private final int capacityOnShelf;
private int productNum;

    public Shelf(int productNum) {
        this.productNum = productNum;
        capacityOnShelf = 20;
    }

    public int getCapacityOnShelf() {
        return capacityOnShelf;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }
}
