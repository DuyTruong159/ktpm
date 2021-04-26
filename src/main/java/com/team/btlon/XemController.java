/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team.btlon;

import com.team.pojo.Chuyenbay;
import com.team.pojo.Vechuyenbay;
import com.team.service.JdbcUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author duytruong
 */
public class XemController implements Initializable {

    @FXML private Button btPback;
    @FXML private Label lbName;
    @FXML private Label lbSodu;
    @FXML private Label p;
    @FXML ComboBox<Vechuyenbay> cbXem;
    @FXML private Label lbArrive;
    @FXML private Label lbDepart;
    @FXML private Label lbDaytime;
    @FXML private Label lbTimeflight;
    @FXML private Label lbPrice;
    @FXML private Label lbClass;
    @FXML private Label lbmess;
    @FXML private Button btHuy;
    
    @FXML private void btBack (ActionEvent Event) throws IOException {
        if ("admin".equals(this.lbName.getText())) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
            Parent root = (Parent) loader.load();
            PrimaryController prc = loader.getController();
            prc.Back();
            prc.getName(this.lbName.getText());
            prc.getPass(this.p.getText());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            Stage stage1 = (Stage) this.btPback.getScene().getWindow();
            stage1.close();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
            Parent root = (Parent) loader.load();
            PrimaryController prc = loader.getController();
            prc.getName(this.lbName.getText());
            prc.getPass(this.p.getText());
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            Stage stage1 = (Stage) this.btPback.getScene().getWindow();
            stage1.close();
        }
    }
    
    @FXML private void btXoa(ActionEvent Event) throws SQLException {
        TextInputDialog inp = new TextInputDialog();
        inp.setHeaderText("Confirm password: ");
        Optional<String> num = inp.showAndWait();
        num.ifPresent(name -> {
            if(name.equals(this.p.getText())) {
                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("You have Canceled succesfull!!");
                    alert.showAndWait();
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("xem.fxml"));
                    Parent root = (Parent) loader.load();
                    
                    XemController xc = loader.getController();
                    xc.getN(this.lbName.getText());
                    xc.getP(this.p.getText());
                    try {
                        xc.getsodu(this.lbName.getText(), this.p.getText());
                    } catch (SQLException ex) {
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    
                    Connection conn = JdbcUtils.getConn();
                    conn.setAutoCommit(false);
                    Statement sthk = conn.createStatement();
                    ResultSet rshk = sthk.executeQuery("SELECT id_khachhang FROM khachhang WHERE name like 'truong' AND password like '123'");
                    while(rshk.next()) {
                        Statement stcb = conn.createStatement();
                        ResultSet rscb = stcb.executeQuery("SELECT id_chuyenbay FROM chuyenbay WHERE ma like '" 
                                + this.cbXem.getSelectionModel().getSelectedItem().getChuyenbay_id() + "'");
                        while(rscb.next()) {
                            Statement stvcb = conn.createStatement();
                            stvcb.executeUpdate("DELETE FROM vechuyenbay WHERE khachhang_id = " + 
                                rshk.getString(1) + " AND chuyenbay_id = " + rscb.getString(1));
                        }
                        stcb.close();
                    }  
                    sthk.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(XemController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(XemController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Wrong Password");
                alert.showAndWait();
            }
        });
    }
    
    public void getN(String text) {
        this.lbName.setText(text);
    }
    
    public void getP(String pass) {
        this.p.setText(pass);
    }
    
    public void getsodu(String name, String pass) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement sthk = conn.createStatement();
        ResultSet rshk = sthk.executeQuery("SELECT stk_id FROM khachhang WHERE name like '" + name + "' AND password like '" + pass + "'");
        
        while (rshk.next()) {
            Statement sttk = conn.createStatement();
            ResultSet rstk = sttk.executeQuery("SELECT money FROM taikhoan WHERE id_taikhoan = " + rshk.getString(1));
            while(rstk.next()) {
                this.lbSodu.setText(String.format("%,d", Integer.parseInt(rstk.getString(1))) + " VND");
            }
            sttk.close();
        }
        
        sthk.close();
        conn.close();
        
    }
    
    private ObservableList<Vechuyenbay> getVechuyenbay() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id_khachhang FROM khachhang WHERE name like 'truong' AND password like '123'");
        while (rs.next()) {
            Statement stvcb = conn.createStatement();
            ResultSet rsvcb = stvcb.executeQuery("SELECT * FROM vechuyenbay WHERE khachhang_id = " + rs.getString(1));
            List<Vechuyenbay> gvcb = new ArrayList<>();
            while (rsvcb.next()) {
                Vechuyenbay vcb = new Vechuyenbay();
                vcb.setCb_id(rsvcb.getString("chuyenbay_id"));
                Statement stcb = conn.createStatement();
                ResultSet rscb = stcb.executeQuery("SELECT * FROM chuyenbay WHERE id_chuyenbay = " + rsvcb.getString("chuyenbay_id"));
                while(rscb.next()) {
                    vcb.setChuyenbay_id(rscb.getString("ma"));
                }
                gvcb.add(vcb);
            }
            return FXCollections.observableArrayList(gvcb);
        }
        
        st.close();
        conn.close();        
        return null;
    }
    
    @FXML private void getThongtin(ActionEvent Event) throws SQLException { 
        Connection conn = JdbcUtils.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM chuyenbay WHERE ma like '" + this.cbXem.getSelectionModel().getSelectedItem().getChuyenbay_id() + "'");
        while(rs.next()) {
            Statement stm1 = conn.createStatement();
            ResultSet rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs.getString("arrive_id"));
            while (rs1.next()) {
                this.lbArrive.setText(rs1.getString("sanbay"));
            }
            rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs.getString("depart_id"));
            while (rs1.next()) {
                this.lbDepart.setText(rs1.getString("sanbay"));
            }
            stm1.close();
            this.lbDaytime.setText(rs.getString("daytime"));
            this.lbTimeflight.setText(rs.getString("timeflight"));
            
            Statement stkh = conn.createStatement();
            ResultSet rskh = stkh.executeQuery("SELECT id_khachhang FROM khachhang WHERE name like 'truong' AND password like '123'");
            while (rskh.next()) {
                Statement stvcb = conn.createStatement();
                ResultSet rsvcb = stvcb.executeQuery("SELECT ghe_id FROM vechuyenbay WHERE khachhang_id = " 
                        + rskh.getString(1) + " AND chuyenbay_id = " + rs.getString("id_chuyenbay"));
                while(rsvcb.next()) {
                    Statement stg = conn.createStatement();
                    ResultSet rsg = stg.executeQuery("SELECT * FROM ghe WHERE id_ghe = " + rsvcb.getString("ghe_id"));
                    while(rsg.next()) {
                        this.lbPrice.setText(String.format("%,d", Integer.parseInt(rsg.getString("gia"))) + " VND");
                        if(Integer.parseInt(rsg.getString("loai")) == 1) {
                            this.lbClass.setText("Primary Class");
                        } else {
                            this.lbClass.setText("Secondary Class");
                        }
                    }
                }
            }
        }
        
        st.close();
        conn.close();     
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        try {
            this.cbXem.setItems(this.getVechuyenbay());
            this.cbXem.getSelectionModel().selectFirst();
            
            if (!this.cbXem.getSelectionModel().isEmpty()) {
                Connection conn = JdbcUtils.getConn();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM chuyenbay WHERE ma like '" + this.cbXem.getSelectionModel().getSelectedItem().getChuyenbay_id() + "'");
                while(rs.next()) {
                    Statement stm1 = conn.createStatement();
                    ResultSet rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs.getString("arrive_id"));
                    while (rs1.next()) {
                        this.lbArrive.setText(rs1.getString("sanbay"));
                    }
                    rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs.getString("depart_id"));
                    while (rs1.next()) {
                        this.lbDepart.setText(rs1.getString("sanbay"));
                    }
                    stm1.close();
                    this.lbDaytime.setText(rs.getString("daytime"));
                    this.lbTimeflight.setText(rs.getString("timeflight"));

                    Statement stkh = conn.createStatement();
                    ResultSet rskh = stkh.executeQuery("SELECT id_khachhang FROM khachhang WHERE name like 'truong' AND password like '123'");
                    while (rskh.next()) {
                        Statement stvcb = conn.createStatement();
                        ResultSet rsvcb = stvcb.executeQuery("SELECT ghe_id FROM vechuyenbay WHERE khachhang_id = " 
                                + rskh.getString(1) + " AND chuyenbay_id = " + rs.getString("id_chuyenbay"));
                        while(rsvcb.next()) {
                            Statement stg = conn.createStatement();
                            ResultSet rsg = stg.executeQuery("SELECT * FROM ghe WHERE id_ghe = " + rsvcb.getString("ghe_id"));
                            while(rsg.next()) {
                                this.lbPrice.setText(String.format("%,d", Integer.parseInt(rsg.getString("gia"))) + " VND");
                                if(Integer.parseInt(rsg.getString("loai")) == 1) {
                                    this.lbClass.setText("Primary Class");
                                } else {
                                    this.lbClass.setText("Secondary Class");
                                }
                            }
                        }
                    }
                }

                st.close();
                conn.close();     
            } else {
                this.lbmess.setText("Khoo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(XemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}