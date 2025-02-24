package io.github.fontysvenlo.ais.restapi;

import io.javalin.http.Context;
import io.javalin.apibuilder.CrudHandler;

import io.github.fontysvenlo.ais.datarecords.CustomerData;
import io.github.fontysvenlo.ais.businesslogic.api.CustomerManager;

/**
 * This class is responsible for handling the requests for the customer resource.
 */
class CustomerResource implements CrudHandler {
    final private CustomerManager customerManager;

    /**
     * Initializes the controller with the business logic.
     */
    CustomerResource(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }

    /**
     * Adds a customer to the storage.
     * - If the customer data is null, the status is set to 400 (Bad Request).
     * - Otherwise, the status is set to 201 (Created) and the added customer is returned as JSON.
     */
    @Override
    public void create(Context context) {
        CustomerData customerData = context.bodyAsClass(CustomerData.class);
        if (customerData == null) {
            context.status(400);
            return;
        }
        context.status(201);
        context.json(customerManager.add(customerData));
    }

    /**
     * Retrieves all customers from the storage.
     * - The status is set to 200 (OK) and the list of customers is returned as JSON.
     */
    @Override
    public void getAll(Context context) {
        context.status(200);
        context.json(customerManager.list());
    }

    @Override
    public void delete(Context context, String customerId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void getOne(Context context, String customerId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void update(Context context, String customerId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}