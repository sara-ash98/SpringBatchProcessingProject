package com.sara.Customer_batch_processing.Config;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.sara.Customer_batch_processing.entity.Customer;

public class CustomFieldSetMapper implements FieldSetMapper<Customer> {
	@Override
public Customer mapFieldSet(FieldSet fieldSet){
	Customer customer=new Customer();
	customer.setIndex(fieldSet.readString("Index"));
	customer.setCustomerId(fieldSet.readString("Customer Id"));
	customer.setFirstName(fieldSet.readString("First Name"));
	customer.setLastName(fieldSet.readString("Last Name"));
	customer.setCompany(fieldSet.readString("Company"));
	customer.setCity(fieldSet.readString("City"));
	customer.setCountry(fieldSet.readString("Country"));
	customer.setPhone1(fieldSet.readString("Phone 1"));
	customer.setPhone2(fieldSet.readString("Phone 2"));
	customer.setEmail(fieldSet.readString("Email"));
	customer.setSubscriptionDate(fieldSet.readString("Subscription Date"));
	customer.setWebsite(fieldSet.readString("Website"));
	 return customer;
}
}
