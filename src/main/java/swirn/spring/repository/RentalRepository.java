package swirn.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import swirn.spring.domain.entity.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}
