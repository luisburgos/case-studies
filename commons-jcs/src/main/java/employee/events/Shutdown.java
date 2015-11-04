package employee.events;

/**
 * Created by luisburgos on 1/11/15.
 */
public class Shutdown extends Event{
    public Shutdown() {
        super(EventTypes.SHUTDOWN);
    }
    public Shutdown(int type, String eventInformation) {
        super(type, eventInformation);
    }
}
