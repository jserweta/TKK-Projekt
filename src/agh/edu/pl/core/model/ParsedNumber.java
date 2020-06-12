package agh.edu.pl.core.model;

public class ParsedNumber extends ParsedValue {

    private String value;

    public ParsedNumber(String value) {
        this.value = value;
    }

    @Override
    public String toXML() {
        return value;
    }
}
