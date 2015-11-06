package employee.misc.events;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Event {
    
    private int type;
    private String eventInformation;
    private Date date;

    public Event(int type){
        this.type = type;
    }

    public Event(int type, String eventInformation){
        this.type = type;
        this.eventInformation = eventInformation;
        this.date = new Date();
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the eventInformation
     */
    public String getEventInformation() {
        return eventInformation;
    }

    /**
     * @param eventInformation the eventInformation to set
     */
    public void setEventInformation(String eventInformation) {
        this.eventInformation = eventInformation;
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }
    
    
}