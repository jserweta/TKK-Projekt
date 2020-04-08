package agh.edu.pl.core.model;

import java.util.ArrayList;
import java.util.List;

public class JSONArray<T extends JSONValue> implements JSONValue {

    private List<T> values = new ArrayList<>();
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void addValue(T value) {
        values.add(value);
    }

    @Override
    public String printToXML() {
        String elementName;
        if(name.length() > 2 && name.charAt(name.length()-1) == 's')
            elementName = name.substring(0, name.length() - 1);
        else elementName = name + "Element";

        StringBuilder xmlString = new StringBuilder();

        for (JSONValue value: values) {
            xmlString.append("<" + elementName + ">");
            xmlString.append(value.printToXML());
            xmlString.append("</" + elementName + ">\n");
        }
        return xmlString.toString();
    }
}
