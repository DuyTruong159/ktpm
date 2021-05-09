/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team.btlon;

import com.team.pojo.Khachhang;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author duytruong
 */
public class RegisterController implements Initializable {

    @FXML private ComboBox<Nganhang> cbBank;
    @FXML private TextField txtusername;
    @FXML private TextField txtcmnd;
    @FXML private TextField txtstk;
    @FXML private PasswordField txtpassword;
    @FXML private PasswordField txtconfirmpw;
    @FXML private Label lbmess;
    @FXML private Button btClose;
    @FXML private Button btAccess;
    
    @FXML private void btBack (ActionEvent Event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            Stage stage1 = (Stage) this.btClose.getScene().getWindow();
            stage1.close();
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
            this.cbBank.setStyle("-fx-border-color: red; -fx-font-family: Arial");
        } else if (!this.txtconfirmpw.getText().equals(this.txtpassword.getText())) {
            this.lbmess.setText("Incorect password confirm!!");
            this.txtpassword.setStyle("-fx-border-color: red;");
            this.txtconfirmpw.setStyle("-fx-border-color: red;");
        } else {
            try {
                Khachhang k = new Khachhang(this.txtusername.getText(), this.txtcmnd.getText(), this.txtpassword.getText(), this.cbBank.getSelectionModel().getSelectedItem().getId_nganhang());
                Register(k);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Welcome " + this.txtusername.getText());
                alert.showAndWait();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
                    Parent root = (Parent) loader.load();
                    
                    PrimaryController controller = loader.getController();
                    controller.getName(this.txtusername.getText()); 
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show(); 
                    
                    Stage stage1 = (Stage) this.btAccess.getScene().getWindow();
                    stage1.close();
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
    
    private void Register(Khachhang k) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        stm.executeUpdate("INSERT INTO khachhang (name, cmnd, nganhang_id, password, stk_id) VALUES "
                + "('" + k.getName() +"','" + k.getCmnd() + "','" + k.getNganhang_id() + "','" + k.getPassword() + "','" + this.getStk() + "')");    
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
            this.cbBank.getSelectionModel().selectFirst();
        } catch (SQLException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.txtstk.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtstk.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }    
}
