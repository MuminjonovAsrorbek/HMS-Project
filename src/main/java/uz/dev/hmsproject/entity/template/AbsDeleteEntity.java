package uz.dev.hmsproject.entity.template;

import jakarta.persistence.MappedSuperclass;



@MappedSuperclass
public interface AbsDeleteEntity {

    boolean deleted = false;

}
