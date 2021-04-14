/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team.btlon;

import com.team.pojo.Nganhang;
import com.team.service.JdbcUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author duytruong
 */
public class RegisterController implements Initializable {

    @FXML ComboBox<Nganhang> cbBank;
    @FXML TextField txtusername;
    @FXML TextField txtcmnd;
    @FXML TextField txtstk;
    @FXML PasswordField txtpassword;
    @FXML PasswordField txtconfirmpw;
    @FXML Label lbmess;
    
    @FXML private void btBack (ActionEvent Event) {
        try {
            App.setRoot("Login");
        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML private void btRegister (ActionEvent Event) {
        if(this.txtusername.getText().isBlank() || this.txtstk.getText().isBlank() || 
                this.txtpassword.getText().isBlank() || this.cbBank.getSelectionModel().isEmpty()) {
            this.lbmess.setText("Information Empty!!");
            this.txtusername.setStyle("-fx-border-color: red;");
            this.txtstk.setStyle("-fx-border-color: red;");
            this.txtpassword.setStyle("-fx-border-color: red;");
            this.cbBank.setStyle("-fx-border-color: red;");
        } else if (!this.txtconfirmpw.getText().equals(this.txtpassword.getText())) {
            this.lbmess.setText("Incorect password confirm!!");
            this.txtpassword.setStyle("-fx-border-color: red;");
            this.txtconfirmpw.setStyle("-fx-border-color: red;");
        } else {
            try {
                Register();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Welcome " + this.txtusername.getText());
                alert.showAndWait();
                try {
                    App.setRoot("primary");
                } catch (IOException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private int getStk() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement lstk = conn.createStatement();
        lstk.executeUpdate("INSERT INTO taikhoan (stk) VALUES (" + this.txtstk.getText() + ")");
        
        int idstk = 0;
        Statement gstk = conn.createStatement();
        ResultSet rsstk = gstk.executeQuery("SELECT * FROM taikhoan WHERE stk = " + this.txtstk.getText());
        while(rsstk.next()) {
            idstk = rsstk.getInt("id_taikhoan");
        }
        
        return idstk;
    }
    
    private void Register() throws SQLException {
        String name = this.txtusername.getText();
        String cmnd = this.txtcmnd.getText();
        int nganhang = this.cbBank.getSelectionModel().getSelectedItem().getId_nganhang();
        String pass = this.txtpassword.getText();
        
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        stm.executeUpdate("INSERT INTO khachhang (name, cmnd, nganhang_id, password, stk_id) VALUES "
                + "('" + name +"','" + cmnd + "','" + nganhang + "','" + pass + "','" + this.getStk() + "')");    
    }
    
    private List<Nganhang> getNganhang() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM nganhang");
        List<Nganhang> nhl = new ArrayList<>();
        
        while(rs.next()) {
            Nganhang nh = new Nganhang();
            nh.setId_nganhang(rs.getInt("id_nganhang"));
            nh.setTen(rs.getString("ten"));
            nhl.add(nh);
        }
        
        stm.close();
        conn.close();
        
        return FXCollections.observableArrayList(nhl);     
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.cbBank.setItems((ObservableList<Nganhang>) getNganhang());
        } catch (SQLException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
