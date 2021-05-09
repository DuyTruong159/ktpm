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
public class Vechuyenbay {
    private String chuyenbay_id;
    private String khachhang_id;
    private String ghe_id;
    private String cb_id;
    
    public Vechuyenbay(String chuyenbay_id, String khachhang_id, String ghe_id, String cb_id) {
        this.chuyenbay_id = chuyenbay_id;
        this.khachhang_id = khachhang_id;
        this.ghe_id = ghe_id;
        this.cb_id = cb_id;
    }
    
    public Vechuyenbay(String chuyenbay_id, String khachhang_id, String ghe_id) {
        this.chuyenbay_id = chuyenbay_id;
        this.khachhang_id = khachhang_id;
        this.ghe_id = ghe_id;
    }
    
    public Vechuyenbay(String khachhang_id, String chuyenbay_id) {
        this.chuyenbay_id = chuyenbay_id;
        this.khachhang_id = khachhang_id;
    }
    
    public Vechuyenbay() {
    
    }
    
    public String toString() {
        return this.chuyenbay_id = chuyenbay_id;
    }

    /**
     * @return the chuyenbay_id
     */
    public String getChuyenbay_id() {
        return chuyenbay_id;
    }

    /**
     * @param chuyenbay_id the chuyenbay_id to set
     */
    public void setChuyenbay_id(String chuyenbay_id) {
        this.chuyenbay_id = chuyenbay_id;
    }

    /**
     * @return the khachhang_id
     */
    public String getKhachhang_id() {
        return khachhang_id;
    }

    /**
     * @param khachhang_id the khachhang_id to set
     */
    public void setKhachhang_id(String khachhang_id) {
        this.khachhang_id = khachhang_id;
    }

    /**
     * @return the ghe_id
     */
    public String getGhe_id() {
        return ghe_id;
    }

    /**
     * @param ghe_id the ghe_id to set
     */
    public void setGhe_id(String ghe_id) {
        this.ghe_id = ghe_id;
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
