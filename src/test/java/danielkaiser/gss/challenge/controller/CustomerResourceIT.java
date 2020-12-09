package danielkaiser.gss.challenge.controller;

import danielkaiser.gss.challenge.AbstractIT;
import danielkaiser.gss.challenge.controller.dto.CustomerCreationDto;
import danielkaiser.gss.challenge.data.CustomerRepository;
import danielkaiser.gss.challenge.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasLength;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerResourceIT extends AbstractIT {

    private static final String URI = "/api/customer";

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldCreateNewCustomer() throws Exception {

        final CustomerCreationDto dto = CustomerCreationDto.builder()
                .firstName("Daniel")
                .lastName("Kaiser")
                .dateOfBirth(LocalDate.of(1980, 8, 4))
                .inceptionOfPolicy(LocalDate.now())
                .build();

        final byte[] inputJson = super.convertObjectToJsonBytes(dto);

        final MockHttpServletResponse response =
                mvc.perform(MockMvcRequestBuilders.post(URI)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$").value(hasLength(8)))
                        .andReturn()
                        .getResponse();

        final String location = response.getHeader("location");
        assertThat(location).startsWith(URI);

        final List<String> insuranceNumbers = customerRepository.findAll().stream().map(Customer::getInsuranceNumber).collect(Collectors.toUnmodifiableList());
        final Set<String> uniqueInsuranceNumbers = new HashSet<>(insuranceNumbers);

        // same size means, that the numbers are actually unique already and this is what we verify here
        assertThat(insuranceNumbers).hasSameSizeAs(uniqueInsuranceNumbers);
    }
}
