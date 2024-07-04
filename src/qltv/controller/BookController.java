/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qltv.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.FileWriter;
import java.io.IOException;
import qltv.model.Book;
import java.util.ArrayList;
import java.util.List;
import qltv.model.Borrowing;
import qltv.model.Data;
import qltv.singleton.JsonSingleTon;

/**
 *
 * @author VanhNguyenX8
 */
public class BookController implements QLTVContronler {

    private final String jsonFilePath = "C:\\\\Users\\\\VanhNguyenX8\\\\Documents\\\\NetBeansProjects\\\\QLTV\\\\src\\\\qltv\\\\storage.json"; // Đường dẫn tới tệp JSON
    //singleton
    JsonSingleTon jsonSingleTon = JsonSingleTon.getInstance();

    Gson gson = new Gson();

    public BookController() {
    }

    @Override
    public void add(Object object) {
        try {
            if (object instanceof Book) {
                Book newBook = (Book) object;
                addBookToJson(newBook);
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
            JsonArray booksArray = jsonObject.getAsJsonArray("books");

            // Duyệt qua từng phần tử trong mảng books để tìm và xóa quyển sách có id tương ứng
            for (int i = 0; i < booksArray.size(); i++) {
                JsonObject bookObject = booksArray.get(i).getAsJsonObject();
                String bookId = bookObject.get("id").getAsString();
                if (bookId.equals(id)) {
                    booksArray.remove(i);
                    break;
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
//        TODO: update class
        try {
            if (object instanceof Book) {
                Book updatedBook = (Book) object;
                // Đọc toàn bộ dữ liệu từ file JSON vào JsonObject
                JsonObject jsonObject = jsonSingleTon.readJsonFile();
                // Lấy mảng books từ JsonObject
                JsonArray booksArray = jsonObject.getAsJsonArray("books");
                // Duyệt qua từng phần tử trong mảng books để tìm và cập nhật quyển sách có id tương ứng
                for (int i = 0; i < booksArray.size(); i++) {
                    JsonObject bookObject = booksArray.get(i).getAsJsonObject();
                    String bookId = bookObject.get("id").getAsString();
                    if (bookId.equals(updatedBook.getId())) {
                        // Cập nhật thông tin quyển sách
                        bookObject.addProperty("name", updatedBook.getName());
                        bookObject.addProperty("author", updatedBook.getAuthor());
                        bookObject.addProperty("category", updatedBook.getCategory());
                        bookObject.addProperty("count", updatedBook.getCount());

                        // Ghi lại JsonObject đã cập nhật vào file JSON
                        writeJsonFile(jsonObject, jsonFilePath);
                        return; // Kết thúc khi đã cập nhật thành công
                    }
                }
                // Nếu không tìm thấy quyển sách có id tương ứng, có thể xử lý thông báo lỗi hoặc logic khác tại đây
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức để đọc dữ liệu từ file JSON
//    private JsonObject readJsonFile(String filePath) throws IOException {
//        try (FileReader reader = new FileReader(filePath)) {
//            JsonParser jsonParser = new JsonParser();
//            return jsonParser.parse(reader).getAsJsonObject();
//        }
//    }
//
//    // Phương thức để ghi dữ liệu vào file JSON
    private void writeJsonFile(JsonObject jsonObject, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
        }
    }

    // Phương thức để thêm một quyển sách mới vào JSON
    private void addBookToJson(Book newBook) {
        try {
            // Đọc toàn bộ dữ liệu từ file JSON vào JsonObject
            JsonObject jsonObject = jsonSingleTon.readJsonFile();
            // Lấy mảng books từ JsonObject
            JsonArray booksArray = jsonObject.getAsJsonArray("books");
            // Tạo một JsonObject mới từ newBook
            JsonObject newBookObject = new JsonObject();
            newBookObject.addProperty("id", newBook.getId());
            newBookObject.addProperty("name", newBook.getName());
            newBookObject.addProperty("author", newBook.getAuthor());
            newBookObject.addProperty("category", newBook.getCategory());
            newBookObject.addProperty("count", newBook.getCount());
            // Thêm newBookObject vào mảng books
            booksArray.add(newBookObject);
            // Ghi lại JsonObject đã cập nhật vào file JSON
            writeJsonFile(jsonObject, jsonFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Book> fitter(Book book) throws IOException {
        List<Book> books = new ArrayList<Book>();
        JsonSingleTon jsonSingleTon = JsonSingleTon.getInstance();
        JsonObject jsonObject = jsonSingleTon.readJsonFile();
        Gson gson = new Gson();
        Data libraryData = gson.fromJson(jsonObject, Data.class);
        for (int i = 0; i < libraryData.getBooks().size(); i++) {
            Book bookresult = libraryData.getBooks().get(i);
            if (book.getId() != null && bookresult.getId().contains(book.getId())) {
                books.add(bookresult);
            } else if (book.getAuthor() != null && bookresult.getAuthor().contains(book.getId())) {
                books.add(bookresult);
            } else if (book.getCategory() != null && bookresult.getCategory().contains(book.getCategory())) {
                books.add(bookresult);
            } else if (book.getName() != null && bookresult.getName().contains(book.getName())) {
                books.add(bookresult);
            }
        }
        return books;
    }

    public Book getBook(String id) throws IOException {
        JsonObject jsonObject = jsonSingleTon.readJsonFile();

        Data dataBook = gson.fromJson(jsonObject, Data.class);
        for (int i = 0; i < dataBook.getBorrowings().size(); i++) {
            Book book = dataBook.getBooks().get(i);
            if (book.getId() == null ? id == null : book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }
}
