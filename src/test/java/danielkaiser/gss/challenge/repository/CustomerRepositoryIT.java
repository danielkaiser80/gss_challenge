package danielkaiser.gss.challenge.repository;

import static org.assertj.core.api.Assertions.assertThat;

import danielkaiser.gss.challenge.data.CustomerRepository;
import danielkaiser.gss.challenge.domain.Customer;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerRepositoryIT {

  @Autowired
  private CustomerRepository repository;

  @Test
  void shouldLoadCustomersFromCsv() {
    final List<Customer> customers = repository.findAll();
    assertThat(customers).hasSizeGreaterThanOrEqualTo(5); // five from liquibase and maybe more from another use of the app

    // all customer numbers have a length of 8 chars
    assertThat(customers)
      .extracting(Customer::getInsuranceNumber)
      .extracting(String::length)
      .containsOnly(8);
  }
}
