package danielkaiser.gss.challenge.controller.dto;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

/**
 * A DTO just representing an insurance number.
 */
@Value(staticConstructor = "of")
public class InsuranceNumberDto {
    @Length(min = 8, max = 8)
    String insuranceNumber;
}
