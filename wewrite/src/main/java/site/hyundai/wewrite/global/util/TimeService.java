package site.hyundai.wewrite.global.util;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 김동욱
 */
@Service
public class TimeService {


    public String parseLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDateTime.format(formatter);
    }


    public String parseLocalDateTimeForMap(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return localDateTime.format(formatter);
    }

    // "yyyy-MM-dd"
    public LocalDateTime parseStringDateTimeForMap(String localDateTime) {

        LocalDate localDate = LocalDate.parse(localDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        return localDate.atTime(LocalTime.MIDNIGHT);

    }

}
