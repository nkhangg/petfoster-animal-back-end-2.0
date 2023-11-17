package com.poly.petfoster.ultils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class FormatUtils {
    
    public String dateToString(Date date) {
        
        SimpleDateFormat dateString = null;
        try {
            dateString = new SimpleDateFormat("MMM d, yyyy");
        } catch (Exception e) {
            e.printStackTrace();
        }

       return dateString.format(date);
    }

    public Date convertMilisecondsToDate(String miliString) throws NumberFormatException {
        Long miliseconds = Long.parseLong(miliString);
        return new Date(miliseconds);
    }

    public Date dateToDateFormat(Date date, String pattern) {

        SimpleDateFormat format = new SimpleDateFormat(pattern);

        Date formattedDate = null;
        try {
            formattedDate = format.parse(format.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        return formattedDate;
    }

}
