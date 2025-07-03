package uz.dev.hmsproject.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtils {

    public static <T> T getOrDef(T value, T def) {
        return value == null ? def : value;
    }

    public static String formattedDate(LocalDateTime localDateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return localDateTime.format(formatter);
    }
}
