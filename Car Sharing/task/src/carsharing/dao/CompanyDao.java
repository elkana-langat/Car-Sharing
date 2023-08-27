package carsharing.dao;

import carsharing.model.Company;

import java.util.List;

public interface CompanyDao {

    List<Company> findAll();

    Company findById(int id);

    void add(String name);

    void update(Company company);

    void deleteById(int id);
}
