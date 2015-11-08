package employee.notifications;

import employee.App;
import employee.misc.Observer;
import employee.misc.events.CacheRegionModified;
import employee.misc.events.Event;
import employee.misc.events.EventTypes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * Created by luisburgos on 6/11/15.
 */
public class PushNotificationSystem {

    private static PushNotificationSystem notificationSystem;
    private PushNotificationManager manager;

    public synchronized static PushNotificationSystem getSystem(){
        if(notificationSystem == null){
            notificationSystem = new PushNotificationSystem();
        }
        return notificationSystem;
    }

    public void register(Object subscriber){
        String classpath = subscriber.getClass().getCanonicalName();
        for(NotificationWrapper n : manager.getNotifications()){
            for(NotificationListener l : n.getListeners()){
                if(l.getClasspath().equals(classpath)){
                    l.setInstance(subscriber);
                }
            }
        }
    }

    public void notify(Notification notification) {
        for(NotificationWrapper n : manager.getNotifications()){
            if(n.getRegionName().equals(notification.getFrom())){
                notifyListeners(n, notification.getData());
            }
        }
    }

    private PushNotificationSystem(){
        manager = NotificationManagerConfigurator.createNotificationManagerFromConfigFile();
    }

    private void injectDataToListener(Object data, Object instance, String classname, String method) throws
            ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException,
            InstantiationException {
        Class<?> classHolder = Class.forName(classname);
        Method methodToCall = getMethod(data, classHolder, method);
        Object instanceClass = instance;
        invokeInjectionMethod(instanceClass, methodToCall, data);
    }

    private void invokeInjectionMethod(Object target, Method methodToCall, Object... params) throws
            InvocationTargetException,
            IllegalAccessException {
        methodToCall.invoke(target, params);
    }

    private Method getMethod(Object data, Class<?> classHolder, String method) throws
            NoSuchMethodException {
        return classHolder.getMethod(method, data.getClass());
    }

    private void notifyListeners(NotificationWrapper n, Object data) {
        for(NotificationListener listener : n.getListeners()){
            try {
                if(listener.getInstance() == null){
                    break;
                }
                injectDataToListener(
                        data,
                        listener.getInstance(),
                        listener.getClasspath(),
                        listener.getMethod()
                );
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
