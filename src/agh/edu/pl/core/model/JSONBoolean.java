package agh.edu.pl.core.model;

public class JSONBoolean implements JSONValue {

    private boolean value;

    public JSONBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public String printToXML() {
        return value ? "true" : "false";
    }
}
