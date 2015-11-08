package employee.model;

import employee.cache.CacheManager;
import employee.cache.RegionNotFoundException;
import employee.database.DAO;
import employee.database.EmployeeDAO;
import employee.model.entities.Employee;

import java.util.Collection;

/**
 * Created by luisburgos on 26/10/15.
 */
public class Employees extends Model {

    public static final String EMPLOYEE_CACHE_NAME = "employee";
    private static Employees employees;

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
        DAO dao = new EmployeeDAO();
        if(dao.insert(employee)){
            addNewEmployeeToCache((Employee) dao.getLastAdded());
        }else {
            System.out.println("Error al agregar a base de datos");
        }
    }

    /**
     * Uses the EmployeeCacheManager instance to store a new employee to the cache region
     * @param employee
     */
    private void addNewEmployeeToCache(Employee employee) {
        try {
            CacheManager
                    .getManager()
                    .addElementToRegion(
                            EMPLOYEE_CACHE_NAME,
                            employee.getId(),
                            employee
                    );
        } catch (RegionNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

}
