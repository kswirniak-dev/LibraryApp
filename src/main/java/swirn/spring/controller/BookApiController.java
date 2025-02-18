package swirn.spring.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import swirn.spring.domain.entity.Book;
import swirn.spring.domain.entity.Rental;
import swirn.spring.dto.BookDTO;
import swirn.spring.repository.BookRepository;
import swirn.spring.repository.HolderRepository;
import swirn.spring.service.BookService;

@RestController
@RequestMapping("api/v1/books")
public class BookApiController {
	
	private final BookService bookService;
	private final HolderRepository holderRepository;

    @Autowired
    public BookApiController(BookService bookService, HolderRepository holderRepository) {
        this.bookService = bookService;
		this.holderRepository = holderRepository;
    }
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public List<BookDTO> showBookList() {
        return bookService.getAll();
    }
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("add")
	public BookDTO addBook(@Valid  @RequestBody BookDTO book) {
		return bookService.save(book);
	}
    @ResponseStatus(code = HttpStatus.ACCEPTED )
	@PutMapping("update/{id}")
	public BookDTO updateBook(@PathVariable("id") Long id, @Valid @RequestBody BookDTO book)  {
	    return bookService.save(book); 	
	}
    @ResponseStatus(code = HttpStatus.NO_CONTENT )
	@DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id)	{		
		bookService.deleteById(id);
	}
}