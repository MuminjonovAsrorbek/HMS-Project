package uz.dev.hmsproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.dev.hmsproject.entity.template.AbsDeleteEntity;
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
@SQLDelete(sql = "update users set deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
@FieldNameConstants
public class User extends AbsDeleteEntity implements UserDetails {

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, length = 100, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    private Role role;

    private boolean isActive = true;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<WorkScheduler> workSchedulers;

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
