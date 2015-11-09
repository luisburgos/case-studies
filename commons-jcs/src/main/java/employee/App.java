package employee;

import employee.cache.CacheManager;
import employee.controller.AddEmployeeController;
import employee.controller.Controller;
import employee.misc.events.*;
import employee.misc.Observer;
import employee.model.Employees;
import employee.model.Model;
import employee.model.entities.Employee;
import employee.notifications.PushNotificationSystem;
import employee.view.EmployeeTableView;

/**
 * Created by luisburgos on 26/10/15.
 */
public class App {

    private Model mModel;
    private Controller mController;
    private EmployeeTableView mTable;

    public App (){
        start();
    }

    private void init(){

        mModel = Employees.getInstance();
        mController = new AddEmployeeController(mModel, new NewEmployee());
        mTable = new EmployeeTableView();

        mModel.register(new Startup(), CacheManager.getManager());
        mModel.register(new Shutdown(), CacheManager.getManager());

    }

    private void start(){
        init();
        mModel.notify(
                new Startup().addStartupRegion("employee")
        );
    }

    public static void main(String[] args) {
        new App();
    }

}
