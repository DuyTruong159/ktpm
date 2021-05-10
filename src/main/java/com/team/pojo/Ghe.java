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
public class Ghe {
    private String gia;
    private String soluong;
    private int sl;
    private String cb_id;
    
    public Ghe(String gia, String soluong) {
        this.gia = gia;
        this.soluong = soluong;
    }
    
    public Ghe(int sl, String soluong) {
        this.sl = sl;
        this.soluong = soluong;
    }
    
    public Ghe(String cb_id) {
        this.cb_id = cb_id;
    }
    
    public Ghe() {
    }

    /**
     * @return the gia
     */
    public String getGia() {
        return gia;
    }

    /**
     * @param gia the gia to set
     */
    public void setGia(String gia) {
        this.gia = gia;
    }

    /**
     * @return the soluong
     */
    public String getSoluong() {
        return soluong;
    }

    /**
     * @param soluong the soluong to set
     */
    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    /**
     * @return the sl
     */
    public int getSl() {
        return sl;
    }

    /**
     * @param sl the sl to set
     */
    public void setSl(int sl) {
        this.sl = sl;
    }

    /**
     * @return the cb_id
     */
    public String getCb_id() {
        return cb_id;
    }

    /**
     * @param cb_id the cb_id to set
     */
    public void setCb_id(String cb_id) {
        this.cb_id = cb_id;
    }
}
