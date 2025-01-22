package swirn.spring.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    
	@PostMapping("/addbook")
	public String addBook(@Valid Book book, BindingResult result, Model model) {
	    
		if (result.hasErrors()) {
	        return "add-book";
	    }
	    
	    bookRepository.save(book);
	    return "redirect:/index";	
		}
	
	@DeleteMapping("deletebook/{id}")
    public String delete(@PathVariable("id") Long id){
		bookRepository.deleteById(id);        
        return "redirect:/index";
	}
}