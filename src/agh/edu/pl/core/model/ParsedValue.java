package agh.edu.pl.core.model;

public abstract class ParsedValue {

    protected int nestLevel = 0;

    abstract public String toXML();

    protected int getNestLevel() {
        return nestLevel;
    }

    protected String getIntend() {
        return new String(new char[getNestLevel()]).replace("\0", "  ");
    }

    protected void setNestLevel(int nestLevel) {
        this.nestLevel = nestLevel;
    }
}
