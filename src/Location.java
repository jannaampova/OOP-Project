import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Location {

        private List<Section> section=new ArrayList<>();
        private int capacity;

    public List<Section> getSection() {
        return section;
    }

    public void setSection(List<Section> section) {
        this.section = section;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Location() {
        this.section =new ArrayList<>();
        this.capacity =15;//sekcii v warehouse
    }
    public  void addSection(Section section1){
        if(section.size()<capacity){
            if(section.get(section.size()-1).getShelfCapacityInSection()<=section.get(section.size()-1).getShelves().size()) {
                section.add(section1);
            }
        }

    }


}

