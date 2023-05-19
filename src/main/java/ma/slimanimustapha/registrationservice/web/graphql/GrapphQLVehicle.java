package ma.slimanimustapha.registrationservice.web.graphql;

import ma.slimanimustapha.registrationservice.entities.Owner;
import ma.slimanimustapha.registrationservice.entities.Vehicle;
import ma.slimanimustapha.registrationservice.dto.VehicleRequest;
import ma.slimanimustapha.registrationservice.repositories.OwnerRepository;
import ma.slimanimustapha.registrationservice.repositories.VehicleRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GrapphQLVehicle {
    private final VehicleRepository vehicleRepository;
    private final OwnerRepository ownerRepository;

    public GrapphQLVehicle(VehicleRepository vehicleRepository, OwnerRepository ownerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.ownerRepository = ownerRepository;
    }

    // Simple CRUD operations
    // - Read all
    @QueryMapping
    public List<Vehicle> getVehicles(){
        return vehicleRepository.findAll();
    }

    // - Read by id
    @QueryMapping("getVehicle")
    public Vehicle getVehicleById(@Argument Long id){
        return vehicleRepository.findById(id).get();
    }

    // - save
    @MutationMapping("saveVehicle")
    public Vehicle saveVehicle(@Argument VehicleRequest vehicleRequest){
        Vehicle vehicle = new Vehicle();

        if(vehicleRequest.getRegNumber() != null) vehicle.setRegNumber(vehicleRequest.getRegNumber());
        if(vehicleRequest.getBrand() != null) vehicle.setBrand(vehicleRequest.getBrand());
        if(vehicleRequest.getFiscalPower() != null) vehicle.setFiscalPower(vehicleRequest.getFiscalPower());
        if(vehicleRequest.getModel() != null) vehicle.setModel(vehicleRequest.getModel());
        vehicle.setId(null);

        return vehicleRepository.save(vehicle);
    }

    // - Update
    @MutationMapping("updateVehicle")
    public Vehicle updateVehicle(@Argument Long id,@Argument Vehicle vehicleRequest){
        Vehicle vehicle = vehicleRepository.findById(id).get();

        if(vehicleRequest.getRegNumber() != null) vehicle.setRegNumber(vehicleRequest.getRegNumber());
        if(vehicleRequest.getBrand() != null) vehicle.setBrand(vehicleRequest.getBrand());
        if(vehicleRequest.getFiscalPower() != null) vehicle.setFiscalPower(vehicleRequest.getFiscalPower());
        if(vehicleRequest.getModel() != null) vehicle.setModel(vehicleRequest.getModel());
        if(vehicleRequest.getOwner() != null){
            Owner owner = new Owner();
            owner.setId(null);
            if (vehicleRequest.getOwner().getName() != null) owner.setName(vehicleRequest.getOwner().getName());
            if (vehicleRequest.getOwner().getEmail() != null) owner.setEmail(vehicleRequest.getOwner().getEmail());
            if (vehicleRequest.getOwner().getBirthDate() != null) owner.setBirthDate(vehicleRequest.getOwner().getBirthDate());
            vehicle.setOwner(owner);
        }

        return vehicleRepository.save(vehicle);
    }

    // - Delete
    @MutationMapping("deleteVehicle")
    public boolean deleteVehicle(@Argument Long id){
        vehicleRepository.deleteById(id);
        return true;
    }

    // Other operations
    // - add vehicle to owner
    @MutationMapping("addVehicleToOwner")
    public Vehicle addVehicleToOwner(@Argument Long ownerId, @Argument Long vehicleId){
        if(vehicleRepository.existsById(vehicleId) && ownerRepository.existsById(ownerId)){
            Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
            Owner owner = ownerRepository.findById(ownerId).get();
            vehicle.setOwner(owner);
            return vehicleRepository.save(vehicle);
        }
        else return null;
    }
}
