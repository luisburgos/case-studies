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

    public void initCacheRegion(String regionName) throws CacheException, RegionAlreadyDefined {
        if(cacheRegions.containsKey(regionName)){
            throw new RegionAlreadyDefined();
        }
        cacheRegions.put(regionName, new CacheRegion(regionName));
    }

    public CacheRegion getCacheRegion(String regionName) throws RegionNotFoundException {
        if(!cacheRegions.containsKey(regionName)){
            throw new RegionNotFoundException();
        }
        return cacheRegions.get(regionName);
    }

    public void loadCacheInformationFromDatabase(String regionName, DAO dao) throws RegionNotFoundException {
        Iterator iterator = dao.getAll().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            Object key = pair.getKey();
            Object value = pair.getValue();
            getCacheRegion(regionName).put(key, value);
        }
    }

    public void addElementToRegion(String regionName, Object key, Object value) throws RegionNotFoundException {
        CacheRegion region = getCacheRegion(regionName);
        region.put(key, value);
        notify(new CacheRegionModified(regionName));
    }

    public Object getLastFromRegion(String regionName) throws RegionNotFoundException {
        return getCacheRegion(regionName).getLast();
    }

    public ArrayList<Object> getAllFromRegion(String regionName) throws RegionNotFoundException {
        return getCacheRegion(regionName).getAll();
    }

    @Override
    public void update(Event event) {
        if (event.getType() == EventTypes.STARTUP){
            for(String region : ((Startup) event).getStartupRegions()){
                initCacheRegion(region);
                try {
                    //TODO: Fix hardcoded EmployeeDAO.
                    loadCacheInformationFromDatabase(region, new EmployeeDAO());
                } catch (RegionNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        if(event.getType() == EventTypes.SHUTDOWN){
            dropAllRegionsData();
        }
    }

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
