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

    public Location(List<Section> section, int capacity) {
        this.section = section;
        this.capacity = capacity;
    }


}

