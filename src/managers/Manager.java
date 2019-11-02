package managers;

import java.util.List;

public interface Manager<T> {

    List<T> get();

    void add(T value);

    void update(T value);

    T getById(long Id);

    void deleteById(long Id);


}
