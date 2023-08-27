package carsharing.dao;

import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Customer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbClient {

    private final DataSource dataSource;

    public DbClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // create and update data in the database
    public void run(String str) {
        try (Connection connection = dataSource.getConnection(); // statement creation
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(str); // statement execution
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // selecting data in the database
    public Company select(String query) {
        List<Company> companies = selectForList(query);

        if (companies.size() == 1) {
            return companies.get(0);
        } else if (companies.size() == 0) {
            return null;
        } else {
            throw new IllegalStateException("Query returned more than one object");
        }
    }

    public List<Company> selectForList(String query) {
        List<Company> companies = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                Company company = new Company(id, name);
                companies.add(company);
            }

            return companies;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return companies;
    }

    // selecting data in the database
    public Car selectCar(String query) {
        List<Car> cars = selectForListCars(query);

        if (cars.size() == 1) {
            return cars.get(0);
        } else if (cars.isEmpty()) {
            return null;
        } else {
            throw new IllegalStateException("Query returned more than one object");
        }
    }

    public List<Car> selectForListCars(String query) {
        List<Car> cars = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int company_id = resultSet.getInt("COMPANY_ID");
                Car car = new Car(id, name, company_id);
                cars.add(car);
            }

            return cars;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return cars;
    }

    // selecting data in the database
    public Customer selectCustomer(String query) {
        List<Customer> customers = selectForListCustomers(query);

        if (customers.size() == 1) {
            return customers.get(0);
        } else if (customers.isEmpty()) {
            return null;
        } else {
            throw new IllegalStateException("Query returned more than one object");
        }
    }

    public String getCarNameFromView(String query) {
        String carName = "";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                carName = resultSet.getString("CarName");
            }

            return carName;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return carName;
    }

    public String getCompanyNameFromView(String query) {
        String companyName = "";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                companyName = resultSet.getString("CompanyName");
            }

            return companyName;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return companyName;
    }

    public List<Customer> selectForListCustomers(String query) {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int rented_car_id = resultSet.getInt("RENTED_CAR_ID");
                Customer customer = new Customer(id, name, rented_car_id);
                customers.add(customer);
            }

            return customers;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return customers;
    }
}
