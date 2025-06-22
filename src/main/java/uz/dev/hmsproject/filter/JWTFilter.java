package uz.dev.hmsproject.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.service.security.AuthService;
import uz.dev.hmsproject.service.security.JWTService;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by: asrorbek
 * DateTime: 6/20/25 16:00
 **/

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final AuthService authService;

    public JWTFilter(JWTService jwtService, @Lazy AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        checkBearer(request, response);

        filterChain.doFilter(request, response);
    }

    private void checkBearer(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorization = request.getHeader("Authorization");

        if (Objects.isNull(authorization) || !authorization.startsWith("Bearer ")) {

            return;

        }

        String token = authorization.substring(7);

        try {

            String email = jwtService.parseToken(token);

            User user = authService.loadUserByUsername(email);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            ));
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }
}
