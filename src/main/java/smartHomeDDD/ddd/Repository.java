package smartHomeDDD.ddd;

import java.util.Optional;

public interface Repository<ID extends DomainId, T extends AggregateRoot<ID> > {

    T save(T entity);

    Iterable<T> findAll();

    Optional<T> ofIdentity(ID id);

    boolean containsOfIdentity(ID id);
}