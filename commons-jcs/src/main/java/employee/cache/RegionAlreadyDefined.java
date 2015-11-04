package employee.cache;

import org.apache.commons.jcs.access.exception.CacheException;

/**
 * Created by luisburgos on 2/11/15.
 */
public class RegionAlreadyDefined extends CacheException {

    private static final String MESSAGE = "The region you trying to defined, is already defined";

    public RegionAlreadyDefined() {
        super(MESSAGE);
    }

    public RegionAlreadyDefined(String message) {
        super(message);
    }

}
