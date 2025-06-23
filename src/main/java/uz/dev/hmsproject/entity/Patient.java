package uz.dev.hmsproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.dev.hmsproject.entity.template.AbsDeleteEntity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by: asrorbek
 * DateTime: 6/16/25 12:13
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SQLDelete(sql = "update patient set deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
public class Patient extends AbsDeleteEntity {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @CreationTimestamp
    private Timestamp createdAt;


}
