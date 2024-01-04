package org.brain.jobscrapping.utils;

import org.springframework.stereotype.Component;

@Component
public class DateConverter {
    public long dateToUnixTimestamp(String date) {
        String[] dateParts = date.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        return (long) (year * 3.154e+10 + month * 2.628e+9 + day * 8.64e+7);
    }
}
