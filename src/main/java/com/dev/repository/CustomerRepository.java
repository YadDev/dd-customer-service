package com.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {


}
