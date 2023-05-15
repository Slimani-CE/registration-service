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
    private Date birthDate;
    private String email;
    @OneToMany(mappedBy = "owner")
    private List<Vehicle> vehicles;
}
