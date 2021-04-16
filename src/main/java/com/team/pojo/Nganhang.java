/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team.pojo;

/**
 *
 * @author duytruong
 */
public class Nganhang {
    private int id_nganhang;
    private String ten;
    
    public Nganhang(int id, String ten) {
        this.id_nganhang = id;
        this.ten = ten;
    }
    
    public Nganhang() {
    }

    /**
     * @return the id_nganhang
     */
    public int getId_nganhang() {
        return id_nganhang;
    }
    
    public String toString() {
        return this.ten = ten;
    }

    /**
     * @param id_nganhang the id_nganhang to set
     */
    public void setId_nganhang(int id_nganhang) {
        this.id_nganhang = id_nganhang;
    }

    /**
     * @return the ten
     */
    public String getTen() {
        return ten;
    }

    /**
     * @param ten the ten to set
     */
    public void setTen(String ten) {
        this.ten = ten;
    }
}
