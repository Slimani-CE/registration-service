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
    // - Create
    @GetMapping("/vehicles")
    public List<Vehicle> getVehicles(){
        return vehicleRepository.findAll();
    }

    // - Read
    @GetMapping("/vehicles/{id}")
    public Vehicle getVehicleById(@PathVariable("id") Long id){
        return vehicleRepository.findById(id).get();
    }

    // - Update
    @PostMapping("/vehicles")
    public Vehicle saveVehicle(@RequestBody Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }

    // - Update
    @DeleteMapping("/vehicles/{id}")
    public Vehicle updateVehicle(@PathVariable("id") Long id,@RequestBody Vehicle vehicle){
        vehicle.setId(id);
        return vehicleRepository.save(vehicle);
    }

    // - Delete
    @PutMapping("/vehicles/{id}")
    public void deleteVehicle(@PathVariable("id") Long id){
        vehicleRepository.deleteById(id);
    }

}
