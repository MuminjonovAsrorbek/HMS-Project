package uz.dev.hmsproject.entity.template;

import jakarta.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class AbsDeleteEntity extends AbsLongEntity {

    boolean deleted = false;

}
