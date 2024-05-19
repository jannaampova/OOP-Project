package bg.tu_varna.sit.models;

import bg.tu_varna.sit.exeptions.LocationException;

import java.util.*;

/**
 *  The Location class represents a location in a warehouse, defined by a sector and a shelf.<br>
 *  It manages the capacity of products on each shelf and the number of shelves in each sector.<br>
 *  The map represents every sector and its shelf occupation
 *
 */
public class Location {

    private String sector;
    public static Map<String,Integer> shelf = new HashMap<>();
    private int realShelf;
    private double quantity;
    private final int shelfCapacity;//how many products can one shelf have
    private final int sectorCapacity;//how many shelves can one sector has

    public Location(String sector, double quantity) throws LocationException {
        this.sector = sector;
        this.shelfCapacity = 250;
        this.sectorCapacity = 5;
        shelf.putIfAbsent(sector,0);
        shelf.put(sector,shelf.get(sector)+1);
        if(shelf.get(sector)>this.getSectorCapacity()){
            throw new LocationException("No space in section!");
        }
        this.realShelf=shelf.get(sector);
        setQuantity(quantity);

    }


    public int getRealShelf() {
        return realShelf;
    }

    public  Map<String, Integer> getShelf() {
        return shelf;
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

    public void setQuantity(double number) {
        double availableSpace=shelfCapacity-getQuantity();
        if(number>shelfCapacity){
            System.out.println(("No capacity on shelf,You can add " + availableSpace));
            return;
        }
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
        return "Sector: "+getSector()+" Shelf: "+getRealShelf()+" Shelf capacity: "+shelfCapacity;
    }
}


