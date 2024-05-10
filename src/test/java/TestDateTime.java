import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TestDateTime {
    public static void main(String[] args) {
        String myDate = "2014/10/29 00:00:0098hh0";
        LocalDateTime localDateTime = LocalDateTime.parse(myDate,
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss") );
/*
  With this new Date/Time API, when using a date, you need to
  specify the Zone where the date/time will be used. For your case,
  seems that you want/need to use the default zone of your system.
  Check which zone you need to use for specific behaviour e.g.
  CET or America/Lima
*/
        long millis = localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
        System.out.println(millis);
    }
}
