package employee.database;

import employee.model.entities.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manage transactions on the EMPLOYEE table of database.
 * Created by luisburgos on 28/10/15.
 */
public class EmployeeDAO extends DAO {

    public EmployeeDAO() {
        super();
    }

    /**
     * @return a HashMap representing all the entries of
     * EMPLOYEE table from database.
     */
    @Override
    public HashMap<Integer, Employee> getAll() {
        HashMap<Integer, Employee> allEmployeesInDatabase = new HashMap<>();

        Statement stat = null;
        con = db.openConnection();
        try {
            stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * from EMPLOYEE;");
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("ID_EMPLOYEE"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("ADDRESS")
                );
                allEmployeesInDatabase.put(employee.getId(), employee);
            }
            rs.close();
            con.close();
            db.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allEmployeesInDatabase;
    }

    /**
     * Insert a new employee entry to the EMPLOYEE table
     * @param entity
     * @return a Boolean depending on the success of the transaction
     */
    @Override
    public boolean insert(Object entity) {
        con = db.openConnection();
        try {
            PreparedStatement prep = con.prepareStatement(
                    "INSERT into EMPLOYEE (NAME, EMAIL, ADDRESS) values (?, ?, ?);"
            );

            prep.setString(1, ((Employee)entity).getName());
            prep.setString(2, ((Employee)entity).getEmail());
            prep.setString(3, ((Employee)entity).getAddress());
            prep.addBatch();

            con.setAutoCommit(false);
            prep.executeBatch();
            con.setAutoCommit(true);

            con.close();
            db.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param id of the employee to look at.
     * @return an Employee representing the entry for the specific id.
     */
    @Override
    public Employee getById(int id) {
        throw new UnsupportedOperationException();
    }

    /**
     * @return an Employee representing the last entry on
     * EMPLOYEE table from database.
     */
    @Override
    public Employee getLastAdded() {
        Employee lastEmployeeAdded = null;
        String query = "SELECT * from EMPLOYEE where ID_EMPLOYEE = (SELECT MAX(ID_EMPLOYEE) from EMPLOYEE)";

        Statement stat = null;
        con = db.openConnection();
        try {
            stat = con.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                lastEmployeeAdded =
                        new Employee(
                                rs.getInt("ID_EMPLOYEE"),
                                rs.getString("NAME"),
                                rs.getString("EMAIL"),
                                rs.getString("ADDRESS")
                        );
            }
            rs.close();
            con.close();
            db.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastEmployeeAdded;
    }
}
