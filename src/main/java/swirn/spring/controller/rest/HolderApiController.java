package swirn.spring.controller.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swirn.spring.dto.HolderDTO;
import swirn.spring.repository.HolderRepository;
import swirn.spring.service.HolderService;

import java.util.List;
@RestController
@RequestMapping("api/v1/holders")
public class HolderApiController {

        private final HolderService holderService;
        private final HolderRepository holderRepository;

        @Autowired
        public HolderApiController(HolderService holderService, HolderRepository holderRepository) {
            this.holderService = holderService;
            this.holderRepository = holderRepository;
        }
        @ResponseStatus(code = HttpStatus.OK)
        @GetMapping
        public List<HolderDTO> showHolderList() {
            return holderService.getAll();
        }

        @ResponseStatus(code = HttpStatus.CREATED)
        @PostMapping("add")
        public HolderDTO addHolder(@Valid @RequestBody HolderDTO holder) {

            return holderService.create(holder);
        }

        @ResponseStatus(code = HttpStatus.ACCEPTED )
        @PutMapping("update/{id}")
        public HolderDTO updateHolder(@PathVariable("id") Long id, @Valid @RequestBody HolderDTO holder)  {
            return holderService.update(id, holder);
        }
        @ResponseStatus(code = HttpStatus.NO_CONTENT )
        @DeleteMapping("{id}")
        public void delete(@PathVariable("id") Long id)	{
            holderRepository.deleteById(id);
        }
}

