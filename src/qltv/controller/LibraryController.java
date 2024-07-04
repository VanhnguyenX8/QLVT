/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qltv.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import qltv.model.Book;
import qltv.model.Data;
import qltv.model.Librarian;
import qltv.singleton.JsonSingleTon;

/**
 *
 * @author VanhNguyenX8
 */
public class LibraryController implements QLTVContronler {

    private final String jsonFilePath = "C:\\\\Users\\\\VanhNguyenX8\\\\Documents\\\\NetBeansProjects\\\\QLTV\\\\src\\\\qltv\\\\storage.json"; // Đường dẫn tới tệp JSON
    JsonSingleTon jsonSingleTon = JsonSingleTon.getInstance();

    public LibraryController() {
    }

    @Override
    public void add(Object object) {
        try {
            if (object instanceof Librarian) {
                Librarian newLibrarian = (Librarian) object;
                addLibrarian(newLibrarian);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            JsonObject jsonObject = jsonSingleTon.readJsonFile();
            // Lấy mảng books từ JsonObject
            JsonArray booksArray = jsonObject.getAsJsonArray("librarians");
            // Duyệt qua từng phần tử trong mảng books để tìm và xóa quyển sách có id tương ứng
            for (int i = 0; i < booksArray.size(); i++) {
                JsonObject bookObject = booksArray.get(i).getAsJsonObject();
                String bookId = bookObject.get("id").getAsString();
                if (bookId.equals(id)) {
                    booksArray.remove(i); // Xóa quyển sách khỏi mảng books
                    break; // Kết thúc vòng lặp sau khi xóa thành công
                }
            }
            // Ghi lại JsonObject đã cập nhật vào file JSON
            writeJsonFile(jsonObject, jsonFilePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void update(Object object) {
        try {
            if (object instanceof Librarian) {
                Librarian updateLibrarian = (Librarian) object;
                // Đọc toàn bộ dữ liệu từ file JSON vào JsonObject
                JsonObject jsonObject = jsonSingleTon.readJsonFile();
                // Lấy mảng books từ JsonObject
                JsonArray booksArray = jsonObject.getAsJsonArray("librarians");
                // Duyệt qua từng phần tử trong mảng books để tìm và cập nhật quyển sách có id tương ứng
                for (int i = 0; i < booksArray.size(); i++) {
                    JsonObject bookObject = booksArray.get(i).getAsJsonObject();
                    String bookId = bookObject.get("id").getAsString();
                    if (bookId.equals(updateLibrarian.getId())) {
                        // Cập nhật thông tin quyển sách
                        bookObject.addProperty("name", updateLibrarian.getName());
                        bookObject.addProperty("id", updateLibrarian.getId());
                        bookObject.addProperty("shift", updateLibrarian.getShift());
                        // Ghi lại JsonObject đã cập nhật vào file JSON
                        writeJsonFile(jsonObject, jsonFilePath);
                        return; // Kết thúc khi đã cập nhật thành công
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addLibrarian(Librarian librarian) {
        try {
            // Đọc toàn bộ dữ liệu từ file JSON vào JsonObject
            JsonObject jsonObject = jsonSingleTon.readJsonFile();
            // Lấy mảng books từ JsonObject
            JsonArray booksArray = jsonObject.getAsJsonArray("librarians");
            // Tạo một JsonObject mới từ newBook
            JsonObject newBookObject = new JsonObject();
            newBookObject.addProperty("id", librarian.getId());
            newBookObject.addProperty("name", librarian.getName());
            newBookObject.addProperty("shift", librarian.getShift());
            // Thêm newBookObject vào mảng books
            booksArray.add(newBookObject);
            // Ghi lại JsonObject đã cập nhật vào file JSON
            writeJsonFile(jsonObject, jsonFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức để ghi dữ liệu vào file JSON
    private void writeJsonFile(JsonObject jsonObject, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
        }
    }

    public List<Librarian> fiterWithIdOrCode(String name, String code) throws IOException {
        List<Librarian> result = new ArrayList<>();

        if (name != "") {
            //TODO: fiter buy name
            //get ALL product and add to array
            JsonSingleTon jsonSingleTon = JsonSingleTon.getInstance();
            JsonObject jsonObject = jsonSingleTon.readJsonFile();
            Gson gson = new Gson();
            Data libraryData = gson.fromJson(jsonObject, Data.class);
            for (int i = 0; i < libraryData.getLibrarians().size(); i++) {
                Librarian librarian = libraryData.getLibrarians().get(i);
                if(librarian.getName().contains(name)) {
                    result.add(librarian);
                }
            }
           
        }
         if (code != "") {
            //TODO: fiter buy name
            //get ALL product and add to array
            JsonSingleTon jsonSingleTon = JsonSingleTon.getInstance();
            JsonObject jsonObject = jsonSingleTon.readJsonFile();
            Gson gson = new Gson();
            Data libraryData = gson.fromJson(jsonObject, Data.class);
            for (int i = 0; i < libraryData.getLibrarians().size(); i++) {
                Librarian librarian = libraryData.getLibrarians().get(i);
                             System.out.println(librarian.getId());

                if(librarian.getId().contains(code)) {
                    result.add(librarian);
                }
            }
        }
        return result;
    }
}
