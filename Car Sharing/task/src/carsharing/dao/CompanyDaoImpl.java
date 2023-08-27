package carsharing.dao;

import carsharing.model.Company;
import org.h2.jdbcx.JdbcDataSource;

import java.util.List;

public class CompanyDaoImpl implements CompanyDao {

    private static final String CREATE_DB = "CREATE TABLE IF NOT EXISTS COMPANY(" +
            "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR(250) UNIQUE NOT NULL);";
    private static final String SELECT_ALL = "SELECT * FROM COMPANY ORDER BY ID";
    private static final String SELECT = "SELECT * FROM COMPANY WHERE ID = %d";
    private static final String INSERT_DATA = "INSERT INTO COMPANY (NAME) VALUES ('%s')";
    private static final String UPDATE_DATA = "UPDATE COMPANY SET NAME " +
            "= '%s' WHERE ID = %d";
    private static final String DELETE_DATA = "DELETE FROM COMPANY WHERE ID = %d";
    private static String CONNECTION_URL = "";
    private final DbClient dbClient;

    public CompanyDaoImpl() {
        //MysqlDataSource dataSource = new MysqlDataSource();
        JdbcDataSource dataSource = new JdbcDataSource();

        dataSource.setUrl(CONNECTION_URL);

        dbClient = new DbClient(dataSource);
        dbClient.run(CREATE_DB);
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
    public List<Company> findAll() {
        return dbClient.selectForList(SELECT_ALL);
    }

    @Override
    public Company findById(int id) {

        return dbClient.select(String.format(SELECT, id));
    }

    @Override
    public void update(Company company) {
        dbClient.run(String.format(
                UPDATE_DATA, company.getName(), company.getId()));
    }

    @Override
    public void deleteById(int id) {
        dbClient.run(String.format(DELETE_DATA, id));
    }
}
