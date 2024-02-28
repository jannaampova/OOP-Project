
import java.util.ArrayList;
import java.util.List;

public class Section {

        private int number;
        private List<Integer> shelves=new ArrayList<>();
        private int shelfCapacity;
        private int capacity;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }


        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public List<Integer> getShelves() {
            return shelves;
        }


        public int getShelfCapacity() {
            return shelfCapacity;
        }

        public void setShelfCapacity(int shelfCapacity) {
            this.shelfCapacity = shelfCapacity;
        }

        public Section(int number, int shelfCapacity, int capacity) {
            this.number = number;
            this.shelfCapacity = shelfCapacity;
            this.capacity = capacity;
        }
        public void addShelf(Integer shelfNum){
            if(shelves.size()<=shelfCapacity){
                shelves.add(shelfNum);}else {
                System.out.println("Няма достатъчно място за нов рафт!");
            }
        }
    }


