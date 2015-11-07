package employee.notifications;

import employee.misc.Observer;
import employee.misc.events.CacheRegionModified;
import employee.misc.events.Event;
import employee.misc.events.EventTypes;
import employee.model.entities.Employee;
import employee.view.EmployeeTableView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by luisburgos on 6/11/15.
 */
public class PushNotificationSystem implements Observer{

    private static PushNotificationSystem notificationSystem;
    private PushNotificationManager manager;

    private PushNotificationSystem(){
        manager = PushNotificationConfigurator.getNotificationManagerFromConfigFile();
        System.out.println(manager.getNotifications());
    }

    public synchronized static PushNotificationSystem getSystem(){
        if(notificationSystem == null){
            notificationSystem = new PushNotificationSystem();
        }
        return notificationSystem;
    }

    @Override
    public void update(Event event) {
        if(event.getType() == EventTypes.CACHE_REGIN_MODIFIED){
            String regionName = ((CacheRegionModified) event).getRegionModifiedName();
            System.out.println("Region name " + regionName);
            Object data = ((CacheRegionModified) event).getData();
            System.out.println("Data modified " + data);
            List<NotificationListener> notifications = manager.getByName(regionName).getListeners();
            for(NotificationListener n : notifications){
                System.out.println(n);
                try {
                    injectDataToListener(data, n.getClassname(), n.getMethod());
                } catch (ClassNotFoundException e) {
                    System.out.println("Cannot inject data to " + n.getClassname());
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    System.out.println("Cannot inject data to " + n.getMethod());
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void injectDataToListener(Object data, String classname, String method) throws
            ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException,
            InstantiationException {
        Class<?> classHolder = Class.forName(classname);
        Method methodToCall = getMethod(data, classHolder, method);
        Object instanceClass = getInstanceClass(classHolder);
        invokeInjectionMethod(instanceClass, methodToCall, data);
    }

    private Object getInstanceClass(Class<?> classHolder) throws
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        Constructor[] cons = classHolder.getDeclaredConstructors();
        cons[0].setAccessible(true);
        return cons[0].newInstance();
    }

    private void invokeInjectionMethod(Object target, Method methodToCall, Object... params) throws
            InvocationTargetException,
            IllegalAccessException {
        System.out.println("Invoking " + methodToCall.getName());
        methodToCall.invoke(target, params);
    }

    private Method getMethod(Object data, Class<?> classHolder, String method) throws
            NoSuchMethodException {
        return classHolder.getMethod(method, data.getClass());
    }

    /*public static void main(String[] args) {
        PushNotificationSystem p = PushNotificationSystem.getSystem();
        Employee emp = new Employee();
        emp.setName("Luis");
        emp.setEmail("Test");
        emp.setAddress("Test");
        p.update(new CacheRegionModified("employee")
                .setData(emp));
    }*/
}
