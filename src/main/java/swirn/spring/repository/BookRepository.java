package swirn.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import swirn.spring.domain.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
