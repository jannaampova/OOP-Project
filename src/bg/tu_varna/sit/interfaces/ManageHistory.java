package bg.tu_varna.sit.interfaces;

import java.io.IOException;

/**
 * The ManageHistory interface defines methods for managing historical changes in the warehouse  system,
 * getting the information out of them as well as writing them into file
 * Classes AddedProductHistory and RemoveProductHistory are implementing this interface with the help of getHistory() method
 * all changes over the warehouse in given period of time are displayed to the user
 */

public interface ManageHistory {
    void addNewChange(String date,String name,double quantity) throws IOException;
    void getHistoryInfo(String fromDate,String toDate) throws IOException;
     void writeIntoFile() throws IOException;
}
