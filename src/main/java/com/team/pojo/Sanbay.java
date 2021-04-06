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
public class Sanbay {
    private int id_sanbay;
    private String sanbay;
    
    public Sanbay(int id_sanbay, String sanbay) {
        this.id_sanbay = id_sanbay;
        this.sanbay = sanbay;
    }

    /**
     * @return the id_sanbay
     */
    public int getId_sanbay() {
        return id_sanbay;
    }

    /**
     * @param id_sanbay the id_sanbay to set
     */
    public void setId_sanbay(int id_sanbay) {
        this.id_sanbay = id_sanbay;
    }

    /**
     * @return the sanbay
     */
    public String getSanbay() {
        return sanbay;
    }

    /**
     * @param sanbay the sanbay to set
     */
    public void setSanbay(String sanbay) {
        this.sanbay = sanbay;
    }
    
}
