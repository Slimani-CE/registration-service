package ma.slimanimustapha.registrationservice.web.rest;

import ma.slimanimustapha.registrationservice.dto.OwnerRequest;
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
    // - Read all
    @GetMapping("/owners")
    public List<Owner> getOwners(){
        return ownerRepository.findAll();
    }

    // - Read by id
    @GetMapping("/owners/{id}")
    public Owner getOwnerById(@PathVariable("id") Long id){
        return ownerRepository.findById(id).get();
    }

    // - Save
    @PostMapping("/owners")
    public Owner saveOwner(@RequestBody OwnerRequest ownerRequest){
        Owner owner = Owner.builder()
                .id(null)
                .name(ownerRequest.getName())
                .email(ownerRequest.getEmail())
                .birthDate(ownerRequest.getBirthDate())
                .build();

        return ownerRepository.save(owner);
    }

    // - Update
    @PutMapping("/owners/{id}")
    public Owner updateOwner(@PathVariable("id") Long id,@RequestBody OwnerRequest ownerRequest){
        if(ownerRepository.existsById(id)){
            Owner owner = ownerRepository.findById(id).get();

            if(ownerRequest.getName() != null) owner.setName(ownerRequest.getName());
            if(ownerRequest.getEmail() != null) owner.setEmail(ownerRequest.getEmail());
            if(ownerRequest.getBirthDate() != null) owner.setBirthDate(ownerRequest.getBirthDate());

            return ownerRepository.save(owner);
        }
        else return null;
    }

    // - Delete
    @DeleteMapping("/owners/{id}")
    public boolean deleteOwner(@PathVariable("id") Long id){
        if(ownerRepository.existsById(id)){
            ownerRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }

    // Custom operations
    // - Check if owner exists
    @GetMapping("/owners/exist/{id}")
    public boolean ownerExists(@PathVariable("id") Long id){
        return ownerRepository.existsById(id);
    }
}
