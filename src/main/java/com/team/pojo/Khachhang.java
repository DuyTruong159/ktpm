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
public class Khachhang {
    private String name;
    private String cmnd;
    private String password;
    private int nganhang_id;
    private String stk_id;
    
    public Khachhang(String name, String cmnd, String password, int nganhang_id) {
        this.name = name;
        this.cmnd = cmnd;
        this.password = password;
        this.nganhang_id = nganhang_id;
    }
    
    public Khachhang(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    public Khachhang(String name, int nganhang_id, String stk_id) {
        this.name = name;
        this.nganhang_id = nganhang_id;
        this.stk_id = stk_id;
    }
    
    public Khachhang() {
    
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

    /**
     * @return the cmnd
     */
    public String getCmnd() {
        return cmnd;
    }

    /**
     * @param cmnd the cmnd to set
     */
    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the nganhang_id
     */
    public int getNganhang_id() {
        return nganhang_id;
    }

    /**
     * @param nganhang_id the nganhang_id to set
     */
    public void setNganhang_id(int nganhang_id) {
        this.nganhang_id = nganhang_id;
    }

    /**
     * @return the stk_id
     */
    public String getStk_id() {
        return stk_id;
    }

    /**
     * @param stk_id the stk_id to set
     */
    public void setStk_id(String stk_id) {
        this.stk_id = stk_id;
    }
    
}
