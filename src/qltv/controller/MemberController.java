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
import qltv.model.Member;
import qltv.singleton.JsonSingleTon;

/**
 *
 * @author VanhNguyenX8
 */
public class MemberController implements QLTVContronler {

    private final String jsonFilePath = "C:\\\\Users\\\\VanhNguyenX8\\\\Documents\\\\NetBeansProjects\\\\QLTV\\\\src\\\\qltv\\\\storage.json"; // Đường dẫn tới tệp JSON
    JsonSingleTon jsonSingleTon = JsonSingleTon.getInstance();
    Gson gson = new Gson();

    @Override
    public void add(Object object) {
        try {
            if (object instanceof Member) {
                Member member = (Member) object;
                addMememberToJson(member);
            }
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    @Override
    public boolean delete(String id) {
        try {
            JsonObject jsonObject = readJsonFile(jsonFilePath);
            JsonArray member = jsonObject.getAsJsonArray("members");
            for (int i = 0; i < member.size(); i++) {
                JsonObject memberObj = member.get(i).getAsJsonObject();
                String memberId = memberObj.get("id").getAsString();
                if (memberId.equals(id)) {
                    member.remove(i);
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
        try {
            if (object instanceof Member) {
                Member updateMember = (Member) object;
                // Đọc toàn bộ dữ liệu từ file JSON vào JsonObject
                JsonObject jsonObject = jsonSingleTon.readJsonFile();
                // Lấy mảng books từ JsonObject
                JsonArray memberResult = jsonObject.getAsJsonArray("members");
                System.out.println(memberResult);
                // Duyệt qua từng phần tử trong mảng books để tìm và cập nhật quyển sách có id tương ứng
                for (int i = 0; i < memberResult.size(); i++) {
                    JsonObject memberObj = memberResult.get(i).getAsJsonObject();
                    String bookId = memberObj.get("id").getAsString();
                    if (bookId.equals(updateMember.getId())) {
                        JsonObject newMember = new JsonObject();
                        memberObj.addProperty("id", updateMember.getId());
                        memberObj.addProperty("name", updateMember.getName());
                        memberObj.addProperty("address", updateMember.getAddress());
                        JsonArray borrowingsArray = new JsonArray();
                        for (String id : updateMember.getBorrowings()) {
                            System.out.println(id);
                            borrowingsArray.add(id);
                        }
                        memberObj.add("borrowings", borrowingsArray);

                        writeJsonFile(jsonObject, jsonFilePath);
                        return;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMememberToJson(Member member) {
        try {
            // Đọc toàn bộ dữ liệu từ file JSON vào JsonObject
            JsonObject jsonObject = readJsonFile(jsonFilePath);
            // Lấy mảng books từ JsonObject
            JsonArray booksArray = jsonObject.getAsJsonArray("members");
            // Tạo một JsonObject mới từ newBook
            JsonObject newMember = new JsonObject();
            newMember.addProperty("id", member.getId());
            newMember.addProperty("name", member.getName());
            newMember.addProperty("address", member.getAddress());
            JsonArray borrowingsArray = new JsonArray();
            for (String bookId : member.getBorrowings()) {
                borrowingsArray.add(bookId);
            }
            newMember.add("borrowings", borrowingsArray);
            // Thêm newBookObject vào mảng books
            booksArray.add(newMember);
            // Ghi lại JsonObject đã cập nhật vào file JSON
            writeJsonFile(jsonObject, jsonFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonObject readJsonFile(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            JsonParser jsonParser = new JsonParser();
            return jsonParser.parse(reader).getAsJsonObject();
        }
    }

    private void writeJsonFile(JsonObject jsonObject, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonObject, writer);
        }
    }

    public List<Member> fitter(Member member) throws IOException {
        List<Member> result = new ArrayList<>();
        JsonSingleTon jsonSingleTon = JsonSingleTon.getInstance();
        JsonObject jsonObject = jsonSingleTon.readJsonFile();
        Gson gson = new Gson();
        Data memberData = gson.fromJson(jsonObject, Data.class);
        for (int i = 0; i < memberData.getMembers().size(); i++) {
            Member memberJson = memberData.getMembers().get(i);

            if (member.getName() != null && memberJson.getName().contains(member.getName())) {
                result.add(memberJson);
            } else if (member.getId() != null && memberJson.getId().contains(member.getId())) {
                result.add(memberJson);

            }

        }
        return result;
    }

    public Member getMember(String id) throws IOException {
        JsonObject jsonObject = jsonSingleTon.readJsonFile();

        Data dataMember = gson.fromJson(jsonObject, Data.class);
        for (int i = 0; i < dataMember.getBorrowings().size(); i++) {
            Member member = dataMember.getMembers().get(i);
            if (member.getId() == null ? id == null : member.getId().equals(id)) {
                return member;
            }
        }
        return null;
    }
}
