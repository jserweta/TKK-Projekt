package agh.edu.pl.core.model;

public class JSONString implements JSONValue {

    private String value;

    public void setValue(String value) throws Exception {
        if ( value == null )
            throw new Exception("Unexpected null string");
        this.value = value;
    }

    @Override
    public String printToXML() {
        return "\"" + value + "\"";
    }
}
