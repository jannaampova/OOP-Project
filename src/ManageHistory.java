public interface ManageHistory {
    void addNewChange(String date,String name,int quantity);
    void getHistoryInfo(String fromDate,String toDate);
}
