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
    private Double fiscalPower;
    private String model;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Owner owner;
}
