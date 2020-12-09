package danielkaiser.gss.challenge.mapper;

import danielkaiser.gss.challenge.controller.dto.CustomerCreationDto;
import danielkaiser.gss.challenge.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(expression = "java( dto.getFirstName() + dto.getLastName() )", target = "name")
    Customer.CustomerBuilder toEntityBuilder(CustomerCreationDto dto);

    default Customer.CustomerBuilder builder() {
        return Customer.builder();
    }
}
