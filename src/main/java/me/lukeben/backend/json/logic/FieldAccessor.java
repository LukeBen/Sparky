package me.lukeben.backend.json.logic;

public interface FieldAccessor {

    Object get(Object entity);

    void set(Object entity, Object val);

}
