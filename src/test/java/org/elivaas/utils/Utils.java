package org.elivaas.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String  generateCurrentDateAndTime(){
        return new SimpleDateFormat("dd-MM-yyyy-HH:mm").format(new Date());
    }
}
