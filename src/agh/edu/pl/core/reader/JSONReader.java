package agh.edu.pl.core.reader;

import agh.edu.pl.core.exception.InvalidCharacterSequence;
import agh.edu.pl.core.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;

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

        ++pos;
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

            JSONValue value = readValue(key);

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

    private JSONBoolean readBool() throws InvalidCharacterSequence {
        if (buf.peek() == 't' ) {
            readExactly("true".toCharArray());
            return new JSONBoolean(true);
        } else {
            readExactly("false".toCharArray());
            return new JSONBoolean(false);
        }
    }

    private void readNull() throws InvalidCharacterSequence {
        readExactly("null".toCharArray());
    }

    private JSONNumber readNumber() throws InvalidCharacterSequence {
        StringBuilder builder = new StringBuilder();

        if ( buf.peek() == '-') {
            ++pos;
            builder.append(buf.pop());
        }

        Character[] allowedCharacters = "123456789".chars().mapToObj(c -> (char)c).toArray(Character[]::new);
        ArrayList<Character> allowed = Arrays.stream(allowedCharacters).collect(Collectors.toCollection(ArrayList::new));

        boolean first = true;
        do {
            if ( first && buf.peek() == '0' ) {
                ++pos;
                builder.append(buf.pop());
                first = false;
                break;
            }

            if ( in(allowed, buf.peek())) {
                if ( buf.peek() == '0' && !in(allowed, '0') )
                    allowed.add('0');
                ++pos;
                builder.append(buf.pop());
            } else {
                throw new InvalidCharacterSequence(pos,
                        "Expected digits: " + allowed.stream().map(x -> x.toString()).collect(Collectors.joining(", ")));
            }
        } while ( buf.peek() != '.' && buf.peek() != 'e' && buf.peek() != 'E' && !in(END_CHARACTERS, buf.peek()) );

        if ( !in(allowed, '0'))
            allowed.add('0');

        //fraction
        if ( buf.peek() == '.') {
            ++pos;
            builder.append(buf.pop());

            do {
                if ( in(allowed, buf.peek())) {
                    ++pos;
                    builder.append(buf.pop());
                } else {
                    throw new InvalidCharacterSequence(pos,
                            "Expected digits: " + allowed.stream().map(x -> x.toString()).collect(Collectors.joining(", ")));
                }
            } while ( buf.peek() != 'e' && buf.peek() != 'E' && !in(END_CHARACTERS, buf.peek()) );
        }
        //exponent
        if ( buf.peek() == 'e' || buf.peek() == 'E' ) {
            pos++;
            builder.append(buf.pop());
            if ( buf.peek() == '-' || buf.peek() == '+') {
                pos++;
                builder.append(buf.pop());
            }
            do {
                if ( in(allowed, buf.peek())) {
                    ++pos;
                    builder.append(buf.pop());
                } else {
                    throw new InvalidCharacterSequence(pos,
                            "Expected digits: " + allowed.stream().map(x -> x.toString()).collect(Collectors.joining(", ")));
                }
            } while ( !in(END_CHARACTERS, buf.peek()) );
        }

        return new JSONNumber(builder.toString());
    }

    private JSONValue readValue(String key) throws Exception {
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
                value = readArray(key);
                break;
            case 'n':
                readNull();
                value = new JSONNull();
                break;
            case 't':
            case 'f':
                value = readBool();
                break;
            default:
                if ( isDigit(buf.peek()) || buf.peek() == '-' )
                    value = readNumber();
                else
                    throw new InvalidCharacterSequence(pos, "Unrecognised value");
                break;
        }

        return value;
    }

    private JSONArray readArray(String key) throws Exception {

        readExactly(new char[]{'['});

        JSONArray array = new JSONArray();
        array.setName(key);

        while ( true ) {
            JSONValue value = readValue(key);
            array.addValue(value);
            skipWhitespaces();
            if ( buf.peek() != ',' )
                break;
            else {
                buf.pop();
                ++pos;
            }
        }

        readExactly(new char[]{']'});

        return array;
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

    private char[] WHITESPACES = {' ', '\t', '\n', '\r'};
    private char[] END_CHARACTERS = {' ', ',', '}', ']'};

    private boolean isDigit(char c) {
        return c > 47 && c < 58;
    }

    private boolean in(char[] allowedCharacters, char character) {
        for ( char allowed: allowedCharacters )
            if ( allowed == character)
                return true;

        return false;
    }

    private boolean in(ArrayList<Character> allowedCharacters, char character) {
        for ( char allowed: allowedCharacters )
            if ( allowed == character)
                return true;

        return false;
    }

    private void readExactly(char[] charactersToRead) throws InvalidCharacterSequence {
        for ( char c: charactersToRead) {
            ++pos;
            if(buf.pop() != c)
                throw new InvalidCharacterSequence(pos, "Wrong characters sequence. Extected: " + Arrays.toString(charactersToRead));
        }
    }
}
