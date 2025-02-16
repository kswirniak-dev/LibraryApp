package swirn.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import swirn.spring.repository.HolderRepository;
import swirn.spring.service.BookService;

@Controller
public class BookViewController {
	private final BookService bookService;
	
	@Autowired
    public BookViewController(BookService bookService) {
        this.bookService = bookService;
    }
	
	@GetMapping
	public String indexView(Model model){
		model.addAttribute("books", bookService.getAll());

		return "books";
	}
	

}
