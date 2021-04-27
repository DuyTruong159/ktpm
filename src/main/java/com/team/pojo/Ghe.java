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
    private String loai;
    private String gia;
    
    public Ghe(String loai, String gia) {
        this.gia = gia;
        this.loai = loai;
    }
    
    public Ghe() {
    }

    /**
     * @return the loai
     */
    public String getLoai() {
        return loai;
    }

    /**
     * @param loai the loai to set
     */
    public void setLoai(String loai) {
        this.loai = loai;
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
    
    
    
}
