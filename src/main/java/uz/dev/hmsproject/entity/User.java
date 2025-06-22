package uz.dev.hmsproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.dev.hmsproject.entity.template.AbsLongEntity;
import uz.dev.hmsproject.enums.Permissions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/16/25 11:44
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User extends AbsLongEntity implements UserDetails {

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, length = 100, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    private Role role;

    private boolean isActive = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Permissions permission : this.role.getPermissions()) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.name());
            authorities.add(authority);
        }
        return authorities;
    }
}
