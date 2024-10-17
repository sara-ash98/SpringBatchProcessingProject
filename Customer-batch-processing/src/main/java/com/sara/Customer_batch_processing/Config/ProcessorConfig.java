package com.sara.Customer_batch_processing.Config;

import org.springframework.batch.item.ItemProcessor;

import com.sara.Customer_batch_processing.entity.Customer;

public class ProcessorConfig implements ItemProcessor<Customer,Customer> {
@Override
public Customer process(Customer customer) throws Exception{
	return customer;
}
}
