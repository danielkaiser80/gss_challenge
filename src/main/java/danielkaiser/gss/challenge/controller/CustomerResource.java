package danielkaiser.gss.challenge.controller;

import danielkaiser.gss.challenge.controller.dto.CustomerCreatedDto;
import danielkaiser.gss.challenge.controller.dto.CustomerCreationDto;
import danielkaiser.gss.challenge.controller.dto.CustomerDto;
import danielkaiser.gss.challenge.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
     * POST  /api/customer : create a new customer and return his insurance number.
     *
     * @param customerCreationDto the customer information we want to create
     * @return the insurance number, which was assigned to the new customer
     */
    @PostMapping
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerCreationDto customerCreationDto) throws URISyntaxException {
        final CustomerCreatedDto result = customerService.createCustomer(customerCreationDto);
        return ResponseEntity.created(new URI("/api/customer/" + result.getId()))
                .body(result.getInsuranceNumber());
    }

    /**
     * GET  /api/customer : retrieve a customer via his insurance number.
     *
     * @param insuranceNumber the insurance number
     * @return 200 and the customer with payment rate; or 404 if customer not found
     */
    @GetMapping(value="{insuranceNumber}")
    public ResponseEntity<CustomerDto> findCustomer( @PathVariable @NotNull String insuranceNumber) {
        return ResponseEntity.of(customerService.findCustomer(insuranceNumber));
    }

    // TODO add retrieval method for all customers with payments

}
