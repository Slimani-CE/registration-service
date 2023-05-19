package ma.slimanimustapha.registrationservice.entities;

import lombok.*;

@Data @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class VehicleRequest {
    private String regNumber;
    private String brand;
    private Float fiscalPower;
    private String model;
    private OwnerRequest owner;
}