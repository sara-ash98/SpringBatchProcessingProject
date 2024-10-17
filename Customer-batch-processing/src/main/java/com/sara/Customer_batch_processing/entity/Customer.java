package com.sara.Customer_batch_processing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CUSTOMER_INFO")
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
@Column(name="INDEXNO")
private String index;
@Id
@Column(name="CUSTOMER_ID")
private String customerId;
public String getIndex() {
	return index;
}
public void setIndex(String index) {
	this.index = index;
}
public String getCustomerId() {
	return customerId;
}
public void setCustomerId(String customerId) {
	this.customerId = customerId;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getCompany() {
	return company;
}
public void setCompany(String company) {
	this.company = company;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getPhone1() {
	return phone1;
}
public void setPhone1(String phone1) {
	this.phone1 = phone1;
}
public String getPhone2() {
	return phone2;
}
public void setPhone2(String phone2) {
	this.phone2 = phone2;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getSubscriptionDate() {
	return subscriptionDate;
}
public void setSubscriptionDate(String subscriptionDate) {
	this.subscriptionDate = subscriptionDate;
}
public String getWebsite() {
	return website;
}
public void setWebsite(String website) {
	this.website = website;
}
@Column(name="FIRST_NAME")
private String firstName;
@Column(name="LAST_NAME")
private String lastName;
@Column(name="COMPANY")
private String company;
@Column(name="CITY")
private String city;
@Column(name="COUNTRY")
private String country;
@Column(name="PHONE1")
private String phone1;
@Column(name="PHONE2")
private String phone2;
@Column(name="EMAIL")
private String email;
@Column(name="SUBSCRIPTION_DATA")
private String subscriptionDate;
@Column(name="WEBSITE")
private String website;
}
