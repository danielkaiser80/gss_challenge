package danielkaiser.gss.challenge.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

/**
 * A DTO just representing an insurance number.
 */
public record InsuranceNumberDto(
  @Length(min = 8, max = 8)
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  String insuranceNumber
) {
  public static InsuranceNumberDto of(String insuranceNumber) {
    return new InsuranceNumberDto(insuranceNumber);
  }
}
