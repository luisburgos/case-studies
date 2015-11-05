package employee.cache;

import employee.database.DAO;
import employee.database.EmployeeDAO;
import employee.events.CacheRegionModified;
import employee.events.Event;
import employee.events.EventTypes;
import employee.events.Startup;
import employee.misc.Observer;
import employee.model.Model;
import employee.model.entities.Employee;
import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.exception.CacheException;
import sun.misc.Cache;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Manage all regions defined in the program.
 * This class offers operations to region manipulation and
 * implements a notification system which triggers {@link CacheRegionModified}
 * events when a region data is modified.
 *
 * Created by luisburgos on 1/11/15.
 */
public class CacheManager extends Model implements Observer{

    private static CacheManager cacheManager;
    private HashMap<String, CacheRegion> cacheRegions;

    private CacheManager(){
        cacheRegions = new HashMap<>();
    }

    public synchronized static CacheManager getManager(){
        if(cacheManager == null){
            cacheManager = new CacheManager();
        }
        return cacheManager;
    }

    /**
     * Creates a new {@link CacheRegion} given a regionName to keep tracked.
     *
     * @param regionName of the region to put in {@link CacheManager#cacheRegions}
     * @throws RegionAlreadyDefined when a region name is already contained in {@link CacheManager#cacheRegions}
     */
    public void initCacheRegion(String regionName) throws RegionAlreadyDefined {
        if(cacheRegions.containsKey(regionName)){
            throw new RegionAlreadyDefined();
        }
        cacheRegions.put(regionName, new CacheRegion(regionName));
    }

    /**
     * Retrives a {@link CacheRegion} given a region name
     * @param regionName of the region to look add.
     * @return a CacheRegion object given the region name
     * @throws RegionNotFoundException when the existing name is not contained in {@link CacheManager#cacheRegions}
     */
    public CacheRegion getCacheRegion(String regionName) throws RegionNotFoundException {
        if(!cacheRegions.containsKey(regionName)){
            throw new RegionNotFoundException();
        }
        return cacheRegions.get(regionName);
    }

    /**
     * Loads all the information about an entity from a database source to
     * the corresponding CacheRegion given a region name.
     * @param regionName of the region for load information.
     * @param dao represents the specific data access object of a specific entity.
     * @throws RegionNotFoundException when the existing name is not contained in {@link CacheManager#cacheRegions}
     */
    public void loadCacheInformationFromDatabase(String regionName, DAO dao) throws RegionNotFoundException {
        Iterator iterator = dao.getAll().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            Object key = pair.getKey();
            Object value = pair.getValue();
            getCacheRegion(regionName).put(key, value);
        }
    }

    /**
     * Inserts an Object to a specific {@link CacheRegion}
     * @param regionName of the specific region where the element will be added.
     * @param key
     * @param value
     * @throws RegionNotFoundException when the existing name is not contained in {@link CacheManager#cacheRegions}
     */
    public void addElementToRegion(String regionName, Object key, Object value) throws RegionNotFoundException {
        CacheRegion region = getCacheRegion(regionName);
        region.put(key, value);
        notify(new CacheRegionModified(regionName).setData(value));
    }

    /**
     * Retrieves the last object added on a specific {@link CacheRegion}
     * @param regionName of the region to look at it.
     * @return an Object representing the last element added on the region.
     * @throws RegionNotFoundException when the existing name is not contained in {@link CacheManager#cacheRegions}
     */
    public Object getLastFromRegion(String regionName) throws RegionNotFoundException {
        return getCacheRegion(regionName).getLast();
    }

    /**
     * Retrieves and ArrayList of Objects containing all the data information of a specific {@link CacheRegion}
     * @param regionName
     * @return an ArrayList representing all information of the region
     * @throws RegionNotFoundException when the existing name is not contained in {@link CacheManager#cacheRegions}
     */
    public ArrayList<Object> getAllFromRegion(String regionName) throws RegionNotFoundException {
        return getCacheRegion(regionName).getAll();
    }

    /**
     * Notification mechanism. Triggers an event when a region data is modified.
     * @param event
     */
    @Override
    public void update(Event event) {
        if (event.getType() == EventTypes.STARTUP){
            for(String region : ((Startup) event).getStartupRegions()){
                initCacheRegion(region);
                try {
                    //TODO: Fix hardcoded EmployeeDAO.
                    loadCacheInformationFromDatabase(region, new EmployeeDAO());
                    notify(((Startup) event).setData(getAllFromRegion(region)));
                } catch (RegionNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        if(event.getType() == EventTypes.SHUTDOWN){
            dropAllRegionsData();
        }
    }

    /**
     * Deletes all information of all regions managed by this class.
     */
    private void dropAllRegionsData() {
        for(String key : cacheRegions.keySet()){
            try {
                getCacheRegion(key).dropCacheData();
            } catch (RegionNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
