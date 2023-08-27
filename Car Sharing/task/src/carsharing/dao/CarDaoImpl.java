package carsharing.dao;

import carsharing.model.Car;
import org.h2.jdbcx.JdbcDataSource;

import java.util.List;

public class CarDaoImpl implements CarDao {

    private static final String CREATE_CAR_TABLE = "CREATE TABLE IF NOT EXISTS CAR (" +
            "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(250) UNIQUE NOT NULL," +
            "COMPANY_ID INTEGER NOT NULL," +
            "CONSTRAINT company_id FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
            ");";
    private static final String SELECT_ALL = "SELECT * FROM CAR WHERE COMPANY_ID=%d";
    private static final String SELECT = "SELECT * FROM CAR WHERE ID = %d";
    private static final String SELECT_BY_NAME = "SELECT * FROM CAR WHERE NAME = '%s'";
    private static final String INSERT_DATA = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES ('%s', %d)";
    private static final String DELETE_DATA = "DELETE FROM CAR WHERE ID = %d";
    private static final String SELECT_VIEW_DATA = "SELECT * FROM CarsNotRented WHERE COMPANY_ID=%d";
    private static String CONNECTION_URL = "";
    private final DbClient dbClient;

    public CarDaoImpl() {
        //MysqlDataSource dataSource = new MysqlDataSource();
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(CONNECTION_URL);

        dbClient = new DbClient(dataSource);
        dbClient.run(CREATE_CAR_TABLE);
    }

    public static void setConnectionUrl(String url) {
        CONNECTION_URL = url;
    }

    @Override
    public void add(String name, int company_id) {
        dbClient.run(String.format(
                INSERT_DATA, name, company_id));
    }

    @Override
    public List<Car> findCarsNotRented(int id) {
        return dbClient.selectForListCars(String.format(
                SELECT_VIEW_DATA, id
        ));
    }

    @Override
    public List<Car> findAll(int id) {
        return dbClient.selectForListCars(String.format(
                SELECT_ALL, id
        ));
    }

    @Override
    public Car findById(int id) {

        return dbClient.selectCar(String.format(SELECT, id));
    }

    @Override
    public Car findByName(String carName) {
        return dbClient.selectCar(String.format(SELECT_BY_NAME, carName));
    }

    @Override
    public void deleteById(int id) {
        dbClient.run(String.format(DELETE_DATA, id));
    }
}
