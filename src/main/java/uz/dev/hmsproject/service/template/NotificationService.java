package uz.dev.hmsproject.service.template;

import uz.dev.hmsproject.dto.response.AppointmentDTO;

/**
 * Created by: asrorbek
 * DateTime: 6/29/25 10:27
 **/

public interface NotificationService {

    void sendEmail(String to, String subject, AppointmentDTO appointmentDTO);

}
