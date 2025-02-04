package by.matthewvirus.service;

import java.io.FileWriter;
import java.io.IOException;

public class PrinterService {

    public static void print(String json) {
        try {
            FileWriter writer = new FileWriter("data.json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}