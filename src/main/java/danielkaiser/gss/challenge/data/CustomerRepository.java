package danielkaiser.gss.challenge.data;

import danielkaiser.gss.challenge.domain.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findByName(String name);

  Optional<Customer> findByInsuranceNumber(String insuranceNumber);
}
