package agh.edu.pl.core.model;

public class JSONNull implements JSONValue {
    @Override
    public String printToXML() {
        return "null";
    }
}
