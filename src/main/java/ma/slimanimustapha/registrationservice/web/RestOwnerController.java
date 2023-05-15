package ma.slimanimustapha.registrationservice.web;

import ma.slimanimustapha.registrationservice.entities.Owner;
import ma.slimanimustapha.registrationservice.repositories.OwnerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web")
public class RestOwnerController {

    private OwnerRepository ownerRepository;

    public RestOwnerController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    // Simple CRUD operations
    // - Create
    @GetMapping("/owners")
    public List<Owner> getOwners(){
        return ownerRepository.findAll();
    }

    // - Read
    @GetMapping("/owners/{id}")
    public Owner getOwnerById(@PathVariable("id") Long id){
        return ownerRepository.findById(id).get();
    }

    // - Save
    @PostMapping("/owners")
    public Owner saveOwner(@RequestBody Owner owner){
        return ownerRepository.save(owner);
    }

    // - Update
    @PutMapping("/owners/{id}")
    public Owner updateOwner(@PathVariable("id") Long id,@RequestBody Owner owner){
        owner.setId(id);
        return ownerRepository.save(owner);
    }

    // - Delete
    @DeleteMapping("/owners/{id}")
    public void deleteOwner(@PathVariable("id") Long id){
        ownerRepository.deleteById(id);
    }
}
