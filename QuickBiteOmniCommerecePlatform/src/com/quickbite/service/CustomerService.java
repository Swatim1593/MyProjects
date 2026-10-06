package com.quickbite.service;

import com.quickbite.dao.impl.CustomerDAOImpl;
import com.quickbite.entity.Customer;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {
    private CustomerDAOImpl customerDAO;

    public CustomerService(CustomerDAOImpl customerDAO) { this.customerDAO = customerDAO; }

    public void registerCustomer(Customer customer) { customerDAO.save(customer); }
    public Customer getCustomer(int id) { return customerDAO.findById(id); }

    public List<Customer> getPremiumCustomers() {
        return customerDAO.findAll().stream()
                .filter(Customer::isPremium)
                .collect(Collectors.toList());
    }
}
