package smartHomeDDD.ddd;

public interface DomainEntity<ID extends DomainId> {

    ID identity();

    boolean sameAs(Object object);
}
