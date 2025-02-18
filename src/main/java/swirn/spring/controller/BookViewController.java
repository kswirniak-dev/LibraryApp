package swirn.spring.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import swirn.spring.dto.BookDTO;
import swirn.spring.repository.HolderRepository;
import swirn.spring.service.BookService;

@Controller
@RequestMapping("books")
public class BookViewController {
	private final BookService bookService;
	
	@Autowired
    public BookViewController(BookService bookService) {
        this.bookService = bookService;
    }
	
	@GetMapping
	public String indexView(Model model){
		model.addAttribute("books", bookService.getAll());
		return "book/list";
	}
	
	@GetMapping("add")
	public String addView(Model model){
		model.addAttribute("book", new BookDTO());

		return "book/add";
	}
	
	@PostMapping
	public String add(BookDTO book){
		bookService.save(book);
		return "redirect:/book/list";
	}
	
	@GetMapping("{id}")
	public String singleView(Model model, @PathVariable Long id){
		model.addAttribute("book", bookService.getById(id));

		return "book/edit";
	}
	
	
	

}
