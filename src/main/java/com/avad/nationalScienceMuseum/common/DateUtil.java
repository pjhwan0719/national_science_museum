package com.avad.nationalScienceMuseum.common;

import org.springframework.stereotype.Component;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtil {

    /**
     * PJH
     * - 오늘 날짜 + 시간
     * @return String
     */
    public String getFullCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(Calendar.getInstance().getTime());
    }

    /**
     * PJH
     * - 오늘날짜 + 시간
     * @return long
     */
    public long getFullCurrentDateLong() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDay = sdf.parse(sdf.format(Calendar.getInstance().getTime()), new ParsePosition(0));
        return currentDay.getTime();
    }

    /**
     * PJH
     * - 오늘 날짜
     * @return String
     */
    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(Calendar.getInstance().getTime());
    }
}
