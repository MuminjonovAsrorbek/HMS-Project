package uz.dev.hmsproject.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.response.AppointmentDTO;
import uz.dev.hmsproject.exception.SendEmailErrorException;
import uz.dev.hmsproject.service.template.NotificationService;
import uz.dev.hmsproject.utils.CommonUtils;

/**
 * Created by: asrorbek
 * DateTime: 6/29/25 10:27
 **/

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;

    @Override
    @Async
    @Transactional
    public void sendEmail(String to, String subject, AppointmentDTO appointmentDTO) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(generateAppointmentHtml(appointmentDTO), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new SendEmailErrorException("Email error occurred: " + to, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Async
    @Transactional
    public void sendEmail(String to, String subject, String html) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new SendEmailErrorException("Email error occurred: " + to, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public String generateAppointmentHtml(AppointmentDTO appointment) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; background-color: #f2f2f2; margin: 0; padding: 0; }
                        .container { background-color: #fff; max-width: 600px; margin: 30px auto; padding: 20px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); }
                        .header { background-color: #4CAF50; color: white; padding: 15px; border-radius: 8px 8px 0 0; text-align: center; font-size: 24px; }
                        .content { padding: 20px; font-size: 16px; color: #333; }
                        .footer { padding: 10px; text-align: center; font-size: 12px; color: #888; }
                        .button { display: inline-block; background-color: #4CAF50; color: white; padding: 10px 20px; margin-top: 20px; border-radius: 4px; text-decoration: none; font-weight: bold; }
                        .appointment-info { margin-top: 15px; padding: 15px; background-color: #f9f9f9; border-left: 4px solid #4CAF50; border-radius: 4px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">Qabul Tasdiqlangan</div>
                        <div class="content">
                            <p>Hurmatli <b>%s</b>,</p>
                            <p>Sizning qabulingiz muvaffaqiyatli ro'yxatdan o'tkazildi.</p>
                
                            <div class="appointment-info">
                                <p><b>🗓 Sana va vaqt:</b> %s</p>
                                <p><b>👨‍⚕️ Shifokor:</b> %s</p>
                                <p><b>🏥 Xona:</b> %s</p>
                                <p><b>💰 Narx:</b> %s so'm</p>
                            </div>
                
                            <a href="#" class="button">Qo'shimcha ma'lumot olmoqchi bo'lsangiz 1022 qisqa raqamiga qo'ng'iroq qiling</a>
                
                            <p style="margin-top: 20px;">Iltimos, qabul vaqtida o'z vaqtida yetib kelishingizni so'raymiz.</p>
                        </div>
                        <div class="footer">&copy; 2025 Hospital Management System</div>
                    </div>
                </body>
                </html>
                """.formatted(
                appointment.getPatient(),
                CommonUtils.formattedDate(appointment.getDateTime()),
                appointment.getDoctor(),
                appointment.getRoom(),
                appointment.getPrice()
        );
    }

}
