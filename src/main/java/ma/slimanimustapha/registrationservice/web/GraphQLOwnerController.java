package ma.slimanimustapha.registrationservice.web;

import ma.slimanimustapha.registrationservice.entities.Owner;
import ma.slimanimustapha.registrationservice.entities.OwnerRequest;
import ma.slimanimustapha.registrationservice.repositories.OwnerRepository;
import ma.slimanimustapha.registrationservice.repositories.VehicleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Date;
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
    @MutationMapping
    public Owner saveOwner(@Argument OwnerRequest ownerRequest){
        Owner owner = new Owner();

        if(ownerRequest.getName() != null) owner.setName(ownerRequest.getName());
        if(ownerRequest.getEmail() != null) owner.setEmail(ownerRequest.getEmail());
        owner.setId(null);
        owner.setVehicles(null);
        owner.setBirthDate(new Date());

        return ownerRepository.save(owner);
    }

    // - Update
    @QueryMapping
    public Owner updateOwner(@Argument Long id,@Argument Owner owner){
        owner.setId(id);
        return ownerRepository.save(owner);
    }

    // - Delete
    @QueryMapping
    public void deleteOwner(@Argument Long id){
        ownerRepository.deleteById(id);
    }
}
