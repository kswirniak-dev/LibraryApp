package swirn.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import swirn.spring.domain.entity.Book;
import swirn.spring.domain.entity.Rental;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b.rentals from Book b where b.id = :bookId ")
    List<Rental> findAllBookRentals(Long bookId);
}
