package swirn.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import swirn.spring.dto.BookDTO;


public interface BookService {
	
	List<BookDTO> getAll();
	
	BookDTO getById(Long id);
	
	BookDTO deleteById(Long id);
	
	BookDTO save(BookDTO book);
	
}
