package uz.dev.hmsproject.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.dev.hmsproject.entity.template.AbsLongEntity;
import uz.dev.hmsproject.enums.Permission;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/16/25 11:49
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "roles")
public class Role extends AbsLongEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "role_permissions",
            joinColumns = {@JoinColumn(name = "role_id")}
    )
    @Column(name = "permission_key")
    private List<Permission> permissions;
}
