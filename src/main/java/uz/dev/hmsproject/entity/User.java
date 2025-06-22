package uz.dev.hmsproject.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.dev.hmsproject.entity.template.AbsLongEntity;

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
public class User extends AbsLongEntity {

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, length = 100, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    private Role role;

    private boolean isActive = true;
}
