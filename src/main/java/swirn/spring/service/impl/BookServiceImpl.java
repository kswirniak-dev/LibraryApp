package swirn.spring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import swirn.spring.domain.entity.Book;
import swirn.spring.dto.BookDTO;
import swirn.spring.mapper.BookMapper;
import swirn.spring.repository.BookRepository;
import swirn.spring.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	
	private final BookRepository bookRepository;
	private final BookMapper bookMapper;
	
	@Autowired
	public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
		this.bookRepository = bookRepository;
		this.bookMapper = bookMapper;
	}

	@Override
	public List<BookDTO> getAll() {
		return bookMapper.convertBookListToBookDTOList(bookRepository.findAll());		
	}

	@Override
	public BookDTO getById(Long id) {
		return bookMapper.bookToBookDTO(bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException()));
	}

	@Override
	public BookDTO deleteById(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
		bookRepository.deleteById(id);
		return bookMapper.bookToBookDTO(book);
	}

	@Override
	public BookDTO save(BookDTO book) {
		Book savedBook = bookRepository.save(bookMapper.bookDTOtoBook(book));
		return bookMapper.bookToBookDTO(savedBook);
	}

}
