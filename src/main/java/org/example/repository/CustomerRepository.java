package org.example.repository;

import org.example.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByPhoneNumber(String phoneNumber);

}

//package com.sweetbiteorders.repository;
//
//import com.sweetbiteorders.model.Customer;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//
//@Repository
//public interface CustomerRepository extends JpaRepository<Customer, Long> {
//    Optional<Customer> findByPhoneNumber(String phoneNumber); // Make sure this line exists
//}