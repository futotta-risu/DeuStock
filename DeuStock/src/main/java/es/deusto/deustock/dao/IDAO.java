package es.deusto.deustock.dao;

import java.util.Collection;

public interface IDAO <T>{

    boolean has(Object identity);

    void store(T object);
    void update(T object);
    void delete(T object);

    T get(Object identity);
    Collection<T> getAll();


}
