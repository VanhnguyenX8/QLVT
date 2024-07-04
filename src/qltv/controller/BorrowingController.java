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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import qltv.model.Book;
import qltv.model.Borrowing;
import qltv.model.Data;
import qltv.model.Member;
import qltv.singleton.JsonSingleTon;

/**
 *
 * @author VanhNguyenX8
 */
public class BorrowingController {

    private final String jsonFilePath = "C:\\\\Users\\\\VanhNguyenX8\\\\Documents\\\\NetBeansProjects\\\\QLTV\\\\src\\\\qltv\\\\storage.json"; // Đường dẫn tới tệp JSON

    JsonSingleTon jsonSingleTon = JsonSingleTon.getInstance();

    Gson gson = new Gson();

    public boolean borrowBook(String idUser, String idBook, Date endDate) throws IOException {
        //TODO: lay thong tin sach
        JsonObject jsonObject = jsonSingleTon.readJsonFile();

        Data libraryData = gson.fromJson(jsonObject, Data.class);
        for (int i = 0; i < libraryData.getBooks().size(); i++) {
            Book book = libraryData.getBooks().get(i);
            if (book.getId().equals(idBook)) {
                if (book.getCount() > 0) {
                    //TODO: cho nguoi ta muon va se phai xoa di so luong sach
                    //TODO: update sach IDSachMuon trong thuoc tinh member
                    MemberController memberControllerUpdate = new MemberController();
                    BookController bookController = new BookController();
                    JsonObject jsonObjectMember = jsonSingleTon.readJsonFile();
                    Data memberData = gson.fromJson(jsonObjectMember, Data.class);
                    for (int j = 0; j < memberData.getMembers().size(); j++) {
                        if (memberData.getMembers().get(i).getId() == null ? idUser == null : memberData.getMembers().get(i).getId().equals(idUser)) {
                            Member updatememMember = (Member) memberData.getMembers().get(i);
                            List<String> listBorrwings = updatememMember.getBorrowings();
                            listBorrwings.add(idBook);
                            System.out.println(listBorrwings);
                            System.out.println(idBook);

                            System.out.println(listBorrwings);
                            updatememMember.setBorrowings(listBorrwings);
                            memberControllerUpdate.update(updatememMember);
                            book.setCount(book.getCount() - 1);
                            bookController.update(book);
                            LocalDate localDate = LocalDate.now();
                            Date startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                            Borrowing borrowing = new Borrowing(idUser, idBook, startDate, endDate);
                            if (addBorrowBook(borrowing)) {
                                return true;
                            }
                            return false;

                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean addBorrowBook(Borrowing borrowing) {
        try {
            // Đọc toàn bộ dữ liệu từ file JSON vào JsonObject
            JsonObject jsonObject = jsonSingleTon.readJsonFile();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Lấy mảng books từ JsonObject
            JsonArray booksArray = jsonObject.getAsJsonArray("borrowings");
            // Tạo một JsonObject mới từ newBook
            JsonObject newBorrowing = new JsonObject();
            newBorrowing.addProperty("id", borrowing.getId());
            newBorrowing.addProperty("idMember", borrowing.getIdMember());

            newBorrowing.addProperty("startBorrowing", dateFormat.format(borrowing.getStartBorrowing()));
            newBorrowing.addProperty("endBorrowing", dateFormat.format(borrowing.getEndBorrowing()));
            newBorrowing.addProperty("state", "dang muon");
            // Thêm newBookObject vào mảng books
            booksArray.add(newBorrowing);
            // Ghi lại JsonObject đã cập nhật vào file JSON
            writeJsonFile(jsonObject, jsonFilePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Borrowing getBorrowing(String userId, String bookId) throws IOException {
        JsonObject jsonObject = jsonSingleTon.readJsonFile();

        Data dataBorrowing = gson.fromJson(jsonObject, Data.class);
        for (int i = 0; i < dataBorrowing.getBorrowings().size(); i++) {
            Borrowing borrowing = dataBorrowing.getBorrowings().get(i);
            if (borrowing.getId() == null ? bookId == null : borrowing.getId().equals(bookId) && borrowing.getIdMember() == null ? userId == null : borrowing.getIdMember().equals(userId)) {
                return borrowing;
            }
        }
        return null;
    }

    public String updateBorrowingMemberAndBook(String idUser, String idBook) throws IOException {
        MemberController memberController = new MemberController();
        Member member = memberController.getMember(idUser);
        List<String> idBooks = member.getBorrowings();
        idBooks.remove(idBook);
        member.setBorrowings(idBooks);
        memberController.update(member);
        BookController bookController = new BookController();
        Book book = bookController.getBook(idBook);
        book.setCount(book.getCount() + 1);
        bookController.update(book);
        if (updateStateBorrowing(idUser, idBook)) {
            return "late";
        } else {
            return "early";
        }

    }

    public boolean updateStateBorrowing(String idUser, String idBook) {
        try {
            Borrowing borrowing = getBorrowing(idUser, idBook);
            // Đọc toàn bộ dữ liệu từ file JSON vào JsonObject
            JsonObject jsonObject = jsonSingleTon.readJsonFile();
            // Lấy mảng books từ JsonObject
            JsonArray borowings = jsonObject.getAsJsonArray("borrowings");
            // Duyệt qua từng phần tử trong mảng books để tìm và cập nhật quyển sách có id tương ứng
            for (int i = 0; i < borowings.size(); i++) {
                JsonObject bookObject = borowings.get(i).getAsJsonObject();
                String borrowingId = bookObject.get("id").getAsString();
                String memberId = bookObject.get("idMember").getAsString();
                if (borrowingId.equals(idBook) && memberId.equals(idUser)) {
                    // Cập nhật thông tin quyển sách
                    if (borrowing.getEndBorrowing().before(new Date())) {
                        bookObject.addProperty("state", "Tra muon");
                    } else {
                        bookObject.addProperty("state", "Dung han");
                    }

                    // Ghi lại JsonObject đã cập nhật vào file JSON
                    writeJsonFile(jsonObject, jsonFilePath);
                    return true;
                }

                // Nếu không tìm thấy quyển sách có id tương ứng, có thể xử lý thông báo lỗi hoặc logic khác tại đây
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Borrowing> showBorrowing() throws IOException {
        List<Borrowing> results = new ArrayList<>();
        JsonObject jsonObjectMember = jsonSingleTon.readJsonFile();
        Data borrowingsData = gson.fromJson(jsonObjectMember, Data.class);
        for (int j = 0; j < borrowingsData.getMembers().size(); j++) {
            results.add(borrowingsData.getBorrowings().get(j));
        }
        return results;
    }

    private void writeJsonFile(JsonObject jsonObject, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
        }
    }

}
