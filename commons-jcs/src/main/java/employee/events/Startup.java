package employee.events;

import java.util.ArrayList;

/**
 * Created by luisburgos on 29/10/15.
 */
public class Startup extends Event {

    private ArrayList<String> startupRegions;
    private ArrayList<Object> data;

    public Startup() {
        super(EventTypes.STARTUP);
        startupRegions = new ArrayList<>();
    }

    public ArrayList<Object> getData() {
        return data;
    }

    public Startup setData(ArrayList<Object> data) {
        this.data = data;
        return this;
    }

    public Startup addStartupRegion(String regionName){
        startupRegions.add(regionName);
        return this;
    }

    public ArrayList<String> getStartupRegions() {
        return startupRegions;
    }
}
