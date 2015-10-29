package employee.model;

import employee.database.DatabaseSource;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by luisburgos on 28/10/15.
 */
public class EmployeeDAO {

    private DatabaseSource db = DatabaseSource.getDatabaseSource();
    private Connection con;

    public EmployeeDAO() {
        db = DatabaseSource.getDatabaseSource();
    }

    public boolean insert(Employee employee){

        con = db.openConnection();
        try {
            PreparedStatement prep = con.prepareStatement(
                    "INSERT into EMPLOYEE (NAME, EMAIL, ADDRESS) values (?, ?, ?);"
            );

            prep.setString(1, employee.getName());
            prep.setString(2, employee.getEmail());
            prep.setString(3, employee.getAddress());
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

    public Employee getEmployeeById(int id){
        throw new UnsupportedOperationException();
    }

    public ArrayList<Employee> getAllEmployees(){
        ArrayList<Employee> allEmployeesInDatabase = new ArrayList<Employee>();

        Statement stat = null;
        con = db.openConnection();
        try {
            stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * from EMPLOYEE;");
            while (rs.next()) {
                allEmployeesInDatabase.add(
                        new Employee(
                                rs.getInt("ID_EMPLOYEE"),
                                rs.getString("NAME"),
                                rs.getString("EMAIL"),
                                rs.getString("ADDRESS")
                        )
                );
            }
            rs.close();
            con.close();
            db.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allEmployeesInDatabase;
    }

    public Employee getLastEmployeeAdded(){
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
