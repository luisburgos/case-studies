package employee.events;

/**
 * Created by luisburgos on 26/10/15.
 */
public class NewEmployee extends Event {

    public NewEmployee(int type) {
        super(type, "Nuevo empleado");
    }

}
