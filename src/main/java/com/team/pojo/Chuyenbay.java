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
    
    public Chuyenbay(String ma, String arrive, String depart, String daytime, String timeflight) {
        this.ma = ma;
        this.arrive = arrive;
        this.depart = depart;
        this.daytime = daytime;
        this.timeflight = timeflight;
    }

    public Chuyenbay() {
    
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
}
