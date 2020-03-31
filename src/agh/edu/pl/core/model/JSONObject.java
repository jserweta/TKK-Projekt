package agh.edu.pl.core.model;

import java.util.Iterator;
import java.util.Map;

public class JSONObject implements JSONValue {

    private Map<String, JSONValue> fields = new java.util.HashMap<>();

    public void addField(String key, JSONValue value) throws Exception {
        if( fields.containsKey(key) )
            throw new Exception("Key duplicate");
        if( key == null || value == null)
            throw new Exception("Unexpected null.");
        fields.put(key, value);
    }

    @Override
    public String printToXML() {
        StringBuilder xmlString = new StringBuilder();

        Iterator<String> keys = fields.keySet().iterator();

        while (keys.hasNext()) {
            String key = keys.next();
            xmlString.append("<" +key + ">");
            xmlString.append(fields.get(key).printToXML());
            xmlString.append("</" + key + ">\n");
        }

        return xmlString.toString();
    }
}
