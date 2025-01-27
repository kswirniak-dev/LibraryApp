package swirn.spring.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import swirn.spring.domain.entity.Book;
import swirn.spring.repository.BookRepository;

@Controller
public class BookController {
	
	private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @GetMapping("/")
    public String redirectToMain() {
    	return "redirect:/index";
    }
	
    @GetMapping("/index")
    public String showBookList(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "index";
    }
    
    @GetMapping("/newbookform")
    public String showSignUpForm(Book book) {
        return "add-book";
    }
    
    @GetMapping("/editbook/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
    	Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id:" + id));
    	model.addAttribute("book", book);
    	return "edit-book";
    }
    
	@PostMapping("/addbook")
	public String addBook(@Valid Book book, BindingResult result, Model model) {
	    
		if (result.hasErrors()) {
	        return "add-book";
	    }
	    
	    bookRepository.save(book);
	    return "redirect:/index";	
		}
	
	@PutMapping("/updatebook/{id}")
	public String  updateBook(@PathVariable("id") Long id, @Valid Book book, BindingResult result, Model model)  {
		if (result.hasErrors()) {
	        return "add-book";
	    }
	    book.setId(id);
	    bookRepository.save(book);
	    return "redirect:/index";	
	}
	
	@SuppressWarnings("finally")
	@DeleteMapping("/deletebook/{id}")
    public String delete(@PathVariable("id") Long id){
		try 
		{
			bookRepository.deleteById(id);
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid book id:" + id);
		}
		finally {
			return "redirect:/index";
		}
	}
}