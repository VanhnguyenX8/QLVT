/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qltv.model;

import java.util.Date;

/**
 *
 * @author VanhNguyenX8
 */
public class Borrowing {
    String idMember;
    String id;
    Date startBorrowing;
    Date endBorrowing;
    String state;
    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public String getIdMember() {
        return idMember;
    }

    public Date getEndBorrowing() {
        return endBorrowing;
    }

    public Date getStartBorrowing() {
        return startBorrowing;
    }

    public void setEndBorrowing(Date endBrrowing) {
        this.endBorrowing = endBrrowing;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
    }

    public void setStartBrrowing(Date startBrrowing) {
        this.startBorrowing = startBrrowing;
    }

    @Override
    public String toString() {
        return "idMember: " + idMember + ", idBook: " + id + ", start brrowing: " + startBorrowing + ", endBrrowing: " + endBorrowing + "Trạng thái: " + state;
    }
    
    public Borrowing() {
    }

    public Borrowing(String idMember, String id, Date startBrrowing, Date endBrrowing) {
        this.idMember = idMember;
        this.id = id;
        this.startBorrowing = startBrrowing;
        this.endBorrowing = endBrrowing;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    
    
}
