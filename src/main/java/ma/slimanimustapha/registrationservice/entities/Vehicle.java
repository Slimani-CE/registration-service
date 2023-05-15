package ma.slimanimustapha.registrationservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor  @ToString
@NoArgsConstructor @Builder
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String regNumber;
    private String brand;
    private Double fiscalPower;
    private String model;
    @ManyToOne
    private Owner owner;
}
