/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qltv.model;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author VanhNguyenX8
 */
public class Member extends People implements Cloneable{
    String address;
    List<String> borrowings;

    public Member() {
    }

    public Member(String address, List<String> borrowings) {
        this.address = address;
        this.borrowings = borrowings;
    }

    public Member(String address, List<String> borrowings, String name, String id) {
        super(name, id);
        this.address = address;
        this.borrowings = borrowings;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // Sử dụng sao chép sâu (deep copy) để sao chép danh sách borrwings
        Member cloned = (Member) super.clone();
        cloned.borrowings = new ArrayList<>(this.borrowings);
        return cloned;
    }
    public String getAddress() {
        return address;
    }

    public List<String> getBorrowings() {
        return borrowings;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBorrowings(List<String> borrowings) {
        this.borrowings = borrowings;
    }

    @Override
    public String toString() {
        return "id: " + id + ",name: " + name + ",address: " + address + ",borrwings: " + borrowings;
    }
    
}
