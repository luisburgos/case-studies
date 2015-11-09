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
 * Manages all notifications to the clients and the registration to
 * the system.
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

    /**
     * Provides an interface to register a client to the PushNotificationSystem
     * @param subscriber
     */
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

    /**
     * Interface that lets notify all the listeners of a
     * region declared on the configuration file.
     * @param notification
     */
    public void notify(Notification notification) {
        for(NotificationWrapper n : manager.getNotifications()){
            if(n.getRegionName().equals(notification.getFrom())){
                notifyListeners(n, notification.getData());
            }
        }
    }

    private PushNotificationSystem(){
        manager = NotificationManagerConfigurator
                .createNotificationManagerFromConfigFile();
    }

    /**
     * Injects data information to a specific object instance given
     * the classpath of the instance class and the name of the
     * method to be call.
     * @param data
     * @param instance
     * @param classname
     * @param method
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void injectDataToListener(
            Object data,
            Object instance,
            String classname,
            String method
    ) throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException,
            InstantiationException {
        //Method body section.
        Class<?> classHolder = Class.forName(classname);
        Method methodToCall = getMethod(data, classHolder, method);
        invokeInjectionMethod(instance, methodToCall, data);
    }

    /**
     * Invokes a specific method
     * @param target
     * @param methodToCall
     * @param params
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void invokeInjectionMethod(
            Object target,
            Method methodToCall,
            Object... params
    ) throws InvocationTargetException, IllegalAccessException {
        methodToCall.invoke(target, params);
    }

    /**
     * Obtains a {@link Method} given the specific class that has
     * the method declaration needed and the method name.
     * @param data
     * @param classHolder
     * @param method
     * @return
     * @throws NoSuchMethodException
     */
    private Method getMethod(Object data, Class<?> classHolder, String method) throws
            NoSuchMethodException {
        return classHolder.getMethod(method, data.getClass());
    }

    /**
     * Notifies all listeners declared in the configuration file by
     * injecting data on the method that serves as interface for
     * communication between clients and the PushNotification System.
     * @param wrapper
     * @param data
     */
    private void notifyListeners(NotificationWrapper wrapper, Object data) {
        for(NotificationListener listener : wrapper.getListeners()){
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
