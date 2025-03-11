package swirn.spring.controller.view;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import swirn.spring.dto.BookDTO;
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
	public String booksView(Model model){
		model.addAttribute("books", bookService.getAll());
		return "book/list";
	}

	@GetMapping("main")
	public String extendedBooksView(Model model) {
		model.addAttribute("books", bookService.getAll());
		return "index";
	}
	
	@GetMapping("add")
	public String addView(Model model){
		model.addAttribute("book", new BookDTO());
		return "book/add";
	}
	
	@PostMapping
	public String add(@Valid BookDTO book){
		bookService.create(book);
		return "redirect:/books";
	}
	
	@PostMapping("{id}")
	public String update(@Valid BookDTO book, @PathVariable Long id){
		bookService.update(id, book);
		return "redirect:/books";
	}
	
	@GetMapping("{id}")
	public String singleView(Model model, @PathVariable Long id){
		model.addAttribute("book", bookService.getById(id));
		return "book/edit";
	}
	
	@DeleteMapping("{id}/delete")
	public String deleteView(@PathVariable Long id) {
		bookService.deleteById(id);
		return "redirect:/books";
	}
	
	
	

}
