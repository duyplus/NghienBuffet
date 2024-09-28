package buffet.utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author duyplus
 */
public class XDate {

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public static DateFormat df = new SimpleDateFormat("HH:mm");
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

    /*
     * Chuyển đổi từ String sang Date
     */
    public static Date toDate(String date, String... pattern) {
        try {
            if (pattern.length > 0) {
                sdf.applyPattern(pattern[0]);
            }
            if (date == null) {
                return XDate.now();
            }
            return sdf.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
     * Chuyển đổi từ Date sang String
     */
    public static String toString(Date date, String... pattern) {
        if (pattern.length > 0) {
            sdf.applyPattern(pattern[0]);
        }
        if (date == null) {
            date = XDate.now();
        }
        return sdf.format(date);
    }

    /*
     * Lấy thời gian hiện tại
     */
    public static Date now() {
        return new Date(); //new Date lấy giờ hiện tại
    }

    /*
     * Chuyển đổi từ String sang Time
     */
    public static Time toTime(String txt) {
        LocalTime time = LocalTime.parse(txt, dtf);
        return time == null ? null : Time.valueOf(time);
    }

    /*
     * Chuyển đổi từ Time sang String
     */
    public static String toStringTime(Time time) {
        LocalDateTime now = LocalDateTime.now();
        if (time == null) {
            time = Time.valueOf(dtf.format(now));
        }
        return df.format(time);
    }
}
