package uz.dev.hmsproject.mapper.template;


import java.util.List;

public interface BaseMapper<T, R> {

    T toEntity(R r);

    R toDTO(T t);

    List<R> toDTO(List<T> dtos);
}
