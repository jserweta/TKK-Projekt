package agh.edu.pl;

import agh.edu.pl.core.reader.JSONReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

//        String test = "{ \"brand\": \"Maserati\",  \"model\" \n: \"Quattroporte\",\"aaa\":\"fsagda\", \"owner\": {\"name\":\"Sophia\", \"age\": -1.2e1, \"married\": false}, \"mileages\": [1,2,{\"aa\":3}]}";

        try {
            String test = readFile("./res/example2.json");
            JSONReader reader = new JSONReader();
            System.out.println(reader.read(test).printToXML());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String         line;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }
}
