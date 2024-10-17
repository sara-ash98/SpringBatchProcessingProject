package com.sara.Customer_batch_processing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sara.Customer_batch_processing.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,String>{

}
