package com.dev.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.constants.CommonConstants;
import com.dev.entities.Customer;
import com.dev.entities.User;
import com.dev.model.ResponseHandler;
import com.dev.repository.CustomerRepository;
import com.dev.repository.UserRepository;
import com.dev.services.CustomerService;
import com.dev.utils.JwtUtil;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository custRepo;

	@Autowired
	private UserRepository userRepository;

	private final JwtUtil jwtUtil;

	@Autowired
	PasswordEncoder encoder;
	public CustomerServiceImpl(CustomerRepository custRepo, UserRepository userRepository, JwtUtil jwtUtil) {
		this.custRepo = custRepo;
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
	}


	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> customers = new ArrayList<>();
		System.out.println("In Customer Service Impl: getAllCustomer");
		customers = custRepo.findAll();
		return customers;
	}

	@Override
	public Customer getCustomer(String id) {
		Optional<Customer> cust = custRepo.findById(id);
		System.out.println("In Customer Service Impl: getCustomer");
		if(cust.isPresent()){
			return cust.get();
		}else {
			return null;
		}
	}

	@Override
	public Customer createCustomer(Customer customer) {
		System.out.println("Entered Customer Detail  saved in database ");
		customer = custRepo.save(customer);
		return customer;
	}

	@Override
	public Customer removeCustomer(String id) {
		System.out.println("Customer removed from database"+id);
		custRepo.deleteById(id);
		return null;
	}

	@Override
	public ResponseEntity<?> customerPasswordAuthentication(User user) {
		String token=null;
		try {
			Boolean customerExist = userRepository.existsByEmail(user.getEmail()) || userRepository.existsByUsername(user.getUsername());
			if(customerExist){
				User dbUser = userRepository.findByUsername(user.getUsername()).get();

				//System.out.println("Entered Password "+enteredPassword+"\t DB Password "+dbUser.getPassword());
				if(encoder.matches(user.getPassword(),dbUser.getPassword())){
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern(CommonConstants.DATE_TIME_FORMAT);
					LocalDateTime localTime = LocalDateTime.now();
					String loginTime 	= dtf.format(localTime);
					token = jwtUtil.generateToken(dbUser);
					user.setLastLoginAt(loginTime);

				}else{
					return ResponseHandler.generateResponse(
							"Username or Password is mismatch.",
							HttpStatus.UNAUTHORIZED,
							null
					);
				}
			}else {
				return ResponseHandler.generateResponse(
						"User does not exist!",
						HttpStatus.BAD_REQUEST,
						null
				);
			}
			return ResponseHandler.generateResponse(
					"User Authenticated Successfully!",
					HttpStatus.OK,
					token);
		}
		catch (Exception e){
			return ResponseHandler.generateResponse(
					"User Authentication Failed !"+e.getMessage(),
					HttpStatus.BAD_REQUEST,
					null);
		}
	}
}
