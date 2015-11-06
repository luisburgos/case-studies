package employee;

import employee.cache.CacheManager;
import employee.controller.AddEmployeeController;
import employee.controller.Controller;
import employee.misc.events.*;
import employee.misc.Observer;
import employee.model.Employees;
import employee.model.Model;
import employee.view.EmployeeTableView;

/**
 * Created by luisburgos on 26/10/15.
 */
public class App implements Observer{

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

        CacheManager.getManager().register(new Startup(), mTable);
        CacheManager.getManager().register(new CacheRegionModified(), this);
        CacheManager.getManager().register(new Shutdown(), this);
    }

    private void start(){
        init();
        mModel.notify(
                new Startup().addStartupRegion("employee")
        );
    }

    @Override
    public void update(Event event) {
        switch (event.getType()){
            case EventTypes.CACHE_REGIN_MODIFIED:
                mTable.update(event);
                break;
        }
    }

    public static void main(String[] args) {
        new App();
    }

}
