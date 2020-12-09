package danielkaiser.gss.challenge.controller;

import danielkaiser.gss.challenge.controller.dto.CustomerCreationDto;
import danielkaiser.gss.challenge.domain.Customer;
import danielkaiser.gss.challenge.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for managing customers.
 */
@RestController
@RequestMapping(value = "/api/customer", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Log4j2
public class CustomerResource {

    private final CustomerService customerService;

    /**
     * POST  /api/ccustomer : create a new customer.
     *
     * @param customerCreationDto the customer information we want to create
     * @return the insurance number, which was assigned to the new customer
     */
    @PostMapping
    public ResponseEntity<Customer> registerClient(@Valid @RequestBody CustomerCreationDto customerCreationDto) throws URISyntaxException {
        final Customer result = customerService.createCustomer(customerCreationDto);
        return ResponseEntity.created(new URI("/api/customer/" + result.getId()))
                .body(result);
    }

}
