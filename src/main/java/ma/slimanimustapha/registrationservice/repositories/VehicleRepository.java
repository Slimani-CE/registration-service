package ma.slimanimustapha.registrationservice.repositories;

import ma.slimanimustapha.registrationservice.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

}
