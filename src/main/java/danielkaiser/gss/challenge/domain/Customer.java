package danielkaiser.gss.challenge.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class Customer {

    @Id
    private Long id;

    private String name;

    private String insuranceNumber;

    private LocalDate dateOfBirth;

    private LocalDate inceptionOfPolicy;

}
