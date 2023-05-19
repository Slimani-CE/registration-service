package ma.slimanimustapha.registrationservice.web;

import ma.slimanimustapha.registrationservice.entities.Vehicle;
import ma.slimanimustapha.registrationservice.repositories.VehicleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web")
public class RestVehicleController {
    private final VehicleRepository vehicleRepository;

    public RestVehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
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
        return vehicleRepository.findById(id).get();
    }

    // - Update
    @PostMapping("/vehicles")
    public Vehicle saveVehicle(@RequestBody Vehicle vehicleRequest){
        return vehicleRepository.save(vehicleRequest);
    }

    // - Update
    @PutMapping("/vehicles/{id}")
    public Vehicle updateVehicle(@PathVariable("id") Long id,@RequestBody Vehicle vehicleRequest){
        vehicleRequest.setId(id);
        return vehicleRepository.save(vehicleRequest);
    }

    // - Delete
    @DeleteMapping("/vehicles/{id}")
    public void deleteVehicle(@PathVariable("id") Long id){
        vehicleRepository.deleteById(id);
    }

}
