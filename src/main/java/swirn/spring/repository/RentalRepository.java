package swirn.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import swirn.spring.domain.entity.Rental;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {

}
