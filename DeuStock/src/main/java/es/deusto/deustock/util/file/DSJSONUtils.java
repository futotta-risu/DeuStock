package es.deusto.deustock.util.file;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;


/**
 * deustock JSON util class
 *
 * @author Erik B. Terres
 */
public class DSJSONUtils {

    public static JSONObject readFile(String path) throws IOException, ParseException {
        return (JSONObject) (new JSONParser()).parse(new FileReader(path));
    }

}
