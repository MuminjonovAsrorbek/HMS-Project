package uz.dev.hmsproject.projection;

/**
 * Created by: asrorbek
 * DateTime: 6/27/25 16:58
 **/

public interface StatisticsProjection {

    Long getTotalAppointments();

    Long getTotalPatients();

    Long getTotalCanceledAppointments();

}
