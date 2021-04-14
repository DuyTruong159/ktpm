/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team.btlon;

import com.team.service.JdbcUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author duytruong
 */
public class LoginController implements Initializable {

    @FXML private TextField txtusername;
    @FXML private PasswordField txtpassword;
    @FXML private Label lbmess;
    
    @FXML private void Loginbt (ActionEvent Event) {
        if (this.txtusername.getText().isBlank() || this.txtpassword.getText().isBlank()) {
            this.lbmess.setText("Insert username and password");
        } else {
            try {
                getLogin();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    } 
    
    private void getLogin() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT count(1) FROM khachhang WHERE name = '" 
                                        + this.txtusername.getText() + "' and password = '" + this.txtpassword.getText() + "'");
        while(rs.next()) {
            if (rs.getInt(1) == 1) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("correct");
                alert.setHeaderText(null);
                alert.showAndWait();
                try {
                    App.setRoot("primary");
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                this.lbmess.setText("Failed. Try again");
            }      
        }      
    }
    
    @FXML private void btRegist (ActionEvent Event) {
        try {
            App.setRoot("Register");
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
            
            
                
        
        
      
    

