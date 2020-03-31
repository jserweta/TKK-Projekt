package agh.edu.pl.core.reader;

import agh.edu.pl.core.exception.InvalidCharacterSequence;
import agh.edu.pl.core.model.*;

import java.util.Stack;

public class JSONReader {

    private Stack<Character> buf = new Stack<>();
    private int pos = 0;

    public JSONObject read(String json) throws Exception {
        char[] charArr = json.toCharArray();
        int size = charArr.length;

        for(int i = size - 1; i >= 0; --i) {
            buf.push(charArr[i]);
        }

        return readObject();
    }

    private JSONObject readObject() throws Exception {
        skipWhitespaces();

        if( buf.pop() != '{' )
            throw new InvalidCharacterSequence( pos, "Object opening bracket expected.");

        JSONObject obj = new JSONObject();

        while ( !buf.empty() ) {
            skipWhitespaces();

            String key;
            try {
                key = readKey();
            } catch (Exception e) {
                throw new InvalidCharacterSequence(pos, "Key string expected");
            }

            skipWhitespaces();

            ++pos;
            if ( buf.pop() != ':' )
                throw new InvalidCharacterSequence(pos, "Colon expected.");

            skipWhitespaces();

            JSONValue value;

            switch ( buf.peek() ) {
                case '\"':
                    JSONString jStr = new JSONString();
                    jStr.setValue(readString());
                    value = jStr;
                    break;
                case '{':
                    value = readObject();
                    break;
                case '[':
                    value = readArray();
                    break;
                case 'n':
                    readNull();
                    value = new JSONNull();
                    break;
                case 't':
                case 'f':
                    value = readBool();
                default:
                    if ( isDigit(buf.peek()) )
                        value = readNumber();
                    else
                        throw new InvalidCharacterSequence(pos, "Unrecognised value");
            }

            skipWhitespaces();

            try {
                obj.addField(key, value);
            } catch (Exception e) {
                throw new InvalidCharacterSequence(pos, "Unexpected null.");
            }

            if (buf.peek() == ',') {
                ++pos;
                buf.pop();
                continue;
            }

            if (buf.peek() == '}') {
                ++pos;
                buf.pop();
                break;
            }
        }

        return obj;
    }

    private JSONBoolean readBool() {
        throw new UnsupportedOperationException("Tried to read bool");
    }

    private JSONNull readNull() {
        throw new UnsupportedOperationException("Tried to read null");
    }

    private JSONNumber readNumber() {
        throw new UnsupportedOperationException("Tried to read number");
    }

    private JSONArray<JSONValue> readArray() {
        throw new UnsupportedOperationException("Tried to read array");
    }

    private String readString() throws Exception {
        StringBuilder builder = new StringBuilder();

        ++pos;
        if( buf.pop() != '\"')
            throw new InvalidCharacterSequence( pos, "Opening quotation mark expected.");

        while ( buf.peek() != '\"' ) {
            builder.append(buf.pop());
            ++pos;
        }

        ++pos;
        if( buf.pop() != '\"')
            throw new InvalidCharacterSequence( pos, "Closing quotation mark expected.");

        return builder.toString();
    }

    private String readKey() throws Exception {
        return readString();
    }

    private void skipWhitespaces() {
        while ( !buf.empty() && in(WHITESPACES, buf.peek()) ) {
            buf.pop();
            ++pos;
        }
    }

    private char[] WHITESPACES = {' ', '\t', '\n'};

    private boolean isDigit(char c) {
        return c > 47 && c < 58;
    }

    private boolean in(char[] allowedCharacters, char character) {
        for ( char allowed: allowedCharacters )
            if ( allowed == character)
                return true;

        return false;
    }

}
