package employee.misc;


import employee.misc.events.Event;

/**
 * Created by luisburgos on 2/09/15.
 */
public interface Observer {
    public void update(Event event);
}
