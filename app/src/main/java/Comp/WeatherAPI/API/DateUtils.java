package Comp.WeatherAPI.API;

import java.time.ZoneId;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtils {

    public String getDaysAgoInSecond(int daysAgo) {
        ZonedDateTime startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault());
        long fiveDaysInMS = daysAgo * 24 * 60 * 60;
        long todayMillis1 = startOfToday.toEpochSecond();
        return String.valueOf(todayMillis1 - fiveDaysInMS);
    }

    public String getDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd_hh:mm:ss");
        String strDate = dateFormat.format(date);
        return strDate;
    }
}