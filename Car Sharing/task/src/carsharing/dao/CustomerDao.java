package carsharing.dao;

import carsharing.model.Customer;

import java.util.List;

public interface CustomerDao {

    List<Customer> findAll();

    Customer findById(int id);

    void add(String name);

    void update(int rented_car_id, int customer_id);

    void deleteById(int id);

    String getRentedName(int car_id);

    String getRentedCarCompany(int car_id);

    void updateNull(int customer_id);
}
