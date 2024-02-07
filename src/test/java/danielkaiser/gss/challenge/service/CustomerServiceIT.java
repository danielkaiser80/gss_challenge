package danielkaiser.gss.challenge.service;

import static org.assertj.core.api.Assertions.assertThat;

import danielkaiser.gss.challenge.data.CustomerRepository;
import danielkaiser.gss.challenge.domain.Customer;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerServiceIT {

  @Autowired
  private CustomerService customerService;

  @Autowired
  private CustomerRepository customerRepository;

  @Test
  void shouldCalculateCorrectBaseRateForGivenCustomer() {
    final Customer thomasDanzig = customerRepository
      .findByName("Thomas Danzig")
      .orElseThrow();
    assertThat(thomasDanzig).isNotNull();

    final BigDecimal rateForCustomer = customerService.calculateRateForCustomer(
      thomasDanzig
    );

    // According to the specification, Mr Danzig should have a rate of 273 € -- but as he actually has already finished ten years (and the specifications were written earlier), this is corrected here
    assertThat(rateForCustomer).isEqualTo(BigDecimal.valueOf(264.0));
  }
}
