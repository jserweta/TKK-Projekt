package agh.edu.pl.core.model;

public class ParsedString extends ParsedValue {

    private String value;

    public void setValue(String value) throws Exception {
        if ( value == null )
            throw new Exception("Unexpected null string");
        this.value = value;
    }

    @Override
    public String toXML() {
        return "\"" + value + "\"";
    }
}
