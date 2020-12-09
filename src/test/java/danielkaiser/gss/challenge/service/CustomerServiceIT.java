package danielkaiser.gss.challenge.service;

import danielkaiser.gss.challenge.data.CustomerRepository;
import danielkaiser.gss.challenge.domain.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerServiceIT {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;


    @Test
    void shouldCalculateCorrectBaseRateForGivenCustomer() {
        final Customer thomasDanzig = customerRepository.findByName("Thomas Danzig").orElseThrow();

        assertThat(thomasDanzig).isNotNull();

        final BigDecimal rateForCustomer = customerService.calculateRateForCustomer(thomasDanzig);

        // TODO here we actually expect a rate of 273EUR in 2020
        assertThat(rateForCustomer).isEqualTo(BigDecimal.ZERO);
    }




}
