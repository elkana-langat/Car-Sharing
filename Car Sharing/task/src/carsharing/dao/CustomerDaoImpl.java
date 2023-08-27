package carsharing.dao;

import carsharing.model.Customer;
import org.h2.jdbcx.JdbcDataSource;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    private static final String CREATE_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
            "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(250) UNIQUE NOT NULL," +
            "RENTED_CAR_ID INTEGER DEFAULT NULL," +
            "CONSTRAINT car_id FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)" +
            ");";
    private static final String SELECT_ALL = "SELECT * FROM CUSTOMER";
    private static final String SELECT = "SELECT * FROM CUSTOMER WHERE ID = %d";
    private static final String INSERT_DATA = "INSERT INTO CUSTOMER (NAME, RENTED_CAR_ID) VALUES ('%s', NULL)";
    private static final String UPDATE_CUSTOMER = "UPDATE CUSTOMER SET RENTED_CAR_ID=%d WHERE ID=%d";
    private static final String UPDATE_CUSTOMER_NULL = "UPDATE CUSTOMER SET RENTED_CAR_ID=NULL WHERE ID=%d";
    private static final String CREATE_VIEW = "CREATE VIEW IF NOT EXISTS CarCompanyView AS\n" +
            "SELECT\n" +
            "    C.ID AS CarID,\n" +
            "    C.NAME AS CarName,\n" +
            "    CO.NAME AS CompanyName\n" +
            "FROM\n" +
            "    CAR C\n" +
            "        JOIN\n" +
            "    COMPANY CO ON C.COMPANY_ID = CO.ID;";

    private static final String CREATE_CAR_VIEW = "CREATE VIEW IF NOT EXISTS CarsNotRented AS\n" +
            "SELECT c.*\n" +
            "FROM CAR c\n" +
            "         LEFT JOIN CUSTOMER cust ON c.ID = cust.RENTED_CAR_ID\n" +
            "WHERE cust.RENTED_CAR_ID IS NULL;";
    private static final String SELECT_CARNAME_FROM_VIEW = "SELECT CarName FROM CarCompanyView WHERE CarID=%d";
    private static final String SELECT_COMPANY_NAME = "SELECT CompanyName FROM CarCompanyView WHERE CarID=%d";

    private static final String DELETE_DATA = "DELETE FROM CUSTOMER WHERE ID = %d";
    private static String CONNECTION_URL = "";
    private final DbClient dbClient;

    public CustomerDaoImpl() {
        //MysqlDataSource dataSource = new MysqlDataSource();
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(CONNECTION_URL);

        dbClient = new DbClient(dataSource);
        dbClient.run(CREATE_CUSTOMER_TABLE);
        dbClient.run(CREATE_VIEW);
        dbClient.run(CREATE_CAR_VIEW);
    }

    public static void setConnectionUrl(String url) {
        CONNECTION_URL = url;
    }

    @Override
    public void add(String name) {
        dbClient.run(String.format(
                INSERT_DATA, name));
    }

    @Override
    public void update(int rented_car_id, int customer_id) {
        dbClient.run(String.format(
                UPDATE_CUSTOMER, rented_car_id, customer_id
        ));
    }

    @Override
    public void updateNull(int customer_id) {
        dbClient.run(String.format(
                UPDATE_CUSTOMER_NULL, customer_id
        ));
    }

    @Override
    public List<Customer> findAll() {
        return dbClient.selectForListCustomers(SELECT_ALL);
    }

    @Override
    public Customer findById(int id) {

        return dbClient.selectCustomer(String.format(SELECT, id));
    }

    @Override
    public String getRentedName(int car_id) {
        return dbClient.getCarNameFromView(String.format(
                SELECT_CARNAME_FROM_VIEW, car_id
        ));
    }

    @Override
    public String getRentedCarCompany(int car_id) {
        return dbClient.getCompanyNameFromView(String.format(
                SELECT_COMPANY_NAME, car_id
        ));
    }


    @Override
    public void deleteById(int id) {
        dbClient.run(String.format(DELETE_DATA, id));
    }
}
