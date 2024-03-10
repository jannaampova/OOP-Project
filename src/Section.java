
import java.util.ArrayList;
import java.util.List;

public class Section {

        private int number;
        private List<Shelf> shelves=new ArrayList<>();
        private int shelfCapacityInSection;


        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }




        public List<Shelf> getShelves() {
            return shelves;
        }


        public int getShelfCapacityInSection() {
            return shelfCapacityInSection;
        }

        public void setShelfCapacityInSection(int shelfCapacity) {
            this.shelfCapacityInSection = shelfCapacity;
        }

        public Section(int number, int shelfCapacityInSection) {
            this.number = number;
            this.shelfCapacityInSection = shelfCapacityInSection;

        }
        public void addShelf(Shelf shelfNum){
            if(shelves.size()<=shelfCapacityInSection){
            if(shelfNum.getCapacityOnShelf()>shelfNum.getProductNum()){
                shelves.add(shelfNum);
            }
            else{
                System.out.println("Няма достатъчно място на този  рафт!");
            }

            }
            else {
                System.out.println("Няма достатъчно място за нов рафт!");
            }
        }
    }


