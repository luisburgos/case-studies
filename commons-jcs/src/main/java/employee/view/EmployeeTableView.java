package employee.view;

import employee.cache.CacheManager;
import employee.cache.RegionNotFoundException;
import employee.notifications.PushNotificationSystem;
import employee.view.components.TableView;
import employee.misc.events.CacheRegionModified;
import employee.misc.events.Event;
import employee.misc.events.EventTypes;
import employee.misc.events.Startup;
import employee.misc.Observer;
import employee.model.entities.Employee;

import java.util.ArrayList;

/**
 * Created by luisburgos on 26/10/15.
 */
public class EmployeeTableView extends TableView {

    private static final String[] COLUMN_NAMES = new String[]{"Name", "Email", "Address"};

    public EmployeeTableView() {
        super("Employee", COLUMN_NAMES);
        PushNotificationSystem.getSystem().register(this);
    }

    public void setData(ArrayList<Employee> data){
        resetTable();
        for(Employee element : data){
            addEmployee(element);
        }
    }

    private void addEmployee(Employee employee) {
        ArrayList row = new ArrayList();
        row.add(employee.getName());
        row.add(employee.getEmail());
        row.add(employee.getAddress());
        add(row);
    }

}
