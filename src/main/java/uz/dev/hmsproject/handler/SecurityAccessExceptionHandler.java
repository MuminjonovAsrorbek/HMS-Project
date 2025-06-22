package uz.dev.hmsproject.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import uz.dev.hmsproject.dto.ErrorDTO;

import java.io.IOException;

@Component
public class SecurityAccessExceptionHandler implements AuthenticationEntryPoint {

    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ErrorDTO errorDTO = new ErrorDTO(
                403,
                authException.getMessage()
        );

        String json = mapper.writeValueAsString(errorDTO);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(json);

    }
}
