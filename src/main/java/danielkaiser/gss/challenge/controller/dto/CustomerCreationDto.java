package danielkaiser.gss.challenge.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
public record CustomerCreationDto(
  @Schema(example = "Daniel", requiredMode = Schema.RequiredMode.REQUIRED)
  String firstName,

  @Schema(example = "Kaiser", requiredMode = Schema.RequiredMode.REQUIRED)
  String lastName,

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
  LocalDate inceptionOfPolicy
) {}
