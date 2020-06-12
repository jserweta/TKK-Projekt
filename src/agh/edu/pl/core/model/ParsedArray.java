package agh.edu.pl.core.model;

import java.util.ArrayList;
import java.util.List;

public class ParsedArray extends ParsedValue {

    private List<ParsedValue> values = new ArrayList<>();
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void addValue(ParsedValue value) {
        values.add(value);
    }

    @Override
    public String toXML() {
        String elementName = name;
//        if(name.length() > 2 && name.charAt(name.length()-1) == 's')
//            elementName = name.substring(0, name.length() - 1);
//        else elementName = name + "Element";

        StringBuilder xmlString = new StringBuilder();

        for (ParsedValue value: values) {
            xmlString.append(getIntend());
            xmlString.append("<" + elementName + ">");
            if(value instanceof ParsedObject)
                xmlString.append("\n");
            xmlString.append(value.toXML());
            if(value instanceof ParsedObject)
                xmlString.append(getIntend());
            xmlString.append("</" + elementName + ">\n");
        }
        return xmlString.toString();
    }

    @Override
    protected void setNestLevel(int nestLevel) {
        super.setNestLevel(nestLevel);
        values.stream().forEach( pv -> pv.setNestLevel(this.getNestLevel() + 1));
    }
}
