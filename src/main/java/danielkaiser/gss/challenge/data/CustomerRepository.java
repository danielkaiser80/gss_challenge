package danielkaiser.gss.challenge.data;

import danielkaiser.gss.challenge.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
