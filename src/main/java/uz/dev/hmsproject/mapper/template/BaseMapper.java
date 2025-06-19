package uz.dev.hmsproject.mapper.template;

/**
 * Created by: asrorbek
 * DateTime: 6/17/25 15:05
 **/

public interface BaseMapper<T, R> {

    T toEntity(R r);

    R toDTO(T t);

}
