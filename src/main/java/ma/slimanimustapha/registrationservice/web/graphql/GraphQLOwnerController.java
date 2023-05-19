package ma.slimanimustapha.registrationservice.web.graphql;

import ma.slimanimustapha.registrationservice.entities.Owner;
import ma.slimanimustapha.registrationservice.dto.OwnerRequest;
import ma.slimanimustapha.registrationservice.repositories.OwnerRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQLOwnerController {
    private OwnerRepository ownerRepository;

    public GraphQLOwnerController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    // Simple CRUD operations
    // - Get all
    @QueryMapping
    public List<Owner> getOwners(){
        return ownerRepository.findAll();
    }

    // - Get by id
    @QueryMapping("getOwner")
    public Owner getOwnerById(@Argument Long id){
        return ownerRepository.findById(id).get();
    }

    // - Save
    @MutationMapping("saveOwner")
    public Owner saveOwner(@Argument OwnerRequest ownerRequest){
        Owner owner = new Owner();

        if(ownerRequest.getName() != null) owner.setName(ownerRequest.getName());
        if(ownerRequest.getEmail() != null) owner.setEmail(ownerRequest.getEmail());
        if(ownerRequest.getBirthDate() != null) owner.setBirthDate(ownerRequest.getBirthDate());

        owner.setId(null);
        owner.setVehicles(null);
        return ownerRepository.save(owner);
    }

    // - Update
    @MutationMapping("updateOwner")
    public Owner updateOwner(@Argument Long id, @Argument Owner ownerRequest){
        Owner owner = ownerRepository.findById(id).get();

        if(ownerRequest.getName() != null) owner.setName(ownerRequest.getName());
        if(ownerRequest.getEmail() != null) owner.setEmail(ownerRequest.getEmail());
        if(ownerRequest.getBirthDate() != null) owner.setBirthDate(ownerRequest.getBirthDate());

        return ownerRepository.save(owner);
    }

    // - Delete
    @MutationMapping("deleteOwner")
    public boolean deleteOwner(@Argument Long id){
        ownerRepository.deleteById(id);
        return true;
    }
}
