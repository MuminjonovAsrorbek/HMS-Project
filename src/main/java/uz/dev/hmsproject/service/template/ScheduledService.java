package uz.dev.hmsproject.service.template;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledService {


     private final AppointmentService appointmentService;

    @Scheduled(cron = "0 0 21 * * *")
     public void changeStatus() {
         appointmentService.changeStatus();
     }
}
