package swirn.spring.controller.view;


import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


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

	@GetMapping("{id}/edit")
	public String editView(@PathVariable("id") Long id, Model model)
	{
		RentalDTO rental = rentalService.getById(id);
		System.out.println("Edit view - Rental ID: " + rental.getId());

		model.addAttribute("rental", rental);
		model.addAttribute("books", bookService.getAll());
		model.addAttribute("holders", holderService.getAll());

		return "rental/edit";
	}

	@PutMapping("{id}/update")
	public String updateRental(@PathVariable("id") Long id,
							   @ModelAttribute("rental") RentalDTO rental,
							   BindingResult result,
							   Model model) {
		System.out.println("Update method - Received ID: " + id);
		System.out.println("Update method - Rental object ID: " + (rental != null ? rental.getId() : "null"));
		System.out.println("Start Date: " + rental.getStartDate());
		System.out.println("End Date: " + rental.getEndDate());

		if (result.hasErrors()) {
			// Re-add all necessary attributes for the view
			model.addAttribute("books", bookService.getAll());
			model.addAttribute("holders", holderService.getAll());
			return "rental/edit";
		}


		rentalService.update(id, rental);
		return "redirect:/rentals";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				if (text == null || text.isEmpty()) {
					setValue(null);
				} else {
					setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				}
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				if (value == null) {
					return "";
				}
				return DateTimeFormatter.ofPattern("yyyy-MM-dd").format((LocalDate) value);
			}
		});
	}
}
