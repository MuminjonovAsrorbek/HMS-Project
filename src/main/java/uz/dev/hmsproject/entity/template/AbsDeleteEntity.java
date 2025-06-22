package uz.dev.hmsproject.entity.template;

import jakarta.persistence.MappedSuperclass;

/**
 * Created by: asrorbek
 * DateTime: 6/22/25 12:22
 **/

@MappedSuperclass
public interface AbsDeleteEntity {

    boolean deleted = false;

}
