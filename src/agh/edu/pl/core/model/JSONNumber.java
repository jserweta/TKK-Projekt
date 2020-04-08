package agh.edu.pl.core.model;

public class JSONNumber implements JSONValue {

    private String value;

    public JSONNumber(String value) {
        this.value = value;
    }

    @Override
    public String printToXML() {
        return value;
    }
}
