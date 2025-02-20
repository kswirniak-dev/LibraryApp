package swirn.spring.service;

import java.util.List;

import swirn.spring.dto.BookDTO;


public interface BookService {
	
	List<BookDTO> getAll();
	
	BookDTO getById(Long id);
	
	void deleteById(Long id);
	
	BookDTO create(BookDTO book);

	BookDTO update(Long id, BookDTO book);
	
}
