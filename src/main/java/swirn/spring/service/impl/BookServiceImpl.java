package swirn.spring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;






import swirn.spring.domain.entity.Book;
import swirn.spring.dto.BookDTO;
import swirn.spring.mapper.BookMapper;
import swirn.spring.repository.BookRepository;
import swirn.spring.service.BookService;
import org.springframework.transaction.annotation.Transactional;


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
	@Transactional(readOnly = true)
	public List<BookDTO> getAll() {
		return bookMapper.convertBookListToBookDTOList(bookRepository.findAll());		
	}

	@Override
	@Transactional(readOnly = true)
	public BookDTO getById(Long id) {
		return bookMapper.bookToBookDTO(bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException()));
	}

	@Override
	@Transactional
	public BookDTO deleteById(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
		bookRepository.deleteById(id);
		return bookMapper.bookToBookDTO(book);
	}

	@Override
	@Transactional
	public BookDTO save(BookDTO book) {
		Book savedBook = bookRepository.save(bookMapper.bookDTOtoBook(book));
		return bookMapper.bookToBookDTO(savedBook);
	}
	
	@Override
	@Transactional
	public BookDTO update(Long id, BookDTO book) {
		BookDTO bookFromRepo = bookMapper.bookToBookDTO(bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException()));
		bookFromRepo.setTitle(book.getTitle());
		bookFromRepo.setAuthor(book.getAuthor());
		bookFromRepo.setYear(book.getYear());
		Book savedBook = bookRepository.save(bookMapper.bookDTOtoBook(bookFromRepo));
		return bookMapper.bookToBookDTO(savedBook);
	}

}
