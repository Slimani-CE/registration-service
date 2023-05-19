package ma.slimanimustapha.registrationservice;

import ma.slimanimustapha.registrationservice.entities.Owner;
import ma.slimanimustapha.registrationservice.web.RestOwnerController;
import ma.slimanimustapha.registrationservice.web.RestVehicleController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class RegistrationServiceApplication implements CommandLineRunner {

    private final RestOwnerController restOwnerController;
    private final RestVehicleController restVehicleController;

    public RegistrationServiceApplication(RestOwnerController restOwnerController, RestVehicleController restVehicleController) {
        this.restOwnerController = restOwnerController;
        this.restVehicleController = restVehicleController;
    }

    public static void main(String[] args) {
        SpringApplication.run(RegistrationServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        // Create owners
        Stream.of("Mustapha", "Ahmed", "oussama", "hind", "sanae").forEach(name -> {
            Owner owner = Owner.builder()
                    .id(null)
                    .name(name)
                    .birthDate(new Date().toString())
                    .email(name + "@gmail.com")
                    .build();
            restOwnerController.saveOwner(owner);
        });

        // Create vehicles and link them with owners
        restOwnerController.getOwners().forEach(owner -> {
            Stream.of("Clio", "Golf", "Audi", "Mercedes", "BMW").forEach(brand -> {
                restVehicleController.saveVehicle(
                        ma.slimanimustapha.registrationservice.entities.Vehicle.builder()
                                .id(null)
                                .regNumber("ABC-" + Math.random() * 10000)
                                .brand(brand)
                                .fiscalPower((float)(Math.random() * 10))
                                .model("model " + Math.random() * 100)
                                .owner(owner)
                                .build()
                );
            });
        });
    }
}
