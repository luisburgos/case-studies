package employee.notifications;

import java.util.List;

/**
 * Created by luisburgos on 6/11/15.
 */
public class PushNotificationManager {

    private List<NotificationWrapper> notifications;

    public PushNotificationManager (){}

    public void setNotifications(List<NotificationWrapper> notifications) {
        this.notifications = notifications;
    }

    public List<NotificationWrapper> getNotifications() {
        return notifications;
    }

    public NotificationWrapper getByName(String name){
        NotificationWrapper wrapperByName = null;
        for(NotificationWrapper wrapper : notifications){
            System.out.println(wrapper);
            if(wrapper.getRegionName().equalsIgnoreCase(name)){
                wrapperByName = wrapper;
            }
        }
        return wrapperByName;
    }

    @Override
    public String toString() {
        return "PushNotificationManager{" +
                "notifications=" + notifications +
                '}';
    }
}
