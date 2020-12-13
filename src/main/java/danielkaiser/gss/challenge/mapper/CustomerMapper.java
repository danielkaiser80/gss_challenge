package danielkaiser.gss.challenge.mapper;

import danielkaiser.gss.challenge.controller.dto.CustomerCreationDto;
import danielkaiser.gss.challenge.controller.dto.CustomerDto;
import danielkaiser.gss.challenge.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(expression = "java( dto.getFirstName() + dto.getLastName() )", target = "name")
    @Mapping(target = "insuranceNumber", ignore = true)
    @Mapping(target = "id", ignore = true)
    Customer.CustomerBuilder toEntityBuilder(CustomerCreationDto dto);

    CustomerDto toDto(Customer customer);

    default Customer.CustomerBuilder builder() {
        return Customer.builder();
    }
}
