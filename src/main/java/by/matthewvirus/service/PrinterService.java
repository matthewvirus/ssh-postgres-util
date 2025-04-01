package by.matthewvirus.service;

import by.matthewvirus.model.user.UserData;

import java.io.FileWriter;
import java.io.IOException;

public class PrinterService {

    public static void print(String json) {
        try {
            FileWriter writer = new FileWriter("data" + UserData.getUserSingleton().getShiftNumber() + ".json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}