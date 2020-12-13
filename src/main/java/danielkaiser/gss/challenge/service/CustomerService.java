package danielkaiser.gss.challenge.service;

import danielkaiser.gss.challenge.controller.dto.CustomerCreationDto;
import danielkaiser.gss.challenge.controller.dto.CustomerDto;
import danielkaiser.gss.challenge.data.CustomerRepository;
import danielkaiser.gss.challenge.domain.Customer;
import danielkaiser.gss.challenge.mapper.CustomerMapper;
import danielkaiser.gss.challenge.util.GenerateShortUuid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private static final int BASE_RATE = 300;

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final Clock clock;

    public CustomerDto createCustomer(final CustomerCreationDto customerCreationDto) {
        log.info("Creating customer from {}", customerCreationDto);

        // get all insurance numbers first, so that we can check, that we do not create one which already exists
        final Set<String> insuranceNumbers = customerRepository.findAll().stream().map(Customer::getInsuranceNumber).collect(Collectors.toUnmodifiableSet());

        final String newInsuranceNumber = generateNewInsuranceNumber(insuranceNumbers);

        final Customer newCustomer = customerMapper.toEntityBuilder(customerCreationDto).insuranceNumber(newInsuranceNumber).build();
        return customerMapper.toDto(customerRepository.save(newCustomer));
    }

    private String generateNewInsuranceNumber(final Set<String> insuranceNumbers) {
        String generated;
        do {
            generated = GenerateShortUuid.generate(8);
        } while (insuranceNumbers.contains(generated));
        return generated;
    }

    BigDecimal calculateRateForCustomer(final Customer customer) {

        final int ageOfCustomer = calculateAgeOfCustomer(customer.getDateOfBirth());
        final int percentage = getAgeFactor(ageOfCustomer);

        final int fullYearsSinceInception = calculateFullYearsSinceInception(customer.getInceptionOfPolicy());

        return BigDecimal.valueOf(percentage * BASE_RATE / 100.0 * (1 - fullYearsSinceInception / 100.0));
    }

    int calculateAgeOfCustomer(final LocalDate dateOfBirth) {
        final LocalDate now = LocalDate.now(clock);

        // if birth date is greater then current date, then do not count this month
        int correctedMonth = now.getMonthValue() - ((dateOfBirth.getDayOfMonth() > now.getDayOfMonth()) ? 1 : 0);
        return calculateYearsBetween(dateOfBirth, correctedMonth, now.getYear());
    }

    int getAgeFactor(final int ageOfCustomer) {
        if (ageOfCustomer < 0) {
            throw new IllegalStateException("Customer with negative age is not possible");
        }
        if (ageOfCustomer < 12) {
            return 50;
        }
        if (ageOfCustomer < 21) {
            return 75;
        }
        if (ageOfCustomer < 45) {
            return 100;
        }
        if (ageOfCustomer < 67) {
            return 125;
        }
        return 0;
    }

    int calculateFullYearsSinceInception(final LocalDate inceptionDate) {
        final LocalDate now = LocalDate.now(clock);

        // according to the requirements specification, the discount is even then applied, when the inception date is on the last day of the month
        // this means, the day of the date is not relevant for this calculation; for the month and year it is actually like calculating the birthdate
        return calculateYearsBetween(inceptionDate, now.getMonthValue(), now.getYear());
    }

    private int calculateYearsBetween(final LocalDate startDate, final int correctedMonthValue, final int currentYear) {
        // if the birth / inception month exceeds current month, then do not count this year
        int correctedYear = currentYear - ((startDate.getMonthValue() > correctedMonthValue) ? 1 : 0);
        return correctedYear - startDate.getYear();
    }
}
