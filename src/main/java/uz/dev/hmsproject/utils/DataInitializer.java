package uz.dev.hmsproject.utils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.dev.hmsproject.entity.Role;
import uz.dev.hmsproject.entity.User;
import uz.dev.hmsproject.enums.Permissions;
import uz.dev.hmsproject.repository.RoleRepository;
import uz.dev.hmsproject.repository.UserRepository;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 16:57
 **/

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> {

                    Role r = new Role();

                    r.setName("ADMIN");

                    return roleRepository.save(r);

                });

        Permissions[] permissions = Permissions.values();

        for (Permissions permission : permissions) {

            if (Objects.nonNull(adminRole.getPermissions())) {

                if (!adminRole.getPermissions().contains(permission)) {

                    adminRole.getPermissions().add(permission);

                }

            } else {

                adminRole.setPermissions(new ArrayList<>());
                adminRole.getPermissions().add(permission);

            }

        }

        if (!userRepository.existsByUsername("admin")) {

            User admin = new User();
            admin.setFullName("Administrator");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin")); // 🔐 use strong default
            admin.setRole(adminRole);

            userRepository.save(admin);
        }
    }
}
