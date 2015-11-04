package employee.view;

import employee.cache.CacheManager;
import employee.cache.RegionNotFoundException;
import employee.components.TableView;
import employee.events.CacheRegionModified;
import employee.events.Event;
import employee.events.EventTypes;
import employee.misc.Observer;
import employee.model.Employees;
import employee.model.entities.Employee;

import java.util.ArrayList;

/**
 * Created by luisburgos on 26/10/15.
 */
public class EmployeeTableView extends TableView implements Observer{

    private static final String[] COLUMN_NAMES = new String[]{"Name", "Email", "Address"};

    public EmployeeTableView() {
        super("Employee", COLUMN_NAMES);
    }

    public void update(Event event) {
        switch (event.getType()){
            case EventTypes.STARTUP:
                initTableData(Employees.EMPLOYEE_CACHE_NAME);
                break;
            case EventTypes.NEW_EMPLOYEE:
                updateWithLastAdded(Employees.EMPLOYEE_CACHE_NAME);
            case EventTypes.CACHE_REGIN_MODIFIED:
                String regionModified = getRegionNameFromEvent(event);
                updateWithLastAdded(regionModified);
                break;
        }
    }

    private void updateWithLastAdded(String regionName) {
        try {
            addEmployee((Employee)CacheManager.getManager().getLastFromRegion(regionName));
        } catch (RegionNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private void initTableData(String regionName) {
        try {
            for(Object employee : CacheManager.getManager().getAllFromRegion(regionName)){
                addEmployee((Employee)employee);
            }
        } catch (RegionNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addEmployee(Employee employee) {
        ArrayList row = new ArrayList();
        row.add(employee.getName());
        row.add(employee.getEmail());
        row.add(employee.getAddress());
        add(row);
    }


    private String getRegionNameFromEvent(Event event) {
        return ((CacheRegionModified) event).getRegionModifiedName();
    }
}
