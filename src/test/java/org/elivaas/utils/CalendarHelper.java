package org.elivaas.utils;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Helpers for the elivaas.com calendar date picker.
 * The picker exposes each selectable day as a button whose aria-label looks like
 * "Tuesday, March 10th, 2026" (day-of-week, month, day-with-ordinal, year).
 * Dates must be computed dynamically — never hardcode aria-label literals.
 */
public class CalendarHelper {

    /** check-in default: a week from today */
    public static LocalDate defaultCheckIn() {
        return LocalDate.now().plusDays(7);
    }

    /** check-out default: two nights after check-in */
    public static LocalDate defaultCheckOut() {
        return LocalDate.now().plusDays(9);
    }

    public static LocalDate daysFromToday(int days) {
        return LocalDate.now().plusDays(days);
    }

    /**
     * Builds the exact aria-label the site renders for a calendar day button,
     * e.g. LocalDate.of(2026, 3, 10) -> "Tuesday, March 10th, 2026".
     */
    public static String ariaLabelFor(LocalDate date) {
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return dayOfWeek + ", " + month + " " + date.getDayOfMonth()
                + ordinalSuffix(date.getDayOfMonth()) + ", " + date.getYear();
    }

    public static String ordinalSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
            default: return "th";
        }
    }
}
