package danielkaiser.gss.challenge.service;

import danielkaiser.gss.challenge.controller.dto.CustomerCreationDto;
import danielkaiser.gss.challenge.data.CustomerRepository;
import danielkaiser.gss.challenge.domain.Customer;
import danielkaiser.gss.challenge.mapper.CustomerMapper;
import danielkaiser.gss.challenge.util.GenerateShortUuid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public Customer createCustomer(final CustomerCreationDto customerCreationDto) {
        log.info("Creating customer from {}", customerCreationDto);

        // get all insurance numbers first, so that we can check, that we do not create one which already exists
        final Set<String> insuranceNumbers = customerRepository.findAll().stream().map(Customer::getInsuranceNumber).collect(Collectors.toUnmodifiableSet());

        final String newInsuranceNumber = generateNewInsuranceNumber(insuranceNumbers);

        final Customer newCustomer = customerMapper.toEntityBuilder(customerCreationDto).insuranceNumber(newInsuranceNumber).build();
        customerRepository.save(newCustomer);
        return newCustomer;
    }

    private String generateNewInsuranceNumber(final Set<String> insuranceNumbers) {
        String generated;
        do {
            generated = GenerateShortUuid.generate(8);
        } while (insuranceNumbers.contains(generated));
        return generated;
    }

}
