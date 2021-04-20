package com.team.btlon;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SecondaryController implements Initializable{
    
    @FXML private Button btPBack;
    @FXML private Label lbmess;
    @FXML private Label lbMa;
    @FXML private Label lbArrive;
    @FXML private Label lbDepart;
    @FXML private Label lbDaytime;
    @FXML private Label lbTimeflight;
    @FXML private RadioButton rbFirstclass;
    @FXML private RadioButton rdSecondclass;
    @FXML private Label lbPrice;
    @FXML private TextField txtStk;
    @FXML private ComboBox cbBank;
    
    @FXML private void btBack() throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
       Parent root = (Parent) loader.load();
       Stage stage = new Stage();
       stage.setScene(new Scene(root));
       stage.show();
            
       Stage stage1 = (Stage) this.btPBack.getScene().getWindow();
       stage1.close();
    }
    
    public void show(String ma, String arrive, String depart, String daytime, String timeflight) {
        this.lbMa.setText(ma);
        this.lbArrive.setText(arrive);
        this.lbDepart.setText(depart);
        this.lbDaytime.setText(daytime);
        this.lbTimeflight.setText(timeflight);     
    }
    
    private String getPrimaryclass(String ma) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id_chuyenbay FROM chuyenbay WHERE ma = '" + ma + "'");
        
        int p = 0;
        while (rs.next())
            p = rs.getInt("id_chuyenbay");
        
        Statement stp = conn.createStatement();
        ResultSet rsp = stp.executeQuery("SELECT * FROM ghe WHERE chuyenbay_id = " + p + " and loai = 1");
        
        int pc = 0;
        while (rsp.next()) {
            pc = Integer.parseInt(rsp.getString("gia"));
        }
        stp.close();
        
        st.close();
        conn.close();
        
        return String.format("%,d", pc);
    }
    
    private String getSecondaryclass(String ma) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id_chuyenbay FROM chuyenbay WHERE ma = '" + ma + "'");
        
        int p = 0;
        while (rs.next())
            p = rs.getInt("id_chuyenbay");
        
        Statement stp = conn.createStatement();
        ResultSet rsp = stp.executeQuery("SELECT * FROM ghe WHERE chuyenbay_id = " + p + " and loai = 2");
        
        int pc = 0;
        while (rsp.next()) {
            pc = Integer.parseInt(rsp.getString("gia"));
        }
        stp.close();
        
        st.close();
        conn.close();
        
        return String.format("%,d", pc);
    }
    
    private void rdButton() {
        ToggleGroup chair = new ToggleGroup();
        this.rbFirstclass.setToggleGroup(chair);
        this.rdSecondclass.setToggleGroup(chair);
        
        this.rdSecondclass.selectedProperty().addListener(cl -> {
            try {
                this.lbPrice.setText(this.getPrimaryclass(this.lbMa.getText()) + " VND");
            } catch (SQLException ex) {
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
        this.rbFirstclass.selectedProperty().addListener(cl -> {
            try {
                this.lbPrice.setText(this.getSecondaryclass(this.lbMa.getText()) + " VND");
            } catch (SQLException ex) {
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rdButton();
    }
}