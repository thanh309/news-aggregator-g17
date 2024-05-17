package what;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public static String dateConverter(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM dd, yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/dd/MM");

        String outputDate = null;
        try {
            Date date = inputFormat.parse(inputDate);
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }

        return outputDate;

    }
//    public static void main(String[] args) {
//        System.out.println(dateConverter("May 12, 2024"));
//    }
}