public interface ManageHistory {
    void addNewChange(String date,String name,double quantity);
    void getHistoryInfo(String fromDate,String toDate);
}
