package ma.slimanimustapha.registrationservice.web.rest;

import ma.slimanimustapha.registrationservice.dto.VehicleRequest;
import ma.slimanimustapha.registrationservice.entities.Vehicle;
import ma.slimanimustapha.registrationservice.repositories.OwnerRepository;
import ma.slimanimustapha.registrationservice.repositories.VehicleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web")
public class RestVehicleController {
    private final VehicleRepository vehicleRepository;
    private final OwnerRepository ownerRepository;

    public RestVehicleController(VehicleRepository vehicleRepository, OwnerRepository ownerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.ownerRepository = ownerRepository;
    }

    // Simple CRUD operations
    // - Read all
    @GetMapping("/vehicles")
    public List<Vehicle> getVehicles(){
        return vehicleRepository.findAll();
    }

    // - Read by id
    @GetMapping("/vehicles/{id}")
    public Vehicle getVehicleById(@PathVariable("id") Long id){
        if(vehicleRepository.existsById(id)){
            return vehicleRepository.findById(id).get();
        }
        else return null;
    }

    // - Update
    @PostMapping("/vehicles")
    public Vehicle saveVehicle(@RequestBody VehicleRequest vehicleRequest){
        Vehicle vehicle = Vehicle.builder()
                .id(null)
                .owner(null)
                .brand(vehicleRequest.getBrand())
                .fiscalPower(vehicleRequest.getFiscalPower())
                .model(vehicleRequest.getModel())
                .regNumber(vehicleRequest.getRegNumber())
                .build();
        return vehicleRepository.save(vehicle);
    }

    // - Update
    @PutMapping("/vehicles/{id}")
    public Vehicle updateVehicle(@PathVariable("id") Long id,@RequestBody VehicleRequest vehicleRequest){
        if(vehicleRepository.existsById(id)){
           Vehicle vehicle = vehicleRepository.findById(id).get();
           if (vehicleRequest.getBrand() != null) vehicle.setBrand(vehicleRequest.getBrand());
           if (vehicleRequest.getFiscalPower() != null) vehicle.setFiscalPower(vehicleRequest.getFiscalPower());
           if (vehicleRequest.getModel() != null) vehicle.setModel(vehicleRequest.getModel());
           if (vehicleRequest.getRegNumber() != null) vehicle.setRegNumber(vehicleRequest.getRegNumber());
           return vehicleRepository.save(vehicle);
        }
        else return null;
    }

    // - Delete
    @DeleteMapping("/vehicles/{id}")
    public boolean deleteVehicle(@PathVariable("id") Long id){
        if(vehicleRepository.existsById(id)){
            vehicleRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    @PostMapping("/vehicles/{vehicleId}/owners/{ownerId}")
    public Vehicle addVehicleToOwner(@PathVariable Long vehicleId,@PathVariable Long ownerId) {
        if(vehicleRepository.existsById(vehicleId) && ownerRepository.existsById(ownerId)){
            Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
            vehicle.setOwner(ownerRepository.findById(ownerId).get());
            return vehicleRepository.save(vehicle);
        }
        else return null;
    }
}
