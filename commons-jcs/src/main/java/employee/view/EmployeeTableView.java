package employee.view;

import employee.events.Event;
import employee.events.EventTypes;
import employee.misc.Observer;
import employee.model.Employee;
import employee.model.EmployeeCacheManager;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by luisburgos on 26/10/15.
 */
public class EmployeeTableView extends TableView implements Observer {

    private static final String[] COLUMN_NAMES = new String[]{"Name", "Email", "Address"};

    public EmployeeTableView() {
        super("Employee", COLUMN_NAMES);
    }

    public void update(Event event) {
        switch (event.getType()){
            case EventTypes.STARTUP:
                initTableData();
                break;
            case EventTypes.NEW_EMPLOYEE:
                updateWithLastAdded();
                break;
        }
    }

    private void updateWithLastAdded() {
        addEmployee(EmployeeCacheManager.getManager().getLastEmployeeAdded());
    }

    private void initTableData() {
        for(Employee employee : EmployeeCacheManager.getManager().getAllEmployees()){
            addEmployee(employee);
        }
    }

    public void addEmployee(Employee employee) {
        ArrayList row = new ArrayList();
        row.add(employee.getName());
        row.add(employee.getEmail());
        row.add(employee.getAddress());
        add(row);
    }

}
