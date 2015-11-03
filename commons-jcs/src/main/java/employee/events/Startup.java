package employee.events;

import java.util.ArrayList;

/**
 * Created by luisburgos on 29/10/15.
 */
public class Startup extends Event {

    private ArrayList<String> startupRegions;

    public Startup() {
        super(EventTypes.STARTUP);
        startupRegions = new ArrayList<>();
    }

    public Startup addStartupRegion(String regionName){
        startupRegions.add(regionName);
        return this;
    }

    public ArrayList<String> getStartupRegions() {
        return startupRegions;
    }
}
