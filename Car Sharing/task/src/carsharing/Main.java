package carsharing;

import carsharing.dao.*;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Customer;

import java.util.List;
import java.util.Scanner;

import static java.sql.Types.NULL;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String DB_URL = "";
        //String DB_URL = "jdbc:mysql://root@localhost:3306/company";

        if (args.length == 2) {
            DB_URL = String.format("jdbc:h2:./src/carsharing/db/%s", args[1]);
        } else {
            DB_URL = "jdbc:h2:./src/carsharing/db/company";
        }

        CompanyDaoImpl.setConnectionUrl(DB_URL);
        CarDaoImpl.setConnectionUrl(DB_URL);
        CustomerDaoImpl.setConnectionUrl(DB_URL);

        CompanyDao companyDao = new CompanyDaoImpl(); // Company data structure create
        CarDao carDao = new CarDaoImpl(); // car data structure create
        CustomerDao customerDao = new CustomerDaoImpl(); // customer data structure create
        mainMenu(companyDao, carDao, customerDao); // The starting point of the project
    }

    public static void mainMenu(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        System.out.println("""
                1. Log in as a manager
                2. Log in as a customer
                3. Create a customer
                0. Exit
                """);
        int choice = scanner.nextInt();

        if (choice == 1) {
            loginInAsManager(companyDao, carDao, customerDao);
        } else if (choice == 2) {
            loginAsCustomer(companyDao, carDao, customerDao);
        } else if (choice == 3) {
            createCustomer(companyDao, carDao, customerDao);
        } else if (choice == 0) {
            return;
        }
    }

    public static void loginAsCustomer(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        List<Customer> customers = customerDao.findAll();

        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            System.out.println();
            mainMenu(companyDao, carDao, customerDao);
        } else {
            System.out.println("Customer list:");
            for (Customer customer : customers) {
                String output = String.format("%d. %s", customer.getId(), customer.getName());
                System.out.println(output);
            }
            System.out.println("0. Back");

            int choice = scanner.nextInt();

            if (choice == 0) {
                mainMenu(companyDao, carDao, customerDao);
            } else {
                Customer customer = customerDao.findById(choice);
                String result = String.format("'%s' customer", customer.getName());
                System.out.println(result);
                customerMenu(companyDao, carDao, customerDao, choice);
            }
        }
        System.out.println();
        mainMenu(companyDao, carDao, customerDao);
    }

    public static void createCustomer(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        System.out.println("Enter the customer name:");
        scanner.nextLine();
        String customerName = scanner.nextLine().trim();

        // add the data
        customerDao.add(customerName);
        System.out.println("The customer was added!");
        System.out.println();
        mainMenu(companyDao, carDao, customerDao);
    }

    private static void loginInAsManager(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        System.out.println("""
                1. Company list
                2. Create a company
                0. Back
                """);

        int choice = scanner.nextInt();

        if (choice == 1) {
            companyList(companyDao, carDao, customerDao);
        } else if (choice == 2) {
            createCompany(companyDao, carDao, customerDao);
        } else if (choice == 0) {
            mainMenu(companyDao, carDao, customerDao);
        }

    }

    private static void createCompany(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        System.out.println("Enter the company name:");
        scanner.nextLine();
        String companyName = scanner.nextLine().trim();
        // add the data
        companyDao.add(companyName);
        System.out.println("The company was created!");
        System.out.println();
        loginInAsManager(companyDao, carDao, customerDao);
    }

    private static void companyList(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        List<Company> companies = companyDao.findAll();

        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Choose the company:");
            for (Company company : companies) {
                String output = String.format("%d. %s", company.getId(), company.getName());
                System.out.println(output);
            }
            System.out.println("0. Back");

            int choice = scanner.nextInt();

            if (choice == 0) {
                loginInAsManager(companyDao, carDao, customerDao);
            } else {
                Company company = companyDao.findById(choice);
                String result = String.format("'%s' company", company.getName());
                System.out.println(result);
                carMenu(companyDao, carDao, customerDao, choice);
            }
        }
        System.out.println();
        loginInAsManager(companyDao, carDao, customerDao);
    }

    private static void carList(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao, int index) {
        List<Car> cars = carDao.findAll(index);

        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            System.out.println();
            carMenu(companyDao, carDao, customerDao, index);
        } else {
            System.out.println("Car list:");

            int length = 1;

            for (Car car : cars) {
                String output = String.format("%d. %s", length, car.getName());
                length++;
                System.out.println(output);
            }
        }
        System.out.println();
        carMenu(companyDao, carDao, customerDao, index);
    }

    private static void createCar(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao, int index) {
        System.out.println("Enter the car name:");
        scanner.nextLine();
        String carName = scanner.nextLine().trim();
        carDao.add(carName, index);
        System.out.println("The car was added!");
        System.out.println();
        carMenu(companyDao, carDao, customerDao, index);
    }

    private static void carMenu(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao, int index) {
        System.out.println("""
                1. Car list
                2. Create a car
                0. Back
                """);

        int choice = scanner.nextInt();

        if (choice == 1) {
            carList(companyDao, carDao, customerDao, index);
        } else if (choice == 2) {
            createCar(companyDao, carDao, customerDao, index);
        } else if (choice == 0) {
            loginInAsManager(companyDao, carDao, customerDao);
        }

    }

    private static void customerMenu(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao, int customer_id) {
        System.out.println("""
                1. Rent a car
                2. Return a rented car
                3. My rented car
                0. Back
                """);

        int choice = scanner.nextInt();

        if (choice == 1) {
            rentCar(companyDao, carDao, customerDao, customer_id);
        } else if (choice == 2) {
            returnRentedCar(companyDao, carDao, customerDao, customer_id);
        } else if (choice == 3) {
            // check rented cars
            checkRentedCars(companyDao, carDao, customerDao, customer_id);
        } else if (choice == 0) {
            mainMenu(companyDao, carDao, customerDao);
        }
    }

    private static void rentCar(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao, int customer_id) {

        Customer customer = customerDao.findById(customer_id);
        if (customer.getRented_car_id() != NULL) {
            System.out.println("You've already rented a car!");
            customerMenu(companyDao, carDao, customerDao, customer_id);
        }
        companyListCustomer(companyDao, carDao, customerDao, customer_id);
    }


    private static void companyListCustomer(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao, int customer_id) {
        List<Company> companies = companyDao.findAll();

        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Choose the company:");
            for (Company company : companies) {
                String output = String.format("%d. %s", company.getId(), company.getName());
                System.out.println(output);
            }
            System.out.println("0. Back");

            int company_id = scanner.nextInt();

            if (company_id == 0) {
                customerMenu(companyDao, carDao, customerDao, customer_id);
            } else {
                Company company = companyDao.findById(company_id);
                String result = String.format("'%s' company", company.getName());
                System.out.println(result);
                customerRentCar(companyDao, carDao, customerDao, company_id, customer_id);
            }
        }
        System.out.println();
        loginInAsManager(companyDao, carDao, customerDao);
    }

    private static void customerRentCar(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao, int company_id,
                                        int customer_id) {
        List<Car> cars = carDao.findCarsNotRented(company_id);

        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            System.out.println();
        } else {
            System.out.println("Choose a car:");

            int length = 1;

            for (Car car : cars) {
                String output = String.format("%d. %s", length, car.getName());
                length++;
                System.out.println(output);
            }

            int car_id = scanner.nextInt();
            if (car_id == 0) {
                companyListCustomer(companyDao, carDao, customerDao, customer_id);
            }
            Car car0 = cars.get(car_id - 1);
            String carName = car0.getName();
            Car car = carDao.findByName(carName);
            customerDao.update(car.getId(), customer_id);
            String fin = String.format("You rented '%s'", car.getName());
            System.out.println(fin);
            customerMenu(companyDao, carDao, customerDao, customer_id);
        }
        System.out.println();
    }

    public static void checkRentedCars(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao, int customer_id) {
        Customer customer = customerDao.findById(customer_id);
        if (customer.getRented_car_id() == NULL) {
            System.out.println("You didn't rent a car!");
            customerMenu(companyDao, carDao, customerDao, customer_id);
        }
        int rented_car_id = customer.getRented_car_id();
        String carName = customerDao.getRentedName(rented_car_id);
        System.out.println("Your rented car:");
        System.out.println(carName);
        checkRentedCarCompany(companyDao, carDao, customerDao, customer_id, rented_car_id);
    }

    public static void checkRentedCarCompany(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao, int customer_id, int car_id) {
        String carCompany = customerDao.getRentedCarCompany(car_id);
        System.out.println("Company:");
        System.out.println(carCompany);
        customerMenu(companyDao, carDao, customerDao, customer_id);
    }

    public static void returnRentedCar(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao, int customer_id) {
        Customer customer = customerDao.findById(customer_id);
        if (customer.getRented_car_id() == NULL) {
            System.out.println("You didn't rent a car!");
            customerMenu(companyDao, carDao, customerDao, customer_id);
        }

        customerDao.updateNull(customer_id);
        System.out.println("You've returned a rented car!");
        customerMenu(companyDao, carDao, customerDao, customer_id);
    }
}