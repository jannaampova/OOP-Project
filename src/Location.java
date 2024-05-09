import exeptions.LocationException;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Location {

    private String sector;
    public static Map<String,Integer> shelf = new HashMap<>();//kolko rafta ima
    private int realShelf;
    private double quantity;
    private final int shelfCapacity;//kolko mozhe an raft produkti
    private final int sectorCapacity;//kolko rafta mozhe da ia v section

    public Location(String sector, double quantity) throws LocationException {
        this.sector = sector;
        this.shelfCapacity = 250;
        this.sectorCapacity = 20;
        shelf.putIfAbsent(sector,0);
        shelf.put(sector,shelf.get(sector)+1);
        if(shelf.get(sector)>this.getSectorCapacity()){
            throw new LocationException("No space n section!");
        }
        this.realShelf=shelf.get(sector);
        setQuantity(quantity);

    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double number) throws LocationException {
        double availableSpace=shelfCapacity-getQuantity();
        if(number>shelfCapacity)throw new LocationException("No capacity on shelf,You can add "+availableSpace);
        this.quantity = number;
    }

    public int getShelfCapacity() {
        return shelfCapacity;
    }

    public int getSectorCapacity() {
        return sectorCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return shelf == location.shelf && quantity == location.quantity && shelfCapacity == location.shelfCapacity && sectorCapacity == location.sectorCapacity && Objects.equals(sector, location.sector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sector, shelf, quantity, shelfCapacity, sectorCapacity);
    }

    @Override
    public String toString() {
        return "Location{" +
                "sector='" + sector + '\'' +
                ", shelf=" + shelf +
                ", quantity=" + quantity +
                ", shelfCapacity=" + shelfCapacity +
                ", sectorCapacity=" + sectorCapacity +
                '}';
    }
}


