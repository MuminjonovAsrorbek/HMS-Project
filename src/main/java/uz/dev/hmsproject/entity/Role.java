package uz.dev.hmsproject.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.dev.hmsproject.entity.template.AbsLongEntity;
import uz.dev.hmsproject.enums.Permissions;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "roles")
public class Role extends AbsLongEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(
            name = "role_permissions",
            joinColumns = {@JoinColumn(name = "role_id")}
    )
    @Column(name = "permission_key")
    private List<Permissions> permissions;
}
