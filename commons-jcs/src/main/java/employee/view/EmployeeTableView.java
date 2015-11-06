package employee.view;

import employee.cache.CacheManager;
import employee.cache.RegionNotFoundException;
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
public class EmployeeTableView extends TableView implements Observer{

    private static final String[] COLUMN_NAMES = new String[]{"Name", "Email", "Address"};

    public EmployeeTableView() {
        super("Employee", COLUMN_NAMES);
    }

    public void update(Event event) {
        switch (event.getType()){
            case EventTypes.STARTUP:
                initTableData(((Startup) event).getData());
                break;
            case EventTypes.NEW_EMPLOYEE:
                //updateWithLastAdded(Employees.EMPLOYEE_CACHE_NAME);
            case EventTypes.CACHE_REGIN_MODIFIED:
                //String regionModified = getRegionNameFromEvent(event);
                updateWithLastAdded(((CacheRegionModified) event).getData());
                break;
        }
    }

    /**
     * Updates the view with new information obtained from event propagation.
     * @param lastAdded an Object representing the information in the event propagation.
     */
    private void updateWithLastAdded(Object lastAdded) {
        try {
            addEmployee((Employee) lastAdded);
        } catch (RegionNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * DEPRECATED!! Use {@link EmployeeTableView#updateWithLastAdded(Object)} instead for
     * get the data from event propagation rather than ask to the Cache for the new information
     * @param regionName
     */
    private void updateWithLastAdded(String regionName) {
        try {
            addEmployee((Employee)CacheManager.getManager().getLastFromRegion(regionName));
        } catch (RegionNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Init the view with information obtained from event propagation.
     * @param initialTableData an ArrayList representing all the information contained in
     * the a specific cache region.
     */
    private void initTableData(ArrayList<Object> initialTableData) {
        try {
            for(Object employee : initialTableData){
                addEmployee((Employee)employee);
            }
        } catch (RegionNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * DEPRECATED!! Use {@link EmployeeTableView#initTableData(ArrayList)} (Object)} instead for
     * get the data from event propagation rather than ask to the Cache for the new information
     * @param regionName
     */
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
