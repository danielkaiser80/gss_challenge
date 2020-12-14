package danielkaiser.gss.challenge.controller.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerCreatedDto {
    Long id;
    String insuranceNumber;
}
