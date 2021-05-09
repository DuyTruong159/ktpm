/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team.btlon;

import com.team.pojo.Ghe;
import com.team.service.JdbcUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author duytruong
 */
public class AddgheController implements Initializable {

    @FXML private Label lbmess;
    @FXML private TextField txtprice1;
    @FXML private TextField txtsoluong1;
    @FXML private TextField txtprice2;
    @FXML private TextField txtsoluong2;
    @FXML private Button btaddht;
    @FXML private Button bthuy;
    String idcb;
    String p;
    
    public void getCB(String ma, String daytime, String timeflight) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id_chuyenbay FROM chuyenbay WHERE ma like '" + ma + "' AND daytime like '" + daytime + "' AND timeflight like '" + timeflight + "'");
        while(rs.next()) {
            this.idcb = rs.getString(1);
        }
    }
    
    public String getP(String text) {
        return this.p = text;
    }
    
    private void addghe(Ghe g1, Ghe g2) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st1 = conn.createStatement();
        st1.executeUpdate("INSERT INTO ghe (loai, gia, soluong, chuyenbay_id) VALUES (1, " + 
                g1.getGia() + ", " + g1.getSoluong() + ", " + this.idcb + ")");
                
        Statement st2 = conn.createStatement();
        st2.executeUpdate("INSERT INTO ghe (loai, gia, soluong, chuyenbay_id) VALUES (2, " + 
                g2.getGia() + ", " + g2.getSoluong() + ", " + this.idcb + ")");
    }
    
    @FXML private void btAdd (ActionEvent Event) throws SQLException {
        if(this.txtprice1.getText().isBlank() || this.txtprice2.getText().isBlank() 
                || this.txtsoluong1.getText().isBlank() || this.txtsoluong2.getText().isBlank()) {
            this.lbmess.setText("Fill Informations !!");
            this.txtprice1.setStyle("-fx-border-color: red;");
            this.txtprice2.setStyle("-fx-border-color: red;");
            this.txtsoluong1.setStyle("-fx-border-color: red;");
            this.txtsoluong2.setStyle("-fx-border-color: red;");
        } else {
            try {
                Ghe g1 = new Ghe(this.txtprice1.getText(), this.txtsoluong1.getText());
                Ghe g2 = new Ghe(this.txtprice2.getText(), this.txtsoluong2.getText());
                addghe(g1,g2);
         
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("New chuyenbay has added!!");
                alert.setHeaderText(null);
                alert.showAndWait();
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
                Parent root = (Parent) loader.load();
                
                AdminController ac = loader.getController();
                ac.getP(this.p);
                
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                
                Stage stage1 = (Stage) this.btaddht.getScene().getWindow();
                stage1.close();
            } catch (IOException ex) {
                Logger.getLogger(AddgheController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML private void btCancel (ActionEvent Event) throws SQLException {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You want to cancel!!");
            alert.setHeaderText(null);
            alert.showAndWait();
            
            Connection conn = JdbcUtils.getConn();
            conn.setAutoCommit(false);
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM chuyenbay WHERE id_chuyenbay = " + this.idcb);
            conn.commit();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root = (Parent) loader.load();
            
            AdminController ac = loader.getController();
            ac.getP(this.p);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            Stage stage1 = (Stage) this.bthuy.getScene().getWindow();
            stage1.close();
        } catch (IOException ex) {
            Logger.getLogger(AddgheController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.lbmess.setText("Nhập thông tin ghế chuyến bay");
    }    
    
}
