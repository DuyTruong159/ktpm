package com.team.btlon;

import com.team.pojo.Chuyenbay;
import com.team.service.JdbcUtils;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrimaryController implements Initializable {
     
    @FXML private TextField txtArrive;
    @FXML private TextField txtDepart;
    @FXML private TableView<Chuyenbay> tbCb;
    
    @FXML private void btSearch (ActionEvent event) {
        
        
    }
    
    private List<Chuyenbay> getChuyenbays() throws SQLException {
        Connection conn = JdbcUtils.getConn();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM chuyenbay");
        List<Chuyenbay> lcb = new ArrayList<>();
        
        while(rs.next()) {
            Chuyenbay cb = new Chuyenbay();
            cb.setMa(rs.getString("ma"));
            Statement stm1 = conn.createStatement();
            ResultSet rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs.getString("arrive_id"));
            while (rs1.next()) {
                cb.setArrive(rs1.getString("sanbay"));
            }
            rs1 = stm1.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs.getString("depart_id"));
            while (rs1.next()) {
                cb.setDepart(rs1.getString("sanbay"));
            }
            stm1.close();
            cb.setDaytime(rs.getString("daytime"));
            cb.setTimeflight(rs.getString("timeflight"));
            lcb.add(cb);
        }
        
        stm.close();
        conn.close();
        
        return FXCollections.observableArrayList(lcb);
    }
    
    private List<Chuyenbay> getChuyenbayByArrive(String kw) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
        stm.setString(1, kw);
        ResultSet rs = stm.executeQuery();
        List<Chuyenbay> lcb = new ArrayList<>();
        
        while(rs.next()) {
            Statement stm1 = conn.createStatement();
            ResultSet rs1 = stm1.executeQuery("SELECT * FROM chuyenbay WHERE arrive_id = " + rs.getString("id_sanbay"));
            while (rs1.next()) {
                Chuyenbay cb = new Chuyenbay();
                cb.setMa(rs1.getString("ma"));
                cb.setArrive(rs.getString("sanbay"));
                Statement stm2 = conn.createStatement();
                ResultSet rs2 = stm2.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs1.getString("depart_id"));
                while(rs2.next()) {
                    cb.setDepart(rs2.getString("sanbay"));
                }
                cb.setDaytime(rs1.getString("daytime"));
                cb.setTimeflight(rs1.getString("timeflight"));
                lcb.add(cb);
            }
            stm1.close();
        }
        
        stm.close();
        conn.close();
        
        return FXCollections.observableArrayList(lcb);   
    }
    
    private List<Chuyenbay> getChuyenbayByDepart(String kw) throws SQLException {
        Connection conn = JdbcUtils.getConn();
        PreparedStatement stm = conn.prepareStatement("SELECT * FROM sanbay WHERE sanbay like concat ('%', ? ,'%')");
        stm.setString(1, kw);
        ResultSet rs = stm.executeQuery();
        List<Chuyenbay> lcb = new ArrayList<>();
        
        while(rs.next()) {
            Statement stm1 = conn.createStatement();
            ResultSet rs1 = stm1.executeQuery("SELECT * FROM chuyenbay WHERE depart_id = " + rs.getString("id_sanbay"));
            while (rs1.next()) {
                Chuyenbay cb = new Chuyenbay();
                cb.setMa(rs1.getString("ma"));
                Statement stm2 = conn.createStatement();
                ResultSet rs2 = stm2.executeQuery("SELECT * FROM sanbay WHERE id_sanbay = " + rs1.getString("arrive_id"));
                while(rs2.next()) {
                    cb.setArrive(rs2.getString("sanbay"));
                }
                cb.setDepart(rs.getString("sanbay"));
                cb.setDaytime(rs1.getString("daytime"));
                cb.setTimeflight(rs1.getString("timeflight"));
                lcb.add(cb);
            }
            stm1.close();
        }
        
        stm.close();
        conn.close();
        
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
        
        TableColumn colDatve = new TableColumn("Dat ve");
        colDatve.setCellFactory(p -> {
            Button bt = new Button("Dat ve");
            bt.setOnAction(et -> {
                try {
                    App.setRoot("secondary");
                } catch (IOException ex) {
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            });
            TableCell cell = new TableCell();
            cell.setGraphic(bt);
            return cell;
        });
          
        this.tbCb.setItems((ObservableList<Chuyenbay>) getChuyenbays());
        this.tbCb.getColumns().addAll(colMa, colArrive, colDepart,  colDayTime, colTimeFlight, colDatve); 
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        try {
            this.loadChuyenbay();
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        this.txtArrive.textProperty().addListener(et -> {
            try {
                this.tbCb.getItems().clear();
                this.tbCb.setItems((ObservableList<Chuyenbay>) getChuyenbayByArrive(this.txtArrive.getText()));
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        });
        
        this.txtDepart.textProperty().addListener(et -> {
            try {
                this.tbCb.getItems().clear();
                this.tbCb.setItems((ObservableList<Chuyenbay>) getChuyenbayByDepart(this.txtDepart.getText()));
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        });
    }
}
        
        
    
            
        
                
         
        
        
    

