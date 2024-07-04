/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qltv.singleton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author VanhNguyenX8
 */
public class JsonSingleTon {

    private static JsonSingleTon instance;
    private static final String path = "C:\\\\Users\\\\VanhNguyenX8\\\\Documents\\\\NetBeansProjects\\\\QLTV\\\\src\\\\qltv\\\\storage.json";

    private JsonSingleTon() {
    }

    public static synchronized JsonSingleTon getInstance() {
        if (instance == null) {
            instance = new JsonSingleTon();
        }
        return instance;
    }

    // Phương thức để đọc dữ liệu từ file JSON
    public JsonObject readJsonFile() throws IOException {
        try (FileReader reader = new FileReader(path)) {
            JsonParser jsonParser = new JsonParser();
            return jsonParser.parse(reader).getAsJsonObject();
        }
    }

    // Phương thức để ghi dữ liệu vào file JSON
    public void writeJsonFile(JsonObject jsonObject) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
        }
    }

}
