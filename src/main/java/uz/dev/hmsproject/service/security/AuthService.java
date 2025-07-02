package uz.dev.hmsproject.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.dev.hmsproject.dto.request.LoginDTO;
import uz.dev.hmsproject.dto.response.TokenDTO;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.exception.EntityNotFoundException;
import uz.dev.hmsproject.exception.PasswordIncorrectException;
import uz.dev.hmsproject.repository.UserRepository;
import uz.dev.hmsproject.utils.SecurityUtils;

import java.util.Optional;

/**
 * Created by: asrorbek
 * DateTime: 6/20/25 15:14
 **/

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final UserRepository userRepository;

    private final SecurityUtils securityUtils;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty())

            throw new EntityNotFoundException("User not found with username : " + username, HttpStatus.NOT_FOUND);

        return optionalUser.get();

    }

    public TokenDTO getToken(LoginDTO loginDTO) {

        User user = loadUserByUsername(loginDTO.getUsername());

        if (!user.isActive())
            throw new PasswordIncorrectException("Username or password incorrect", HttpStatus.BAD_REQUEST);

        String encodedPassword = user.getPassword();

        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), encodedPassword);

        if (!matches) {

            throw new PasswordIncorrectException("Password incorrect", HttpStatus.BAD_REQUEST);

        }

        String token = jwtService.generateToken(loginDTO.getUsername());

        return new TokenDTO(token);
    }

//    public UserPermissionDTO getUserPagesAndPermissions() {
//
//        User currentUser = securityUtils.getCurrentUser();
//
//        Role role = currentUser.getRole();
//
//        List<Permissions> permissions = role.getPermissions();
//
//        List<PageEnum> pages = permissions.stream().map(Permissions::getPage).toList();
//
//        return new UserPermissionDTO(permissions, pages);
//    }
}
