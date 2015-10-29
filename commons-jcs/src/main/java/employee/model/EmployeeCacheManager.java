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

    public void init() {
        EmployeeDAO dao = new EmployeeDAO();
        for(Employee e : dao.getAllEmployees()){
            addEmployee(e);
        }
        notify(new Startup());
    }

    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> allEmployees = new ArrayList<>();

        Set<Object> keys =
                CompositeCacheManager.getInstance(EMPLOYEE_CACHE_NAME).getCache(EMPLOYEE_CACHE_NAME).getKeySet();

        for(Object key : keys){
            Employee e = cache.get((Integer) key);
            if(e != null){
                allEmployees.add(e);
            }
        }

        return allEmployees;

    }

    public Employee getLastEmployeeAdded() {
        Set<Object> keys =
                CompositeCacheManager.getInstance(EMPLOYEE_CACHE_NAME).getCache(EMPLOYEE_CACHE_NAME).getKeySet();
        return cache.get((Integer) keys.size());
    }

}
