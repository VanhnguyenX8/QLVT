/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qltv.model;

/**
 *
 * @author VanhNguyenX8
 */
public class Librarian extends People{
    String shift;

    public Librarian() {
    }

    public Librarian(String shift) {
        this.shift = shift;
    }

    public Librarian(String shift, String name, String id) {
        super(name, id);
        this.shift = shift;
    }
  
    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getShift() {
        return shift;
    }

    @Override
    public String toString() {
        return "id: " + id + ",name: " + name + ",shift:" + shift;
    }
    
}
