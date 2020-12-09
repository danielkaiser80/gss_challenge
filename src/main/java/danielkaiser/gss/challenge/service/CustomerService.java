package danielkaiser.gss.challenge.service;

import danielkaiser.gss.challenge.controller.dto.CustomerCreationDto;
import danielkaiser.gss.challenge.domain.Customer;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CustomerService {

    public Customer createCustomer(CustomerCreationDto customerCreationDto) {
        // do nothing for now
        log.info("Customer shall be created from {}", customerCreationDto);

        // TODO replace with DTO or something the like
        return new Customer() {};
    }

}
