package io.github.fontysvenlo.ais.persistence.api;

import io.github.fontysvenlo.ais.datarecords.CustomerData;
import java.util.List;

/**
 * Interface that describes all services offered by the CustomerRepository.
 */
public interface CustomerRepository {
    /**
     * Adds a customer to the storage.
     * @param customerData the customer to add
     * @return the added customer
     */
    CustomerData add( CustomerData customerData);

    /**
     * Retrieves all customers from the storage.
     * @return a list of all customers
     */
    List<CustomerData> getAll();
}
