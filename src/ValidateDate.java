import java.util.Scanner;

public class ValidateDate {
    public String validate(String userInputDate) {
        String[] tempString = userInputDate.split("/");
        String day = tempString[0];
        String month = tempString[1];
        String year = tempString[2];
        Scanner sc = new Scanner(System.in);

        switch (month) {
            case "01":
            case "03":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                while (!(day.compareTo("01") >= 0 && day.compareTo("31") <= 0)) {
                    System.out.println("Not a valid day for the particular month!\n Enter day from 01-31!");
                    day = sc.next();
                }
                break;
            case "04":
            case "06":
            case "09":
            case "11":
                while (!(day.compareTo("01") >= 0 && day.compareTo("30") <= 0)) {
                    System.out.println("Not a valid day for the particular month!\n Enter day from 01-30!");
                    day = sc.next();
                }
                break;
            case "02":
                while (!(day.compareTo("01") >= 0 && day.compareTo("28") <= 0)) {
                    System.out.println("Not a valid day for the particular month!\n Enter day from 01-28!");
                    day = sc.next();
                }
                break;
            default:
                System.out.println("Not a valid month! ");
                while (!(month.compareTo("01") >= 0 && day.compareTo("12") <= 0)) {
                    System.out.println("Not a valid month!\n Enter month from 01-12!");
                    month = sc.next();
                }
                break;


        }

        while (!(year.compareTo("2020") >= 0 && year.compareTo("2024") <= 0)) {
            System.out.println("Not a valid year\n Enter year from 2020-2024!");
            year = sc.next();

        }
        StringBuilder sb = new StringBuilder();
        sb.append(day).append("/").append(month).append("/").append(year);
        return sb.toString();
    }

    public String getDay(String date){
        String[] tempArr=date.split("/");
        return tempArr[0];
    }
    public String getMonth(String date){
        String[] tempArr=date.split("/");
        return tempArr[1];
    }
    public String getYear(String date){
        String[] tempArr=date.split("/");
        return tempArr[2];
    }

}
