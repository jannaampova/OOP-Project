package bg.tu_varna.sit.validateDate;

import java.time.LocalDate;
import java.util.Scanner;

public class ValidateDate {
    /**
     * Validates the accuracy of a given date based on the current month and the days each month can have.
     * This method will repeatedly prompt the user to correct the date if any component (day, month, year) is invalid.
     *
     * @param userInputDate The input date as a String in the format "dd/mm/yyyy".
     * @return A validated date String in the format "dd/mm/yyyy".
     */
    public String validate(String userInputDate) {
        String[] tempString = userInputDate.split("/");
        String day = tempString[0];
        String month = tempString[1];
        String year = tempString[2];
        Scanner sc = new Scanner(System.in);

        switch (month) {
            case "1":
            case "3":
            case "5":
            case "7":
            case "8":
            case "10":
            case "12":
                while (!(day.compareTo("1") >= 0 && day.compareTo("31") <= 0)) {
                    System.out.println("Not a valid day for the particular month!\n Enter day from 1-31!");
                    day = sc.next();
                }
                break;
            case "4":
            case "6":
            case "9":
            case "11":
                while (!(day.compareTo("1") >= 0 && day.compareTo("30") <= 0)) {
                    System.out.println("Not a valid day for the particular month!\n Enter day from 1-30!");
                    day = sc.next();
                }
                break;
            case "2":
                while (!(day.compareTo("1") >= 0 && day.compareTo("28") <= 0)) {
                    System.out.println("Not a valid day for the particular month!\n Enter day from 1-28!");
                    day = sc.next();
                }
                break;
            default:
                System.out.println("Not a valid month! ");
                while (!(month.compareTo("1") >= 0 && month.compareTo("12") <= 0)) {
                    System.out.println("Not a valid month!\n Enter month from 1-12!");
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

    /**
     * These three methods simply take a given string and split it based on a delimiter
     * and return the actual day/month/year
     * @param date given string as date
     */
    public String getDay(String date) {
        String[] tempArr = date.split("/");
        return tempArr[0];
    }

    public String getMonth(String date) {
        String[] tempArr = date.split("/");
        return tempArr[1];
    }

    public String getYear(String date) {
        String[] tempArr = date.split("/");
        return tempArr[2];
    }

    /**
     * This method is used to parse a given string into localDate<br>
     * getYear getDay getMonth are other methods that are simply splithing the given string on given delimeter<br>
     * after getting the actual day month and year i pass them(parsed to int ) to the localDate.of() function and parse the into LocalDate<br>
     * I created this method to help me have more readable code because there were to many times that i needed to parse some string to local date<br>
     *
     * @param dateString the given string needed to be parsed<br>
     */
    public LocalDate parseDate(String dateString) {
         String year = getYear(dateString);
        String day = getDay(dateString);
        String month =getMonth(dateString);
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }


}
