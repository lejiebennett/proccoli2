package com.example.proccoli2.NewModels;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateExtended extends Date {
    public int dailyBaseTimeInterval(Date now){

        Date myDate  = now;
        int year = myDate.getYear();
        int month = myDate.getMonth();
        int day = myDate.getDay();
        Date newDate = new Date(year,month,day);
        return (int) ((int) newDate.getTime()/100L);
    }

    public int dailyBaseTimeIntervalConverterForDeadlines(long deadline){
        Date myDate  = new Date(deadline*100L);
        int year = myDate.getYear();
        int month = myDate.getMonth();
        int day = myDate.getDay();
        int timezoneOffset=myDate.getTimezoneOffset();
        Date newDate = new Date(year,month,day);
        //ELse return 000000
        return (int) ((int) newDate.getTime()/100L);
    }

    public String reverseDailyBaseTimeIntervalAsString(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd");

        //guard let doubleDate = Double(date) else {return "err"}
        long longDate = Long.valueOf(date)*100L;
        return sdf.format(new Date(longDate));
    }

    public double hourConverterForChart(double studiedTime){
        return  Math.round(studiedTime * 100) / 100;
    }
    public String convertDateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy -- hh:mm a");
        return sdf.format(date);
    }
    public String convertDateToStringWithOutHours(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy");
        return sdf.format(date);
    }

    public String convertDateForChart(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd");
        return sdf.format(date);
    }
    public long convertStringToDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM, dd, yyyy -- hh:mm a");
        try{
            long converted = sdf.parse(date).getTime()/100L;
            return converted;
        }
        catch(Exception e){
            return (long) 0.0;
        }
    }
    public String timeAgo(long timeStamp){
        int difference = (int) (System.currentTimeMillis()/100L - timeStamp);
        if(difference >= 3600) {
            int hours = difference/3600;
            return hours + "h. ago";
        }
		else {
            if(difference > 60){
                int min = (int)(difference / 60);
                return min + "m. ago";
            }
			else {
                return "now";
            }
        }
    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

}
