package com.excilys.cdb.persistence.interfaces;

import java.util.List;

/**
 * 
 * @author sclaudet
 *
 */
public interface AbstractDAO<T> {

  public List<T> getAll();

  public T getById(int id);

  public default void set(T t) {
    System.out.println("Not implemented");
  }

  public default void update(T t) {
    System.out.println("Not implemented");
  }

  public void delete(int id);

}
