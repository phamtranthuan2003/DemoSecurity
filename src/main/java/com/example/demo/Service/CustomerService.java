package com.example.demo.Service;

import java.util.List;
import com.example.demo.Entity.Customer;

public interface CustomerService {

    List<Customer> findAll();
    Customer findById(Long id);
    void save(Customer customer);
    void delete(Long id);
}
