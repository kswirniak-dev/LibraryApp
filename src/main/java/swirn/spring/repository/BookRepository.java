package swirn.spring.repository;

import org.springframework.data.repository.CrudRepository;

import swirn.spring.domain.entity.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

}
