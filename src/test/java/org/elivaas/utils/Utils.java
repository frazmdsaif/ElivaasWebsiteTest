package org.elivaas.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String  generateCurrentDateAndTime(){
        return new SimpleDateFormat("ddMMyyyyHHmm").format(new Date());
    }
}
