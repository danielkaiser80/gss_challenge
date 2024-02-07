package danielkaiser.gss.challenge.controller.dto;

import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record CustomerCreatedDto(
  Long id,
  @Length(min = 8, max = 8) String insuranceNumber
) {}
