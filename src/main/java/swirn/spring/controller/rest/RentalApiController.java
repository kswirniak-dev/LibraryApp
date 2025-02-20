package swirn.spring.controller.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swirn.spring.dto.RentalDTO;
import swirn.spring.service.BookService;
import swirn.spring.service.HolderService;
import swirn.spring.service.RentalService;

import java.util.List;

@RestController
@RequestMapping("api/v1/holders")
public class RentalApiController {

    private final RentalService rentalService;
    private final HolderService holderService;

    private final BookService bookService;

    @Autowired
    public RentalApiController(HolderService holderService, RentalService rentalService, BookService bookService) {
        this.holderService = holderService;
        this.rentalService = rentalService;
        this.bookService = bookService;
    }
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public List<RentalDTO> getRentals() {
        return rentalService.getAll();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("add")
    public RentalDTO addRental(@Valid @RequestBody RentalDTO rental) {
        return rentalService.create(rental);
    }

    @ResponseStatus(code = HttpStatus.ACCEPTED )
    @PutMapping("update/{id}")
    public RentalDTO updateHolder(@PathVariable("id") Long id, @Valid @RequestBody RentalDTO rental)  {
        return rentalService.update(id, rental);
    }
    @ResponseStatus(code = HttpStatus.NO_CONTENT )
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id)	{
        rentalService.deleteById(id);
    }
}