package uz.dev.hmsproject.service.template;

import java.util.List;


public interface BaseService<T, ID> {

    List<T> getAll();

    T getById(ID id);

    void create(T t);

    void update(ID id, T t);

    void delete(ID id);

}
