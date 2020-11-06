package Comp.WeatherAPI;

import java.time.ZoneId;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class DateUtils {

    public String getDaysAgoInSecond(int daysAgo) {
        ZonedDateTime startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault());
        long fiveDaysInMS = daysAgo * 24 * 60 * 60;
        long todayMillis1 = startOfToday.toEpochSecond();
        return String.valueOf(todayMillis1 - fiveDaysInMS);
    }
}