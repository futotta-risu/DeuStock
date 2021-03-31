package es.deusto.deustock.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logger class to avoid cross references through the code.
 *
 * @author Erik B. Terres
 */
public class DeuLogger {
    public static final Logger logger = LoggerFactory.getLogger(DeuLogger.class);
}
