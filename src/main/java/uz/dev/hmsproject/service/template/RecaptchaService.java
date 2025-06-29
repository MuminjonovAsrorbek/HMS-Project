package uz.dev.hmsproject.service.template;

public interface RecaptchaService {

    boolean verify(String token);

}
