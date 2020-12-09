package danielkaiser.gss.challenge.controller;

import danielkaiser.gss.challenge.AbstractTest;
import danielkaiser.gss.challenge.controller.dto.CustomerCreationDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasLength;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerResourceTest extends AbstractTest {

    private static final String URI = "/api/customer";

    @Test
    void shouldCreateNewCustomer() throws Exception {

        final CustomerCreationDto dto = CustomerCreationDto.builder()
                .firstName("Daniel")
                .lastName("Kaiser")
                .dateOfBirth(LocalDate.of(1980, 8, 4))
                .inceptionOfPolicy(LocalDate.now())
                .build();

        final byte[] inputJson = super.convertObjectToJsonBytes(dto);

        mvc.perform(MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(hasLength(8)));


    }
}
