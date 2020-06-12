package agh.edu.pl.core.model;

public class ParsedNull extends ParsedValue {
    @Override
    public String toXML() {
        return "null";
    }
}
