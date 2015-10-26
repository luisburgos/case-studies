import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;


/**
 * Created by luisburgos on 26/10/15.
 */
public class CityManager {

    private JCS cache;
    private static final String CITY_CACHE_NAME = "default";

    public CityManager() {
        try {
            cache = JCS.getInstance(CITY_CACHE_NAME);
        } catch (CacheException e) {
            System.out.println(String.format("Problem initializing cache: %s", e.getMessage()));
        }
    }

    public void addCity(City city) {
        String key = city.name;
        try {
            cache.put(key, city);
        } catch (CacheException e) {
            System.out.println(String.format("Problem putting city %s in the cache, for key %s%n%s",
                    city.name, key, e.getMessage()));
        }
    }

    public City getCity(String cityKey) {
        return (City) cache.get(cityKey);
    }

    public void removeCity(String cityName) {
        try {
            cache.remove(cityName);
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

}
