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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private TableView tbXem;
    @FXML private Label p;
    @FXML private Label test;
    String i;
    
    @FXML private void btBack (ActionEvent Event) throws IOException {
        if ("admin".equals(this.lbName.getText())) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
            Parent root = (Parent) loader.load();
            PrimaryController prc = loader.getController();
            prc.Back();
            prc.getName(this.lbName.getText());
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
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            
            Stage stage1 = (Stage) this.btPback.getScene().getWindow();
            stage1.close();
        }
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
    
    public void getcbdadat(String name, String pass) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT id_khachhang FROM khachhang WHERE name like '" + name + 
                "' AND password like '" + pass + "'");
        while(rs.next()) {
            Statement stvcb = conn.createStatement();
            ResultSet rsvcb = stvcb.executeQuery("SELECT * FROM vechuyenbay WHERE khachhang_id = " + rs.getString(1));
            while(rsvcb.next()) {
                this.test.setText(rsvcb.getString(1));
            }
        }
    }
    
    private List<Chuyenbay> getChuyenbays() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        
        Statement stcb = conn.createStatement();
        ResultSet rscb = stcb.executeQuery("SELECT * FROM chuyenbay WHERE id_chuyenbay like '" + this.test.getText() + "'");
        List<Chuyenbay> lcb = new ArrayList<>();
        while(rscb.next()) {
            Chuyenbay cb = new Chuyenbay();
            cb.setMa(rscb.getString("ma"));
            Statement stm1 = conn.createStatement();
            ResultSet rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rscb.getString("arrive_id"));
            while (rs1.next()) {
                cb.setArrive(rs1.getString("sanbay"));
            }
            rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rscb.getString("depart_id"));
            while (rs1.next()) {
                        cb.setDepart(rs1.getString("sanbay"));
            }
            stm1.close();
            cb.setDaytime(rscb.getString("daytime"));
            cb.setTimeflight(rscb.getString("timeflight"));
            lcb.add(cb);
        }
                
        return FXCollections.observableArrayList(lcb);
    } 
    
    private void loadChuyenbay() throws SQLException {

      TableColumn colMa = new TableColumn("Ma"); 
        colMa.setCellValueFactory(new PropertyValueFactory("ma"));
        
        TableColumn colArrive = new TableColumn("Arrive"); 
        colArrive.setCellValueFactory(new PropertyValueFactory("arrive"));
        
        TableColumn colDepart = new TableColumn("Depart"); 
        colDepart.setCellValueFactory(new PropertyValueFactory("depart"));
        
        TableColumn colDayTime = new TableColumn("DayTime"); 
        colDayTime.setCellValueFactory(new PropertyValueFactory("daytime"));
        
        TableColumn colTimeFlight = new TableColumn("TimeFlight"); 
        colTimeFlight.setCellValueFactory(new PropertyValueFactory("timeflight"));
        
        this.tbXem.setItems((ObservableList<Chuyenbay>) getChuyenbays());
        this.tbXem.getColumns().addAll(colMa, colArrive, colDepart,  colDayTime, colTimeFlight); 


    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.loadChuyenbay();
        } catch (SQLException ex) {
            Logger.getLogger(XemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
