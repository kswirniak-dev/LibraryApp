package swirn.spring.domain.repository;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository<Book, Long> extends CrudRepository<Book, Long> {

}
