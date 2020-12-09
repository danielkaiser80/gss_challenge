package danielkaiser.gss.challenge.controller.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Value
@Jacksonized
@Builder
@ToString
public class CustomerCreationDto {
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    LocalDate inceptionOfPolicy;
}
