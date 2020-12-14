package danielkaiser.gss.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import danielkaiser.gss.challenge.ChallengeApplication;
import danielkaiser.gss.challenge.controller.dto.CustomerCreationDto;
import danielkaiser.gss.challenge.data.CustomerRepository;
import danielkaiser.gss.challenge.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasLength;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ChallengeApplication.class)
@WebAppConfiguration
@Transactional
@Rollback
class CustomerResourceIT {

    private static final String URI = "/api/customers";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CustomerRepository customerRepository;

    private MockMvc mvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = Jackson2ObjectMapperBuilder.json().build();
    }

    @Test
    void shouldCreateNewCustomer() throws Exception {

        final CustomerCreationDto dto = CustomerCreationDto.builder()
                .firstName("Daniel")
                .lastName("Kaiser")
                .dateOfBirth(LocalDate.of(1980, 8, 4))
                .inceptionOfPolicy(LocalDate.now())
                .build();

        final byte[] inputJson = objectMapper.writeValueAsBytes(dto);

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

    @Test
    void shouldThrowExceptionOnCreateWithMissingField() throws Exception {

        final CustomerCreationDto dto = CustomerCreationDto.builder()
                .firstName("Daniel")
                .lastName("Kaiser")
                .inceptionOfPolicy(LocalDate.now())
                .build();

        final byte[] inputJson = objectMapper.writeValueAsBytes(dto);

        mvc.perform(MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRetrieveExistingCustomer() throws Exception {

        // get the insurance number for Thomas Danzig
        final String thomas_danzig = "Thomas Danzig";
        final String insuranceNumber = customerRepository.findByName(thomas_danzig).map(Customer::getInsuranceNumber).orElseThrow();

        mvc.perform(MockMvcRequestBuilders.get(URI + "/" + insuranceNumber)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(equalTo(thomas_danzig)))
                .andExpect(jsonPath("$.insuranceNumber").value(equalTo(insuranceNumber)))
                .andExpect(jsonPath("$.dateOfBirth").value(equalTo("1990-09-13")))
                .andExpect(jsonPath("$.inceptionOfPolicy").value(equalTo("2010-01-01")))
                .andExpect(jsonPath("$.paymentRate").value(equalTo(270.0)));
    }

    @Test
    void shouldNotRetrieveCustomerWithWrongInsuranceNumber() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URI + "/fakeNumber")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

}
