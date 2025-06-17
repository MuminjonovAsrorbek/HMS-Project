package uz.dev.hmsproject.service.template;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/17/25 13:30
 **/

public interface BaseService<T, ID> {

    List<T> getAll();

    T getById(ID id);

    void create(T t);

    void update(ID id, T t);

    void delete(ID id);

}
