package ma.slimanimustapha.registrationservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor @Builder
@NoArgsConstructor @ToString
public class Owner {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String birthDate;
    private String email;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Vehicle> vehicles;
}
