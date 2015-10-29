package employee.model;

import employee.events.NewEmployee;

import java.util.ArrayList;

/**
 * Created by luisburgos on 26/10/15.
 */
public class Employees extends Model {

    private static Employees employees;

    private ArrayList<Employee> candidates;

    private Employees(){
        super();
    }

    public synchronized static Employees getInstance(){
        if(employees == null){
            employees = new Employees();
        }
        return employees;
    }

    /**
     * Inserts a new employee entry to the database and if the operation success then
     * store the object on the program's cache
     * @param employee
     */
    public void addNewEmployeeToDatabase(Employee employee){
        
        EmployeeDAO dao = new EmployeeDAO();
        if(dao.insert(employee)){
            addNewEmployeeToCache(dao.getLastEmployeeAdded());
        }else {
            System.out.println("Error al agregar a base de datos");
        }
        
    }

    /**
     * Uses the EmployeeCacheManager instance to store a new employee to the cache region
     * @param employee
     */
    private void addNewEmployeeToCache(Employee employee) {
        EmployeeCacheManager.getManager().addEmployee(employee);
        notify(new NewEmployee());
    }

    /**
     * @return an ArrayList representing all entries on the table EMPLOYEE from database
     */
    public ArrayList<Employee> getAllEmployees(){
        EmployeeDAO dao = new EmployeeDAO();
        return dao.getAllEmployees();
    }

}
