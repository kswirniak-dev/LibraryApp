package swirn.spring.service.impl;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swirn.spring.domain.entity.Book;
import swirn.spring.dto.BookDTO;
import swirn.spring.mapper.BookMapper;
import swirn.spring.repository.BookRepository;
import swirn.spring.repository.RentalRepository;
import swirn.spring.service.BookService;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final BookMapper bookMapper;

	@Autowired
	public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper)
	{
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
		return bookMapper.bookToBookDTO(bookRepository.findById(id).orElseThrow(EntityNotFoundException::new));
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		if (!bookRepository.existsById(id)){
			throw new EntityNotFoundException("Book with id " + id + " not found");
		}
		bookRepository.deleteById(id);
	}

	@Override
	@Transactional
	public BookDTO create(BookDTO bookDTO)
	{
		if (bookDTO == null) {
			throw new IllegalArgumentException("Book data cannot be null");
		}
		if (bookDTO.getId() != null) {
			bookDTO.setId(null);
		}
		Book savedBook = bookRepository.save(bookMapper.bookDTOtoBook(bookDTO));
		return bookMapper.bookToBookDTO(savedBook);
	}

	@Override
	@Transactional
	public BookDTO update(Long id, BookDTO bookDTO) {
		Book existingBook = bookRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

		if (bookDTO.getTitle() != null) {
			existingBook.setTitle(bookDTO.getTitle());
		}
		if (bookDTO.getAuthor() != null) {
			existingBook.setAuthor(bookDTO.getAuthor());
		}
		if (bookDTO.getYear() != null) {
			existingBook.setYear(bookDTO.getYear());
		}

		Book savedBook = bookRepository.save(existingBook);
		return bookMapper.bookToBookDTO(savedBook);
	}
}
