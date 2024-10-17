package baloncesto.patrondao;

import java.util.Set;

public interface Dao <T, K> {
    T get(K id);
    Set<T> getAll();
    boolean save(T obxeto);
    boolean delete(T obxeto);
    boolean deleteById(K id);
    boolean deleteAll();
    void update(T obxeto);
}
