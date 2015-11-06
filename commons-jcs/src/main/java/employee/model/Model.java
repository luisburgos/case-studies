package employee.model;


import employee.misc.events.Event;
import employee.misc.Observable;
import employee.misc.Observer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by luisburgos on 2/09/15.
 */
public abstract class Model extends Observable {

    private HashMap<Integer, ArrayList<Observer>> setOfObservers;

    public Model(){
        setOfObservers = new HashMap<Integer, ArrayList<Observer>>();
    }

    public HashMap<Integer, ArrayList<Observer>> getSetOfObservers() {
        return setOfObservers;
    }

    @Override
    public void register(Event event, Observer observer) {
        getObserversList(event.getType()).add(observer);;
    }

    @Override
    public void unregister(Event event, Observer observer) {
        getObserversList(event.getType()).remove(observer);
    }

    @Override
    public void notify(Event event) {
        int eventType = event.getType();
        ArrayList observersToNotify = setOfObservers.get(eventType);
        Observer observer;
        for (Iterator it = observersToNotify.iterator(); it.hasNext();) {
            observer = (Observer)it.next();
            observer.update(event);
        }
    }

    private ArrayList<Observer> getObserversList(int eventType) {
        if (!setOfObservers.containsKey(eventType)) {
            setOfObservers.put(eventType, new ArrayList<Observer>());
        }
        return setOfObservers.get(eventType);
    }

    public void callFunctionByName(Class mClass, Class targetClass, String name, Object...params){
        Method method = null;
        try {
            method = mClass.getMethod(name, targetClass);
        } catch (SecurityException e) {
            System.err.println(e.getMessage());
        } catch (NoSuchMethodException e) {
            System.err.println(e.getMessage());
        }

        try {
            method.invoke(this, params);
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
    }

}
