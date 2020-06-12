package agh.edu.pl.core.model;

import java.util.Iterator;
import java.util.Map;

public class ParsedObject extends ParsedValue {

    private Map<String, ParsedValue> fields = new java.util.HashMap<>();

    public void addField(String key, ParsedValue value) throws Exception {
        if( fields.containsKey(key) )
            throw new Exception("Key duplicate");
        if( key == null || value == null)
            throw new Exception("Unexpected null.");
        fields.put(key, value);
    }

    @Override
    public String toXML() {
        StringBuilder xmlString = new StringBuilder();
        setNestLevel(this.getNestLevel());

        Iterator<String> keys = fields.keySet().iterator();

        while (keys.hasNext()) {

            String key = keys.next();
            ParsedValue value = fields.get(key);
            if (value instanceof ParsedObject) {
                xmlString.append(getIntend());
                xmlString.append("<" + key + ">");
                xmlString.append(value.toXML());
                xmlString.append(getIntend());
                xmlString.append("</" + key + ">\n");
            } else if (value instanceof ParsedArray) {
                xmlString.append(value.toXML());
            } else {
                xmlString.append(getIntend());
                xmlString.append("<" +key + ">");
                xmlString.append(value.toXML());
                xmlString.append("</" + key + ">\n");
            }
        }

        return xmlString.toString();
    }

    @Override
    protected void setNestLevel(int nestLevel) {
        super.setNestLevel(nestLevel);
        fields.values().stream().forEach( pv -> pv.setNestLevel(this.getNestLevel() + 1));
    }
}
