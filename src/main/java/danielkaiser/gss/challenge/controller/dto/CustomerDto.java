package danielkaiser.gss.challenge.controller.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class CustomerDto {
    Long id;
    String name;
    String insuranceNumber;
    LocalDate dateOfBirth;
    LocalDate inceptionOfPolicy;
}
