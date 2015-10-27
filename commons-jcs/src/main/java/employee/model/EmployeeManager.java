package employee.model;

import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.exception.CacheException;

/**
 * Created by luisburgos on 26/10/15.
 */
public class EmployeeManager {

    private CacheAccess<String, Employee> cache = null;
    private static final String EMPLOYEE_CACHE_NAME = "employeeCache";

    public EmployeeManager (){
        try{
            cache = JCS.getInstance(EMPLOYEE_CACHE_NAME);
            cache.put("1", new Employee("1", "Luis", "C. 11 #118 Mérida"));
            cache.put("2", new Employee("2", "Jose", "C. 12 #238 Mérida"));
        }catch(CacheException e){
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee employee) {
        try {
            cache.put(employee.getId(), employee);
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployeeById(String id) {
        return (Employee) cache.get(id);
    }

    public void removeEmployeeById(String id) {
        try {
            cache.remove(id);
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();

        //manager.addEmployee(new Employee("1", "Luis", "C. 11 #118 Mérida"));
        //manager.addEmployee(new Employee("2", "Jose", "C. 12 #238 Mérida"));

        Employee employee = manager.getEmployeeById("1");
        if(employee != null){
            System.out.println(employee.getName());
        }else{
            System.out.println("Errorz..");
        }
    }

}
