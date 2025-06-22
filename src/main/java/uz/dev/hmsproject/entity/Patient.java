package uz.dev.hmsproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import uz.dev.hmsproject.entity.template.AbsLongEntity;

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
public class Patient extends AbsLongEntity {

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
