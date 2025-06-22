package uz.dev.hmsproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.dev.hmsproject.entity.template.AbsDeleteEntity;
import uz.dev.hmsproject.entity.template.AbsLongEntity;
import uz.dev.hmsproject.enums.Permissions;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "roles")
@SQLDelete(sql = "update roles set deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
public class Role extends AbsLongEntity implements AbsDeleteEntity {

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
