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
public class Chuyenbay {
    private String ma;
    private String arrive;
    private String depart;
    private String daytime;
    private String timeflight;
    private String id_chuyenbay;
    private String soghe1;
    private String soghe2;
    
    public Chuyenbay(String id, String ma, String arrive, String depart, String daytime, String timeflight, String soghe1, String soghe2) {
        this.ma = ma;
        this.arrive = arrive;
        this.depart = depart;
        this.daytime = daytime;
        this.timeflight = timeflight;
        this.id_chuyenbay = id;
        this.soghe1 = soghe1;
        this.soghe2 = soghe2;
    }

    public Chuyenbay() {
    
    }
    
    public String toString() {
        return this.ma = ma;
    }

    /**
     * @return the ma
     */
    public String getMa() {
        return ma;
    }

    /**
     * @param ma the ma to set
     */
    public void setMa(String ma) {
        this.ma = ma;
    }

    /**
     * @return the arrive
     */
    public String getArrive() {
        return arrive;
    }

    /**
     * @param arrive the arrive to set
     */
    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    /**
     * @return the depart
     */
    public String getDepart() {
        return depart;
    }

    /**
     * @param depart the depart to set
     */
    public void setDepart(String depart) {
        this.depart = depart;
    }

    /**
     * @return the daytime
     */
    public String getDaytime() {
        return daytime;
    }

    /**
     * @param daytime the daytime to set
     */
    public void setDaytime(String daytime) {
        this.daytime = daytime;
    }

    /**
     * @return the timeflight
     */
    public String getTimeflight() {
        return timeflight;
    }

    /**
     * @param timeflight the timeflight to set
     */
    public void setTimeflight(String timeflight) {
        this.timeflight = timeflight;
    }

    /**
     * @return the id_chuyenbay
     */
    public String getId_chuyenbay() {
        return id_chuyenbay;
    }

    /**
     * @param id_chuyenbay the id_chuyenbay to set
     */
    public void setId_chuyenbay(String id_chuyenbay) {
        this.id_chuyenbay = id_chuyenbay;
    }

    /**
     * @return the soghe1
     */
    public String getSoghe1() {
        return soghe1;
    }

    /**
     * @param soghe1 the soghe1 to set
     */
    public void setSoghe1(String soghe1) {
        this.soghe1 = soghe1;
    }

    /**
     * @return the soghe2
     */
    public String getSoghe2() {
        return soghe2;
    }

    /**
     * @param soghe2 the soghe2 to set
     */
    public void setSoghe2(String soghe2) {
        this.soghe2 = soghe2;
    }
}
