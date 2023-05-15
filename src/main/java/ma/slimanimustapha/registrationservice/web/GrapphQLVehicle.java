package ma.slimanimustapha.registrationservice.web;

import ma.slimanimustapha.registrationservice.entities.Vehicle;
import ma.slimanimustapha.registrationservice.repositories.VehicleRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GrapphQLVehicle {
    private final VehicleRepository vehicleRepository;

    public GrapphQLVehicle(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
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


}
