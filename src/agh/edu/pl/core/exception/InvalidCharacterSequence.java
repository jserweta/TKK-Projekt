package agh.edu.pl.core.exception;

public class InvalidCharacterSequence extends Exception {

    public InvalidCharacterSequence(int pos, String message) {
        super( String.format("Invalid character sequence at position %d.", pos) + message);
    }
}
