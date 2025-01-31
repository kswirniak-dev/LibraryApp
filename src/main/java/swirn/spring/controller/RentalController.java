package swirn.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import jakarta.validation.Valid;
import swirn.spring.domain.entity.Holder;
import swirn.spring.domain.entity.Rental;
import swirn.spring.repository.BookRepository;
import swirn.spring.repository.RentalRepository;

public class RentalController {
	
	private final RentalRepository rentalRepository;

    @Autowired
    public RentalController(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }
    
	@GetMapping("/rentals")
    public String showBookList(Model model) {
        model.addAttribute("rentals", rentalRepository.findAll());
        return "rentals";
    }
	
	@PostMapping("/rental/add")
	public String addRental(@Valid Rental rental, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        return "add-holder";
	    }
	    rentalRepository.save(rental);
	    return "redirect:/holders";	
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
