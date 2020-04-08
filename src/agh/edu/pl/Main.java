package agh.edu.pl;

import agh.edu.pl.core.reader.JSONReader;

public class Main {
    public static void main(String[] args) {

        String test = "{ \"brand\": \"Maserati\",  \"model\" \n: \"Quattroporte\",\"aaa\":\"fsagda\", \"owner\": {\"name\":\"Sophia\", \"age\": -1.2e1, \"married\": false}, \"mileages\": [1,2,{\"aa\":3}]}";

        JSONReader reader = new JSONReader();

        try {
            System.out.println(reader.read(test).printToXML());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
