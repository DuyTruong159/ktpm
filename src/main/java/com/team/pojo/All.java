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
public class All {
    private String ma;
    private String arrive;
    private String depart;
    private String daytime;
    private String timeflight;
    private String loai;
    private String gia;
    private String name;
    
    public All(String ma, String arrive, String depart, String daytime, 
            String timeflight, String loai, String gia, String name) {
        this.ma = ma;
        this.arrive = arrive;
        this.depart = depart;
        this.timeflight = timeflight;
        this.daytime = daytime;
        this.loai = loai;
        this.gia = gia;
        this.name = name;
    }
    
    public All() {
        
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
