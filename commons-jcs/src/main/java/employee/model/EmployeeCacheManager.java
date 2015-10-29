package employee.model;

import employee.events.Event;
import employee.events.EventTypes;
import employee.events.Startup;
import employee.misc.Observable;
import employee.misc.Observer;
import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.exception.CacheException;
import org.apache.commons.jcs.engine.control.CompositeCacheManager;

import java.util.ArrayList;
import java.util.Set;

/**
 * Manage the storage of objects in the cache
 * Created by luisburgos on 26/10/15.
 */
public class EmployeeCacheManager extends Model implements Observer{

    private CacheAccess<Integer, Employee> cache = null;
    private static final String EMPLOYEE_CACHE_NAME = "employeeCache";
    private static EmployeeCacheManager cacheManager;

    private EmployeeCacheManager(){
        try{
            cache = JCS.getInstance(EMPLOYEE_CACHE_NAME);
        }catch(CacheException e){
            e.printStackTrace();
        }
    }

    public synchronized static EmployeeCacheManager getManager(){
        if(cacheManager == null){
            cacheManager = new EmployeeCacheManager();
        }
        return cacheManager;
    }

    /**
     * Add new employee object to the cache
     * @param employee
     */
    public void addEmployee(Employee employee) {
        try {
            cache.put(employee.getId(), employee);
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployeeById(int id) {
        return (Employee) cache.get(id);
    }

    public void removeEmployeeById(int id) {
        try {
            cache.remove(id);
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Event event) {
        if (event.getType() == EventTypes.STARTUP){
            init();
        }
    }

    /**
     * Load the information fromo the database to the cache
     */
    public void init() {
        EmployeeDAO dao = new EmployeeDAO();
        for(Employee e : dao.getAllEmployees()){
            addEmployee(e);
        }
        notify(new Startup());
    }

    /**
     * @return an ArrayList representing all the objects Employee in the cache
     */
    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> allEmployees = new ArrayList<>();

        Set<Object> keys = getCacheKeys();

        for(Object key : keys){
            Employee e = cache.get((Integer) key);
            if(e != null){
                allEmployees.add(e);
            }
        }

        return allEmployees;

    }

    /**
     * @return an Employee representing the las object added to the cache
     */
    public Employee getLastEmployeeAdded() {
        Set<Object> keys = getCacheKeys();
        return cache.get((Integer) keys.size());
    }

    /**
     * Clear the cache entries
     */
    public void removeAllEmployees(){
        Set<Object> keys = getCacheKeys();
        for(Object key : keys){
            cache.remove((Integer) key);
        }
    }

    private Set<Object> getCacheKeys() {
        return  CompositeCacheManager.getInstance(EMPLOYEE_CACHE_NAME).getCache(EMPLOYEE_CACHE_NAME).getKeySet();
    }
}
