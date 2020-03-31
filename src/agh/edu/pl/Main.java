package agh.edu.pl;

import agh.edu.pl.core.reader.JSONReader;

public class Main {
    public static void main(String[] args) {

        String test = "{ \"brand\": \"Maserati\",  \"model\" \n: \"Quattroporte\",\"aaa\":\"fsagda\", \"owner\": {\"name\":\"Sophia\"}}";

        JSONReader reader = new JSONReader();

        try {
            System.out.println(reader.read(test).printToXML());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
