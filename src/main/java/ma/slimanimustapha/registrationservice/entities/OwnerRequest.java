package ma.slimanimustapha.registrationservice.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class OwnerRequest {
    private String name;
    private String email;
    private String birthDate;
}
