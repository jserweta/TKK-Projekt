package agh.edu.pl.core.model;

public class ParsedBoolean extends ParsedValue {

    private boolean value;

    public ParsedBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public String toXML() {
        return value ? "true" : "false";
    }
}
