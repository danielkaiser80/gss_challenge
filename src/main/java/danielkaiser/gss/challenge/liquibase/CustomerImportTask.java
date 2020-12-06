package danielkaiser.gss.challenge.liquibase;

import danielkaiser.gss.challenge.domain.Customer;

public class CustomerImportTask extends AbstractCsvImportTask<Customer> {

    @Override
    protected Class<Customer> getImportTypeClass() {
        return Customer.class;
    }

    @Override
    protected Object mapToEntityInstance(Customer customer) {
        return customer;
    }

}
