package uz.dev.hmsproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import uz.dev.hmsproject.dto.response.RecaptchaResponseDTO;
import uz.dev.hmsproject.service.template.RecaptchaService;

@Service
@RequiredArgsConstructor
public class RecaptchaServiceImpl implements RecaptchaService {

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean verify(String token) {

        String url = "https://www.google.com/recaptcha/api/siteverify?secret=" + recaptchaSecret + "&response=" + token;

        try {
            ResponseEntity<RecaptchaResponseDTO> response =
                    restTemplate.postForEntity(url, null, RecaptchaResponseDTO.class);

            return response.getBody() != null && response.getBody().isSuccess();

        } catch (Exception e) {
            return false;
        }
    }
}
