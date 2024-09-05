package com.amgocode.amigocode;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")

public class AmigocodeApplication {
       
	private final CustomerRepository customerRepository;

	public AmigocodeApplication(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AmigocodeApplication.class, args);

	}

	// For Geting the All Customers [GET METHOD ]
	@GetMapping
	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	// For Creating The Customer [POST

	record NewCustomerRequest(
			String name,
			String email,
			Integer age) {
	}

	@PostMapping
	public void addCustomer(@RequestBody NewCustomerRequest request) {
		Customer customer = new Customer();
		customer.setAge(request.age);
		customer.setEmail(request.email);
		customer.setName(request.name);

		customerRepository.save(customer);

	}

	// Delete the Customer [ DELETE METHOD]
	@DeleteMapping("{customerId}")
	public void deleteCustomer(@PathVariable("customerId") Integer id) {
		customerRepository.deleteById(id);
	}

	// Updating the Customer [PUT Request]
	@PutMapping("/{customerId}")
	public void updateCustomer(
			@PathVariable("customerId") Integer id,
			@RequestBody Customer updatedCustomer) {
		Optional<Customer> existingCustomerOptional = customerRepository.findById(id);

		if (existingCustomerOptional.isPresent()) {
			Customer existingCustomer = existingCustomerOptional.get();
			existingCustomer.setAge(updatedCustomer.getAge());
			existingCustomer.setEmail(updatedCustomer.getEmail());
			existingCustomer.setName(updatedCustomer.getName());
			customerRepository.save(existingCustomer);
		}
	}

}
