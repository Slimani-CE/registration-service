package ma.slimanimustapha.registrationservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.JSONArray;

@Entity
@Data
@AllArgsConstructor  @ToString
@NoArgsConstructor @Builder
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String regNumber;
    private String brand;
    private Float fiscalPower;
    private String model;
    @ManyToOne
    private Owner owner;
}
