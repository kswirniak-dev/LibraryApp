package swirn.spring.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import swirn.spring.dto.RentalDTO;
import swirn.spring.mapper.BookMapper;
import swirn.spring.service.BookService;
import swirn.spring.service.HolderService;
import swirn.spring.service.RentalService;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
	
	@GetMapping("add")
    public String bookRentalView(Model model) {
    	RentalDTO rental = new RentalDTO();
		model.addAttribute("rental", rental);
    	model.addAttribute("books", bookService.getAll());
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
		model.addAttribute("books", bookService.getAll());
		model.addAttribute("holders", holderService.getAll());
    	return "rental/edit";
    }
    
    @PutMapping("{id}/update")
	public String updateRental(@PathVariable("id") Long id, @Valid RentalDTO rental, BindingResult result)  {
		if (result.hasErrors()) {
	        return "rental/edit";
	    }
	    rentalService.update(id, rental);
	    return "redirect:/rentals";	
    }

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));


		binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				if (text == null || text.isEmpty()) {
					setValue(null);
				} else {
					setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
				}
			}

			@Override
			public String getAsText() {
				LocalDate value = (LocalDate) getValue();
				return (value != null ? value.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : "");
			}
		});
	}
}
