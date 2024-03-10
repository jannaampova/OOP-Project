// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Product product1=new DiaryProducts.ProductBuilder("MILK","15/05/2024","15/01/2024","ВЕРЕЯ","КГ",15).build();
        Product product2=new DiaryProducts.ProductBuilder("MILK","10/05/2024","15/01/2024","ВЕРЕЯ","КГ",16).build();

        Warehouse warehouse=new Warehouse();
        warehouse.add(product1);
        warehouse.add(product2);
        warehouse.printAllWithTheSameName(product1);
    }
}