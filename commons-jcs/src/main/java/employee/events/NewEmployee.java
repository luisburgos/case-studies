package employee.events;

import employee.model.Employees;
import employee.model.entities.Employee;

/**
 * Created by luisburgos on 26/10/15.
 */
public class NewEmployee extends CacheRegionModified {

    public NewEmployee(){
        super(Employees.EMPLOYEE_CACHE_NAME);
        setType(EventTypes.NEW_EMPLOYEE);
    }

}
