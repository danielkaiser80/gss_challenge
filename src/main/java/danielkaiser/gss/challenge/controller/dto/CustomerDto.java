package danielkaiser.gss.challenge.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record CustomerDto(
  @Schema(description = "The database ID", example = "1") Long id,

  @Schema(
    description = "The full name of the customer",
    example = "Daniel Kaiser",
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  String name,

  @Length(min = 8, max = 8)
  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  String insuranceNumber,

  @Schema(
    example = "1980-08-04",
    requiredMode = Schema.RequiredMode.REQUIRED,
    description = "The birth date of the customer"
  )
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  LocalDate dateOfBirth,

  @Schema(
    example = "2010-01-01",
    requiredMode = Schema.RequiredMode.REQUIRED,
    description = "The date of the inception of the contract"
  )
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  LocalDate inceptionOfPolicy,

  @Schema(
    description = "The monthly payment amount in Euro.",
    example = "270",
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  BigDecimal paymentRate
) {}
