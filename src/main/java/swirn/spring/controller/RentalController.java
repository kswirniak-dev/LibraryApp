package swirn.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import jakarta.validation.Valid;
import swirn.spring.domain.entity.Book;
import swirn.spring.domain.entity.Holder;
import swirn.spring.domain.entity.Rental;
import swirn.spring.repository.BookRepository;
import swirn.spring.repository.HolderRepository;
import swirn.spring.repository.RentalRepository;

@Controller
public class RentalController {
	
	private final RentalRepository rentalRepository;
	private final BookRepository bookRepository;
	private final HolderRepository holderRepository;

    @Autowired
    public RentalController(RentalRepository rentalRepository, BookRepository bookRepository, HolderRepository holderRepository) {
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
        this.holderRepository = holderRepository;
    }
    
	@GetMapping("/rentals")
    public String showBookList(Model model) {
        model.addAttribute("rentals", rentalRepository.findAll());
        return "rentals";
    }
	
	@GetMapping("/book/{id}/rent")
    public String addRentalToBook(@PathVariable("id") Long id, Model model) {
    	Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id:" + id));
    	Rental rental = new Rental();
    	rental.setBook(book);
    	model.addAttribute("rental", rental);    	
    	model.addAttribute("book", book);
    	model.addAttribute("holders", holderRepository.findAll());
        return "add-rental";
    }
	
	@PostMapping("/rentals/new")
	public String addRental(@Valid Rental rental, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        return "add-rental";
	    }
	    rentalRepository.save(rental);
	    return "redirect:/books";	
		}

	@SuppressWarnings("finally")
	@DeleteMapping("/rental/delete/{id}")
    public String delete(@PathVariable("id") Long id){
		try 
		{
			rentalRepository.deleteById(id);
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid rental id:" + id);
		}
		finally {
			return "redirect:/rentals";
		}
	}
	
    @GetMapping("/rental/edit/{id}")
    public String showEditRentalForm(@PathVariable("id") Long id, Model model) {
    	Rental rental = rentalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rental id:" + id));
    	model.addAttribute("rental", rental);
    	return "edit-rental";
    }
    
    @PutMapping("/rental/update/{id}")
	public String updateRental(@PathVariable("id") Long id, @Valid Rental rental, BindingResult result, Model model)  {
		if (result.hasErrors()) {
	        return "add-rental";
	    }
	    rental.setId(id);
	    rentalRepository.save(rental);
	    return "redirect:/rentals";	
    }
}
