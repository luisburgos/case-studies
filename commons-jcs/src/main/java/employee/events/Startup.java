package employee.events;

/**
 * Created by luisburgos on 29/10/15.
 */
public class Startup extends Event {
    public Startup() {
        super(EventTypes.STARTUP);
    }

    public Startup(int type, String eventInformation) {
        super(type, eventInformation);
    }
}
