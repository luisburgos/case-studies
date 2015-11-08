package employee.notifications;

import java.util.List;

/**
 * Created by luisburgos on 7/11/15.
 */
public class NotificationWrapper {

    private String regionName;
    private List<NotificationListener> listeners;

    public NotificationWrapper(){}

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public List<NotificationListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<NotificationListener> listeners) {
        this.listeners = listeners;
    }

}
