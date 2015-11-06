package employee.misc;

import employee.misc.events.Event;

public abstract class Observable {
    
    public abstract void register(Event event , Observer observer);
    public abstract void unregister(Event event , Observer observer);
    public abstract void notify(Event event);
    
}