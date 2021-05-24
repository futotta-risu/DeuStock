package es.deusto.deustock.client.controllers;

import java.util.HashMap;

/**
 * Interface DSGenericController with the following method
 */
public interface DSGenericController {

    /**
     * Method that sets the parameters
     *
     * @param params collects all the received objects with their respective key in a HashMap
     */
    void setParams(HashMap<String, Object> params);
}
