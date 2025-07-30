package com.graphql.federation.customer.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.graphql.federation.customer.model.Customer;

@Service
public class CustomerService {

	// Simulate database to avoid data collection using repository.
	private static final Map<Integer, Customer> CUSTOMERS_DB = new ConcurrentHashMap<>(Map.of(
		1, Customer.builder().id(1).firstName("Admin")
                            .build(),
		2, Customer.builder().id(2).firstName("User").build()
																						 )
	);

	public Collection<Customer> findAll() {
		return CUSTOMERS_DB.values();
	}

	public Customer addCustomer(String firstName) {
		var newCustomer = Customer.builder()
								  .id(generateId())
								  .firstName(firstName)
								  .build();
		CUSTOMERS_DB.put(newCustomer.getId(), newCustomer);
		return newCustomer;
	}

	private int generateId() {
		return CUSTOMERS_DB.size() + 1;
	}
}