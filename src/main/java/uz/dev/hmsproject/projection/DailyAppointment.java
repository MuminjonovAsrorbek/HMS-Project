package uz.dev.hmsproject.projection;

import java.time.LocalDate;

/**
 * Created by: asrorbek
 * DateTime: 6/27/25 17:18
 **/


public interface DailyAppointment {

    LocalDate getDate();

    Long getCount();

}
