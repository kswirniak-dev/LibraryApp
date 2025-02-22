package swirn.spring.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import swirn.spring.dto.BookDTO;
import swirn.spring.dto.RentalDTO;
import swirn.spring.mapper.BookMapper;
import swirn.spring.service.BookService;
import swirn.spring.service.HolderService;
import swirn.spring.service.RentalService;

@Controller
@RequestMapping("rentals")
public class RentalViewController {
	
	private final RentalService rentalService;
	private final BookService bookService;
	private final HolderService holderService;

	private final BookMapper bookMapper;

	@Autowired
	public RentalViewController(RentalService rentalService, BookService bookService, HolderService holderService, BookMapper bookMapper) {
		this.rentalService = rentalService;
		this.bookService = bookService;
		this.holderService = holderService;
		this.bookMapper = bookMapper;
	}

	@GetMapping()
    public String rentalsView(Model model) {
        model.addAttribute("rentals", rentalService.getAll());
        return "rental/list";
    }
	
	@GetMapping("book/{book-id}/rent")
    public String bookRentalView(@PathVariable("book-id") Long id, Model model) {
    	BookDTO book = bookService.getById(id);
    	RentalDTO rental = new RentalDTO();
		rental.setBook(bookMapper.bookDTOtoBookSimpleDTO(book));
    	model.addAttribute("rental", rental);    	
    	model.addAttribute("book", book);
    	model.addAttribute("holders", holderService.getAll());
        return "rental/add";
    }
	
	@PostMapping("add")
	public String addRental(@Valid RentalDTO rental, BindingResult result) {
	    if (result.hasErrors()) {
	        return "rental/add";
	    }
	    rentalService.create(rental);
	    return "redirect:/books";	
		}


	@DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id){
		rentalService.deleteById(id);
		return "redirect:/rentals";

	}
	
    @GetMapping("/{id}/edit")
    public String editView(@PathVariable("id") Long id, Model model) {
    	RentalDTO rental = rentalService.getById(id);
    	model.addAttribute("rental", rental);
    	return "rental/edit";
    }
    
    @PutMapping("/rental/{id}/update")
	public String updateRental(@PathVariable("id") Long id, @Valid RentalDTO rental, BindingResult result)  {
		if (result.hasErrors()) {
	        return "rental/edit";
	    }
	    rentalService.update(id, rental);
	    return "redirect:/rentals";	
    }
}
