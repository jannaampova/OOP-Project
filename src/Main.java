// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Product product1=new DiaryProducts("MILK","15/05/2024","15/01/2024","ВЕРЕЯ","КГ",15,"Older");
        Product product2=new DiaryProducts("MILK","10/05/2024","15/01/2024","ВЕРЕЯ","КГ",16, "Newer");
        Product product3=new DiaryProducts("MILK","10/05/2024","20/01/2024","ВЕРЕЯ","КГ",3, "Newer");

        Warehouse warehouse=new Warehouse();
        warehouse.add(product1);
        warehouse.add(product2);
        warehouse.add(product3);
        System.out.println();

        warehouse.printAllWithTheSameName(product1);
    }
}